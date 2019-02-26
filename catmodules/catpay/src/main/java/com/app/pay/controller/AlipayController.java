package com.app.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.app.properties.PropertiesForFile;
import com.bobo.base.BaseClass;
import com.bobo.domain.ResultMeta;
import com.bobo.utils.ComUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 支付宝
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController extends BaseClass {
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayTradeWapPayRequest alipayTradeWapPayRequest;

    @Autowired
    private AlipayTradePagePayRequest alipayTradePagePayRequest;
    // 电脑网站支付
    @RequestMapping("/pay/pc")
    @ResponseBody
    public Object pc() throws Exception {
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "pay2017" + System.currentTimeMillis());
        bizContent.put("total_amount", "0.01");
        bizContent.put("subject", "Iphone6 16G");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        alipayTradePagePayRequest.setBizContent(bizContent.toString());
        return alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
    }

    // 手机网站支付
    @RequestMapping("/pay/wap")
    @ResponseBody
    public Object wap() throws Exception {
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "pay2017" + System.currentTimeMillis());
        bizContent.put("total_amount", "0.01");
        bizContent.put("subject", "Iphone6 16G");
        bizContent.put("product_code", "QUICK_WAP_PAY");
        alipayTradeWapPayRequest.setBizContent(bizContent.toString());
        return alipayClient.pageExecute(alipayTradeWapPayRequest).getBody();
    }

    // 异步通知
    @RequestMapping("/notifyUrl")
    @ResponseBody
    public Object notifyUrl(HttpServletRequest request) throws Exception {
        Map<String, String> parameterMap = ComUtils.getParameterMap(request);
        // 验签
        ResultMeta meta = new ResultMeta();
        boolean signVerified = AlipaySignature.rsaCheckV1(
                parameterMap, PropertiesForFile.getInstance().getAlipay_public_key(),
                PropertiesForFile.getInstance().getCharset(),
                PropertiesForFile.getInstance().getSign_type());
        if (!signVerified) {
            return meta.failure("支付宝对接验证失败");
        }
        return meta.success("验证成功");
    }
}
