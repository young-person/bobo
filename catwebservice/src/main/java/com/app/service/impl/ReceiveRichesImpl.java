package com.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.app.config.CatXml;
import com.app.crawler.base.RCache;
import com.app.crawler.riches.BRiches;
import com.app.crawler.riches.RicheTarget;
import com.app.crawler.riches.pojo.Bean;
import com.app.crawler.riches.pojo.Data;
import com.app.crawler.riches.pojo.ExcelUser;
import com.app.crawler.riches.pojo.OnlineBean;
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
import java.util.*;

@Service
public class ReceiveRichesImpl extends BaseClass implements ReceiveRiches {

	private CatXml catXml = new CatXml();

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${mail.from}")
    private String from;

    @Autowired
    private FreeMarkerConfigurer templateEngine;

    @Override
    public void receiveRichesData(List<RicheTarget> datas) {
		List<ExcelUser> users = this.getAllUsers();
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

    public List<ExcelUser> getAllUsers(){
		List<ExcelUser> users = null;
		File pFile = new File(catXml.getDataPath());
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
			users = new ArrayList<>();
		}finally {
			IOUtils.closeQuietly(stream);
		}
		return users;
	}
	/**
	 * 发送监控数据
	 */
	@Override
	public void sendScheduleData() {
		RLoadXml loadXml = new RLoadXml();
		Data data = loadXml.getDataFromXml(catXml.getDataPath());
		BRiches bRiches = new BRiches();

		List<ExcelUser> users = this.getAllUsers();
		for (ExcelUser user : users) {
			try {
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom(from);
				helper.setTo(InternetAddress.parse(user.getEmail()));
				StringBuilder builder = new StringBuilder("【");
				builder.append(LocalDate.now());
				builder.append("-");
				builder.append(catXml.getSendEmailSubject());
				helper.setSubject(builder.toString());

				for(Bean bean :data.getBeans()){
					OnlineBean onlineBean = bRiches.reloadOnlineData(bean.getCode());
					String price = onlineBean.getNewPrice();
					String minV = bean.getMark().split("-")[0];
					String maxV = bean.getMark().split("-")[1];
					String msg = null;
					if (Float.valueOf(price) > Float.valueOf(minV) && Float.valueOf(price) <= Float.valueOf(maxV)){
						msg = "已经符合您的预期，您可以查看此条数据详细情况！";
					}else if(Float.valueOf(price) > Float.valueOf(maxV)){
						msg = "此数据已经超过您设定的预期，请及时关注操作！";
					}else if(Float.valueOf(price)*1.1 > Float.valueOf(minV) ){
						msg = "此数据还有不到10%的增长就达到您的预期，请您近期注意观察！";
					}
					if (Objects.nonNull(msg)){
						helper.setText(msg);
						javaMailSender.send(mimeMessage);
					}
				}

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}




	}
}
