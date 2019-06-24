package com.app.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class FreeMakerUtils {
	private static Configuration configuration = null;
	private FreeMakerUtils(){}
	private static final String defaultPath;
	static{
		configuration = new Configuration(Configuration.VERSION_2_3_0);
		configuration.setDefaultEncoding("UTF-8");
		defaultPath = FreeMakerUtils.class.getClass().getResource("/").getPath()+"dictionary/word/";
	}
	
	public static File createDoc(Map<String, Object> dataMap, String templateName) {
		File outFile = null;
		try {
			configuration.setDirectoryForTemplateLoading(new File(defaultPath));
            //获取模板 
            Template template = configuration.getTemplate(templateName+".ftl");
            //输出文件
            outFile = new File(templateName+".doc");
            //将模板和数据模型合并生成文件 
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            //生成文件
            template.process(dataMap, out);
            //关闭流
            out.flush();
            out.close();
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
        return outFile;
    }
	public static Template getTemplate(String name) {
		Template tmp = null;
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
		// 指定模板路径
		File file = new File("");
		try {
			cfg.setDirectoryForTemplateLoading(file);
			tmp = cfg.getTemplate(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	public static void fprintFreeMakerTemp(String name, Map<String, Object> rootMap,String outFile) {
		FileWriter out = null;
		try {
			out = new FileWriter(new File(outFile));
			Template tmp = getTemplate(name);
			tmp.process(rootMap, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
