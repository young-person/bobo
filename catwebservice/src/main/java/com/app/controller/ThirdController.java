package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.utils.HttpUtil;

@RestController
@RequestMapping("third")
public class ThirdController {

	
	@GetMapping("eastmoney1")
	public String requestQuote(){
		return HttpUtil.doGetRequest("http://quote.eastmoney.com/stocklist.html");
	}

	@GetMapping("eastmoney2")
	public String requestQuote(String code){
		return HttpUtil.doGetRequest("http://quotes.money.163.com/service/chddata.html?code=0"+code+"&end=20161231&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP");
	}
	
}
