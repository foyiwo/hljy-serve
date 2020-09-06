package com.mall.web.controller;

import com.github.pagehelper.util.StringUtil;
import com.mall.common.api.CommonResult;
import com.mall.web.dto.OrderStatusDto;
import com.mall.web.param.OrderPayParam;
import com.mall.web.service.MallPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "前端支付管理接口", description = "BdmPaymentController")
@Controller
@RequestMapping(value = "/Pay")
public class MallPaymentController {

    @Autowired
    private MallPaymentService mallPaymentService;


    /**
     * 成功的标识
     */
    private final static String SUCCESS="SUCCESS";

    /**
     * 返回状态码的变量名
     *
     */
    private final static String RETURN_CODE="RETURN_CODE";

    //private static Logger logger = LoggerFactory.getLogger(BdmPaymentController.class);



    @ApiOperation(value = "微信支付统一下单订单", notes = "订单支付")
    @RequestMapping(value = "/wxPay",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult wxPay(@RequestBody OrderPayParam input) {

        try {
            return CommonResult.success(mallPaymentService.getOrderPayConfig(input,false));
        }catch (Exception e){
            return CommonResult.failed(e.getMessage());
        }
    }

    @ApiOperation(value = "微信支付成功回调",notes = "微信支付成功异步回调,修改订单信息")
    @RequestMapping(value = "/wxNotifyResult",method = RequestMethod.POST)
    public void notifyResultGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("微信回调requestGet===>"+request);
       mallPaymentService.notifyResult(request,response);
    }

    @ApiOperation(value = "轮询是否已经支付")
    @GetMapping("/findIsPay")
    @ResponseBody
    public CommonResult findOrderIsPay(@RequestParam String orderSn){
        //logger.info("产品订单的orderSn ====> " + orderSn+" 用户openId ====> "+code+"  小程序支付开始");
        try {
            if(!StringUtil.isEmpty(orderSn)){
                OrderStatusDto orderStatus = mallPaymentService.findOrderIsPay(orderSn);
                //支付状态：0->未支付；1->已支付;
                if (!StringUtils.isEmpty(orderStatus)){
                    return CommonResult.success(orderStatus);
                }
            }
        }catch (Exception ex){
            return CommonResult.failed(ex.getMessage());
        }
        return CommonResult.failed();
    }

}
