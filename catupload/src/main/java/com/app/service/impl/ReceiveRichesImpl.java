package com.app.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.app.config.EmailConfig;
import com.app.pojo.ExcelUser;
import com.app.pojo.RicheTarget;
import com.app.runner.ApplicationRunnerImpl;
import com.app.service.ExcelService;
import com.app.service.ExcelService.CallBack;
import com.app.service.ReceiveRiches;
import com.bobo.base.BaseClass;

@Service
public class ReceiveRichesImpl extends BaseClass implements ReceiveRiches {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	@Autowired
	private EmailConfig emailConfig;

	@Override
	public void receiveRichesData(List<RicheTarget> datas) {

		this.receiveRichesData(datas, new ExcelService() {

			@Override
			public File getOutFile() {
				return null;
			}

			@Override
			public String getName() {
				return ApplicationRunnerImpl.CAT_CACHE.get("shareName").getValue();
			}

			@Override
			public File getInPath() {
				try {
					return ResourceUtils.getFile(ApplicationRunnerImpl.CAT_CACHE.get("usersPath").getValue());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		
		this.writeReceiveRichesData(datas, new ExcelService() {

			@Override
			public File getOutFile() {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
					String date = dateFormat.format(new Date());
					return new File(ResourceUtils.getFile(ApplicationRunnerImpl.CAT_CACHE.get("sharePath").getValue()),date+".xlsx");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public String getName() {
				return ApplicationRunnerImpl.CAT_CACHE.get("shareName").getValue();
			}

			@Override
			public File getInPath() {
				if (getOutFile().exists()) {
					LOGGER.info("{}文件已经存在",getInPath().getAbsoluteFile());
					getOutFile().delete();
				}
				try {
					return ResourceUtils.getFile(ApplicationRunnerImpl.CAT_CACHE.get("sharePath").getValue());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	/**
	 * 写入推荐数据到excel
	 * @param list
	 * @param datas
	 * @throws MessagingException
	 */
	private void receiveRichesData(List<RicheTarget> datas, ExcelService excelService) {
		List<ExcelUser> list = new ArrayList<ExcelUser>();
		excelService.readDataToExcel(new CallBack<ExcelUser>() {

			@Override
			public void writeExcel(int index, ExcelUser bean, Sheet sheet) {
				Row row = sheet.getRow(index);
				String v0 = row.getCell(0).getStringCellValue();
				String v1 = row.getCell(1).getStringCellValue();
				String v2 = row.getCell(2).getStringCellValue();
				String v3 = row.getCell(3).getStringCellValue();
				String v4 = row.getCell(4).getStringCellValue();
				String v5 = row.getCell(5).getStringCellValue();
				ExcelUser excelUser = new ExcelUser();
				excelUser.setId(v0);
				excelUser.setAccount(v1);
				excelUser.setUserName(v2);
				excelUser.setPassWord(v3);
				excelUser.setEmail(v4);
				excelUser.setForbidden(v5);
				list.add(excelUser);
			}
			
		});
		this.contextLoads(list, datas);
	}
	
	private void writeReceiveRichesData(List<RicheTarget> datas, ExcelService excelService) {
		excelService.writeDataToExcel(datas, (index, bean, sheet) -> {
			Row row = sheet.createRow(index+1);
			row.createCell(0).setCellValue(bean.getStockName());
			row.createCell(1).setCellValue(bean.getRisePrice());
			row.createCell(2).setCellValue(bean.getL());
			row.createCell(3).setCellValue(bean.getHand());
			row.createCell(4).setCellValue(bean.getDetailUrl());
		});
		
	}
	/**
	 * 发送邮件
	 * @param list
	 * @param datas
	 * @throws MessagingException
	 */
	private void contextLoads(List<ExcelUser> list,List<RicheTarget> datas) {
		for(ExcelUser excelUser : list) {
			try {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
				mimeMessageHelper.setFrom(emailConfig.getFrom());
				mimeMessageHelper.setTo(excelUser.getEmail());
				mimeMessageHelper.setSubject(ApplicationRunnerImpl.CAT_CACHE.get("sendEmailSubject").getValue());
				Context ctx = new Context();
				ctx.setVariable("datas", datas);
				ctx.setVariable("title", ApplicationRunnerImpl.CAT_CACHE.get("sendEmailSubject").getValue());
				String emailText = templateEngine.process(ApplicationRunnerImpl.CAT_CACHE.get("emailSubjectTemplate").getValue(), ctx);
				mimeMessageHelper.setText(emailText, true);
				mailSender.send(mimeMessage);
			} catch (MessagingException e) {
				LOGGER.error("发送邮件失败：接收人地址：【】",excelUser.getEmail(),e);
			}

		}

	}
}
