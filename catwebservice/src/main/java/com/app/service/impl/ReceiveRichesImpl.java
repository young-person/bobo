package com.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.config.CatXml;
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
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

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
    public void receiveRichesData(CopyOnWriteArraySet<RicheTarget> datas) {
		List<ExcelUser> users = this.getAllUsers();
		if (Objects.isNull(datas) || datas.size() == 0)
			return;
		for (ExcelUser user : users) {
			try {
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom(from);
				helper.setTo(InternetAddress.parse(user.getEmail()));
				StringBuilder builder = new StringBuilder();
				builder.append(LocalDate.now());
				builder.append("-");
				builder.append(catXml.getSendEmailSubject());
				helper.setSubject(builder.toString());

				Map<String, Object> model = new HashMap<>();
				model.put("datas", datas);
				model.put("title",catXml.getSendEmailSubject());
				Template template = templateEngine.getConfiguration().getTemplate(catXml.getEmailSubjectTemplate());
				String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
				helper.setText(text, true);
				javaMailSender.send(mimeMessage);
			} catch (IOException | TemplateException | MessagingException e) {
				e.printStackTrace();
			}
		}
    }


    public void writeUserToJson(List<ExcelUser> datas) throws IOException {
    	FileOutputStream stream = null;
    	try{
			File pFile = ResourceUtils.getFile(catXml.getDataPath());
			File file = new File(pFile, catXml.getUsersPath());
			if (file.exists()){
				file.delete();
			}
			file.createNewFile();
			stream = new FileOutputStream(file);
			JSON.writeJSONString(stream,datas, SerializerFeature.QuoteFieldNames);

		}finally {
    		IOUtils.closeQuietly(stream);
		}
    }

    public List<ExcelUser> getAllUsers(){
		List<ExcelUser> users = null;

		InputStream stream = null;
		try {
			File pFile = ResourceUtils.getFile(catXml.getDataPath());
			File file = new File(pFile, catXml.getUsersPath());
			if (!file.exists()){
				file.createNewFile();
			}
			stream = new FileInputStream(file);
			byte[] bytes = new byte[1024];

			int count = 0;
			StringBuilder builder = new StringBuilder();
			while ((count = stream.read(bytes))!=-1){
				builder.append(new String(bytes,0,count));
			}
			users = JSON.parseArray(builder.toString(), ExcelUser.class);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(stream);
		}
		if (Objects.isNull(users)){
			users = new ArrayList<>();
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
		if (Objects.nonNull(users)){
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
					if (Objects.isNull(data.getBeans()) || data.getBeans().size() == 0)
						continue;
					StringBuilder stringBuilder = new StringBuilder();
					boolean flag = false;
					for(Bean bean :data.getBeans()){

						OnlineBean onlineBean = bRiches.reloadOnlineData(bean.getCode());
						String price = onlineBean.getNewPrice();
						String minV = bean.getMark().split("-")[0];
						String maxV = bean.getMark().split("-")[1];

						switch (bean.getMark()) {
							/**
							 * 抄底 如果当前价格接近设置最大值的1至1.05倍 通知 当前价格在 最小值至最大值之间通知 如果当前值小于最小值 通知
							 */
							case "0":
								if (Float.valueOf(price) > Float.valueOf(minV) && Float.valueOf(price) <= Float.valueOf(maxV)){
									stringBuilder.append("【");
									stringBuilder.append(bean.getName());
									stringBuilder.append("-");
									stringBuilder.append("代码为：");
									stringBuilder.append(bean.getCode());
									stringBuilder.append("】");
									stringBuilder.append("已经符合您的买入预期【");
									stringBuilder.append(bean.getMark());
									stringBuilder.append("】,");
									stringBuilder.append("您可以查看此条数据详细情况！");
									stringBuilder.append("\n");
									flag = true;
								}else if(Float.valueOf(price) < Float.valueOf(minV)){
									stringBuilder.append("【");
									stringBuilder.append(bean.getName());
									stringBuilder.append("-");
									stringBuilder.append("代码为：");
									stringBuilder.append(bean.getCode());
									stringBuilder.append("】");
									stringBuilder.append("此数据已经超过您设定的买入预期【");
									stringBuilder.append(bean.getMark());
									stringBuilder.append("】,");
									stringBuilder.append("请及时关注操作！");
									stringBuilder.append("\n");
									flag = true;
								}else if(Float.valueOf(price) > Float.valueOf(maxV) && Float.valueOf(price) < Float.valueOf(maxV) *1.05){
									stringBuilder.append("【");
									stringBuilder.append(bean.getName());
									stringBuilder.append("-");
									stringBuilder.append("代码为：");
									stringBuilder.append(bean.getCode());
									stringBuilder.append("】");
									stringBuilder.append("此数据还有不到10%的增长就达到您的买入预期【");
									stringBuilder.append(bean.getMark());
									stringBuilder.append("】,");
									stringBuilder.append("请您近期注意观察！");
									stringBuilder.append("\n");
									flag = true;
								}
								break;
							case "1":
								if (Float.valueOf(price) > Float.valueOf(minV) && Float.valueOf(price) <= Float.valueOf(maxV)){
									stringBuilder.append("【");
									stringBuilder.append(bean.getName());
									stringBuilder.append("-");
									stringBuilder.append("代码为：");
									stringBuilder.append(bean.getCode());
									stringBuilder.append("】");
									stringBuilder.append("已经符合您的卖出预期【");
									stringBuilder.append(bean.getMark());
									stringBuilder.append("】,");
									stringBuilder.append("您可以查看此条数据详细情况！");
									stringBuilder.append("\n");
									flag = true;
								}else if(Float.valueOf(price) > Float.valueOf(maxV)){
									stringBuilder.append("【");
									stringBuilder.append(bean.getName());
									stringBuilder.append("-");
									stringBuilder.append("代码为：");
									stringBuilder.append(bean.getCode());
									stringBuilder.append("】");
									stringBuilder.append("此数据已经超过您设定的卖出预期【");
									stringBuilder.append(bean.getMark());
									stringBuilder.append("】,");
									stringBuilder.append("请及时关注操作！");
									stringBuilder.append("\n");
									flag = true;
								}else if(Float.valueOf(price)*1.1 > Float.valueOf(minV) ){
									stringBuilder.append("【");
									stringBuilder.append(bean.getName());
									stringBuilder.append("-");
									stringBuilder.append("代码为：");
									stringBuilder.append(bean.getCode());
									stringBuilder.append("】");
									stringBuilder.append("此数据还有不到10%的增长就达到您的卖出预期【");
									stringBuilder.append(bean.getMark());
									stringBuilder.append("】,");
									stringBuilder.append("请您近期注意观察！");
									stringBuilder.append("\n");
									flag = true;
								}
								break;
							default:
								break;
						}

					}
					if (flag){
						helper.setText(stringBuilder.toString());
						javaMailSender.send(mimeMessage);
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
