package com.bobo.dbconnection;

import java.util.List;
import java.util.Map;


public interface ConnectionHolder<T>{
	public List<Map<String, Object>> query(T Dbs, String sql);
	
	public int update(T dbs, String sql);
	
	public boolean delete(T dbs, String sql);
	
	public void save(T dbs, String sql);
	
	public boolean execute(T dbs, String sql);

	public int queryForInt(T dbs, String sql);
	
	public boolean executeAtomicity(T dbs, String sql);
}
