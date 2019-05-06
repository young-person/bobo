package com.bobo.dbconnection;

import com.mybatis.pojo.Dbs;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionHolderDefault implements ConnectionHolder {
	private static final int LIMIT = 500;

	@Override
	public List<Map<String, Object>> query(Dbs db, String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Class.forName(db.getDriver());
			connection = DriverManager.getConnection(db.getUrl(),db.getUsername(), db.getPassword());
			logger.info(sql);
			statement = connection.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			statement.setFetchSize(LIMIT);
			resultSet = statement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int count = metaData.getColumnCount();
			String[] name = new String[count];
			for (int i = 0; i < count; i++) {
				name[i] = metaData.getColumnLabel(i+1);
			}
			while (resultSet.next()) {
				Map<String, Object> m = new HashMap<String, Object>();
				for (int i = 0; i < count; i++) {
					Object obj = resultSet.getObject(name[i]);
					String columnName = name[i];
					m.put(columnName, obj);
				}
				list.add(m);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			close(connection,statement,resultSet);
		}
		return list;
	}

	@Override
	public int update(Dbs db, String sql) {
		Connection connection = null;
		Statement statement = null;
		int cnt = 0;
		try {
			Class.forName(db.getDriver());
			connection = DriverManager.getConnection(db.getUrl(),db.getUsername(), db.getPassword());
			statement = connection.createStatement();
			logger.info(sql);
			cnt = statement.executeUpdate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			close(connection,statement,null);
		}
		return cnt;
	}

	@Override
	public boolean delete(Dbs db, String sql) {
		Connection connection = null;
		Statement statement = null;
		boolean cnt = false;
		try {
			Class.forName(db.getDriver());
			connection = DriverManager.getConnection(db.getUrl(),db.getUsername(), db.getPassword());
			statement = connection.createStatement();
			logger.info(sql);
			cnt = statement.execute(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			close(connection,statement,null);
		}
		return cnt;
	}

	@Override
	public void save(Dbs db, String sql) {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName(db.getDriver());
			connection = DriverManager.getConnection(db.getUrl(),db.getUsername(), db.getPassword());
			statement = connection.createStatement();
			logger.info(sql);
			statement.execute(sql);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			close(connection,statement,null);
		}
	}
	
	@Override
	public boolean execute(Dbs db, String sql) {
		Connection connection = null;
		Statement statement = null;
		boolean cnt = false;
		try {
			Class.forName(db.getDriver());
			connection = DriverManager.getConnection(db.getUrl(),db.getUsername(), db.getPassword());
			statement = connection.createStatement();
			logger.info(sql);
			cnt = statement.execute(sql);
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("连接信息:"+connection.toString()+statement.toString());
			e.printStackTrace();
		}finally{
			close(connection,statement,null);
		}
		return cnt;
	}
	
	@Override
	public int queryForInt(Dbs db, String sql) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		int cnt = 0;
		try {
			Class.forName(db.getDriver());
			connection = DriverManager.getConnection(db.getUrl(),db.getUsername(), db.getPassword());
			statement = connection.createStatement();
			logger.info(sql);
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				cnt = resultSet.getInt(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			close(connection,statement,resultSet);
		}
		return cnt;
	}
	private void close(Connection connection, Statement statement,ResultSet resultSet) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public boolean executeAtomicity(Dbs db, String sql) {
		Connection connection = null;
		Statement statement = null;
		boolean cnt = false;
		try {
			Class.forName(db.getDriver());
			connection = DriverManager.getConnection(db.getUrl(),db.getUsername(), db.getPassword());
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.createStatement();
			logger.info(sql);
			cnt = statement.execute(sql);
			connection.commit();
			cnt = true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			close(connection,statement,null);
		}
		return cnt;
	}
}
