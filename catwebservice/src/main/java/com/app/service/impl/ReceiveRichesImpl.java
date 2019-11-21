package com.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.app.crawler.base.RCache;
import com.app.crawler.riches.RicheTarget;
import com.app.crawler.riches.pojo.Bean;
import com.app.crawler.riches.pojo.Data;
import com.app.crawler.riches.pojo.ExcelUser;
import com.app.crawler.riches.producer.RLoadXml;
import com.app.service.ReceiveRiches;
import com.bobo.base.BaseClass;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReceiveRichesImpl extends BaseClass implements ReceiveRiches {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${mail.from}")
    private String from;

    @Autowired
    private FreeMarkerConfigurer templateEngine;

    @Override
    public void receiveRichesData(List<RicheTarget> datas) {
		List<ExcelUser> users = null;
		File pFile = new File(RCache.CAT_CACHE.get("dataPath").getValue());
		InputStream stream = null;
		try {
			File file = new File(pFile, "users.json");
			if (!file.exists()){
				file.createNewFile();
			}
			stream = new FileInputStream(file);
			users = JSON.parseObject(stream, ExcelUser.class);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(stream);
		}

		for (ExcelUser user : users) {
			try {
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom(from);
				helper.setTo(InternetAddress.parse(user.getEmail()));
				StringBuilder builder = new StringBuilder("【");
				builder.append(LocalDate.now());
				builder.append("-");
				builder.append(RCache.CAT_CACHE.get("sendEmailSubject").getValue());
				helper.setSubject(builder.toString());

				Map<String, Object> model = new HashMap<>();
				model.put("datas", datas);
				model.put("title", RCache.CAT_CACHE.get("sendEmailSubject").getValue());
				Template template = templateEngine.getConfiguration().getTemplate(RCache.CAT_CACHE.get("emailSubjectTemplate").getValue());
				String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
				helper.setText(text, true);
				javaMailSender.send(mimeMessage);
			} catch (IOException | TemplateException | MessagingException e) {
				e.printStackTrace();
			}
		}
    }

	/**
	 * 发送监控数据
	 */
	@Override
	public void sendScheduleData() {
		RLoadXml loadXml = new RLoadXml();
		Data data = loadXml.getDataFromXml(RCache.CAT_CACHE.get("dataPath").getValue());
		for(Bean bean :data.getBeans()){

		}
	}
}
