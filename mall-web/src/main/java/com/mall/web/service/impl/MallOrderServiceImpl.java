package com.mall.web.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.mall.common.api.CommonResult;
import com.mall.common.enumconfig.enumWxPlatformStatusCode;
import com.mall.mbg.Mapper.LMemberMapper;
import com.mall.mbg.Mapper.LMemberWechatMapper;
import com.mall.mbg.Mapper.LOrderMapper;
import com.mall.mbg.Model.*;
import com.mall.web.config.WeChatConfig;
import com.mall.web.dto.OrderExcelDto;
import com.mall.web.param.OrderQueryParam;
import com.mall.web.service.MallMemberService;
import com.mall.web.service.MallOrderService;
import com.mall.web.service.MallWxService;
import com.mall.web.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

@Service
public class MallOrderServiceImpl implements MallOrderService {


    @Autowired
    private MallMemberService mallMemberService;
    @Autowired
    private LOrderMapper  orderMapper;
    @Autowired
    private LMemberMapper memberMapper;


    private final static Object excelUploadLock = new Object();
    private List<OrderExcelDto> orderList       = null;
    private OrderExcelDto orderExcelDto = null;


    @Override
    public List<LOrder> orderList(OrderQueryParam orderQueryParam) {

        Integer currentMemberId = mallMemberService.getCurrentMemberId();
        if(currentMemberId == null || currentMemberId <= 0){
            throw new BadCredentialsException("未登录，无法查询");
        }

        LOrderExample orderExample = new LOrderExample();
        LOrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andMemberIdEqualTo(currentMemberId);

        if(orderQueryParam != null){
            if(orderQueryParam.getPatStatus() != null){
                criteria.andPayStatusEqualTo(orderQueryParam.getPatStatus());
            }
        }

        return  orderMapper.selectByExample(orderExample);
    }

    @Override
    public LOrder getOrderByOrderSn(String OrderSn) {
        LOrderExample orderExample = new LOrderExample();
        LOrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andOrderSnEqualTo(OrderSn);

        List<LOrder> lOrders = orderMapper.selectByExample(orderExample);
        if(lOrders != null && lOrders.size() > 0){
            return lOrders.get(0);
        }
        return new LOrder();
    }

    @Override
    public String importOrderByExcel(InputStream inputStream) {
        long startTime = System.currentTimeMillis();//获取开始时间

        orderList = new ArrayList<>();
        synchronized (excelUploadLock) {
            ExcelUtil.readBySax(inputStream, 0, new RowHandler() {
                @Override
                public void handle(int sheetIndex, int rowIndex, List<Object> list) {
                    try {
                        //从第三行开始导入
                        if (rowIndex < 1) return;
                        //组装数据
                        assemblyOrderListList(list);
                    } catch (Exception ex) {
                        throw new BadCredentialsException("第" + rowIndex + "行:" + ex.getMessage());
                    }
                }
            });
            //插入最后一次数据
            if (orderList != null && orderExcelDto != null && !StringUtils.isEmpty(orderExcelDto.getOrderSn())) {
                orderList.add(orderExcelDto);
            }
            //重置静态变量
            orderExcelDto = null;
        }

        Integer orderNumber = 0;
        //导入数据
        for (OrderExcelDto excelDto : orderList) {
            //插入用户
            Integer memberId = this.insertMember(excelDto);

            LOrder byOrderSn = this.getOrderByOrderSn(excelDto.getOrderSn());
            if(byOrderSn != null && byOrderSn.getOrderId() != null && byOrderSn.getOrderId() <= 0){
                continue;
            }

            LOrder lOrder = new LOrder();
            lOrder.setOrderSn(excelDto.getOrderSn());
            lOrder.setBillDate(new Date());
            lOrder.setMemberId(memberId);
            lOrder.setOrderStatus(0);
            lOrder.setPayStatus(0);
            lOrder.setOrderAmount(BigDecimal.valueOf(excelDto.getOrderAmount()));
            lOrder.setRemark(excelDto.getRemark());

            orderMapper.insertSelective(lOrder);
            orderNumber++;
        }
        long endTime = System.currentTimeMillis();//获取结束时间
        return "成功导入：" + orderNumber + " 条，一共消耗：" + (endTime - startTime) / (1000) + " 秒";
    }

    private Integer insertMember(OrderExcelDto excelDto){
        LMember memberByUsername = mallMemberService.getMemberByUsername(excelDto.getStudentNo());
        if(memberByUsername == null || memberByUsername.getId() <=0 ){

            LMember member = new LMember();
            member.setUserName(excelDto.getStudentNo());
            member.setName(excelDto.getName());
            member.setStudentNo(excelDto.getStudentNo());
            member.setIdCart(excelDto.getIdCart());
            member.setPhone(excelDto.getPhone());
            member.setProfession(excelDto.getProfession());

            memberMapper.insertSelective(member);
        }
        return mallMemberService.getMemberIdByUsername(excelDto.getStudentNo());
    }

    //组装数据
    private void assemblyOrderListList(List<Object> list) {
        if (list == null || list.size() < 5) return;

        if (orderExcelDto != null && orderExcelDto.getOrderSn() != null) orderList.add(orderExcelDto);
        //编码为空则跳过处理
        if (StringUtils.isEmpty(list.get(0))) return;
        //初始化容器
        orderExcelDto = new OrderExcelDto();


        orderExcelDto.setOrderSn(getJPVerifyStringValue(list.get(0), "编号" , "订单编码"));
        orderExcelDto.setStudentNo(getJPVerifyStringValue(list.get(1), "学号" , "学号"));
        orderExcelDto.setName(getJPVerifyStringValue(list.get(2), "编号" , "姓名"));
        orderExcelDto.setIdCart(getJPVerifyStringValue(list.get(3), "编号" , "身份证"));
        orderExcelDto.setPhone(getJPVerifyStringValue(list.get(4), "编号" , "电话"));
        orderExcelDto.setProfession(getJPVerifyStringValue(list.get(5), "编号" , "专业"));
        orderExcelDto.setRemark(getJPVerifyStringValue(list.get(6), "编号" , "订单明细"));

        String OrderAmount = getJPVerifyStringValue(list.get(7), "编号" , "订单金额");
        orderExcelDto.setOrderAmount(Double.valueOf(OrderAmount));
    }
    //获取经过空判断的字符串，为空则直接抛错
    private String getJPVerifyStringValue(Object data, String code, String DataDesc) {
        if (data != null && !StrUtil.isEmpty(data.toString())) {
            return String.valueOf(data);
        } else {
            throw new BadCredentialsException(code + "：" + DataDesc + "异常，导入终止");
        }
    }
}
