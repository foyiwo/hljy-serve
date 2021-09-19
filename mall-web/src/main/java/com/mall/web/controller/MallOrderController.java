package com.mall.web.controller;


import com.mall.common.api.CommonResult;
import com.mall.mbg.Model.LMember;
import com.mall.mbg.Model.LOrder;
import com.mall.web.dto.LoginResultDto;
import com.mall.web.param.MemberLoginParam;
import com.mall.web.param.OrderQueryParam;
import com.mall.web.service.MallMemberService;
import com.mall.web.service.MallOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(tags = "订单管理")
@Controller
@RequestMapping(value = "/order")
public class MallOrderController {

    @Autowired
    private MallOrderService mallOrderService;

    @ApiOperation(value = "缴费记录")
    @RequestMapping(value = "/list/all", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult listAll()
    {
        try {
            List<LOrder> result = mallOrderService.orderList(null);

            return CommonResult.success(result);
        }catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }

    }

    @ApiOperation(value = "待缴费用")
    @RequestMapping(value = "/list/pay", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult list()
    {
        try {
            OrderQueryParam queryParam = new OrderQueryParam();
//            queryParam.setPatStatus(1);
            List<LOrder> result = mallOrderService.orderList(queryParam);
            return CommonResult.success(result);
        }catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }

    }

    @ApiOperation(value = "缴费记录导入Excel")
    @PostMapping(value = "/importOrderByExcel")
    @ResponseBody
    public CommonResult importOrderByExcel(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            //读取Excel文件数据
            String result = mallOrderService.importOrderByExcel(excelFile.getInputStream());
            return CommonResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

}
