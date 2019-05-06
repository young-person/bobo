package com.bobo.dbconnection;

import com.mybatis.pojo.Dbs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public interface ConnectionHolder {
	static Logger logger = LoggerFactory.getLogger(ConnectionHolder.class);
	public List<Map<String, Object>> query(Dbs Dbs, String sql);
	
	public int update(Dbs dbs, String sql);
	
	public boolean delete(Dbs dbs, String sql);
	
	public void save(Dbs dbs, String sql);
	
	public boolean execute(Dbs dbs, String sql);

	public int queryForInt(Dbs dbs, String sql);
	
	public boolean executeAtomicity(Dbs dbs, String sql);
}
