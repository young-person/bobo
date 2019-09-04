package com.app.crawler.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.mysql.cj.jdbc.Driver;

/** 
 * @Description: TODO
 * @date 2019年7月9日 下午12:07:41 
 * @ClassName: TextToDb 
 */
public class TextToDb {

	private DataSource dataSource;
	
	public TextToDb(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public interface TextFilter{
		
		void parseLineText(String text);
		
	}
	public interface InDbFilter{
		
		void parseTextToDB(PreparedStatement ps,int i,List<Map<String, Object>> datas);
		
	}
	public void readTextLine(String filePath,TextFilter filter) {
		
		File file = new File(filePath);
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String text = null;
			while((text = reader.readLine()) != null) {
				filter.parseLineText(text);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void datasToDb(final List<Map<String, Object>> datas,String sql,InDbFilter filter) {
		if (datas != null && datas.size() > 0) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					filter.parseTextToDB(ps, i, datas);
				}
				@Override
				public int getBatchSize() {
					return datas.size();
				}
			});
		}
	}
	
	public void writeToTxt(String cxt,String file) {
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File(file);
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(cxt);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToSql(String cxt,String file) {
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File(file);
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(cxt);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
