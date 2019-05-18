
package com.app.distributed.service.coordinator.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.app.distributed.CatParticipant;
import com.app.distributed.CatTransaction;
import com.app.distributed.config.CatConfig;
import com.app.distributed.config.MysqlConfig;
import com.app.distributed.service.coordinator.CoordinatorRepository;
import com.bobo.enums.JTAEnum;
import com.bobo.serializer.CObjectSerializer;
import com.bobo.utils.ComUtils;
import org.apache.commons.collections.CollectionUtils;

import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class JdbcCoordinatorRepository implements CoordinatorRepository {

    private DruidDataSource dataSource;

    private String tableName;

    private CObjectSerializer serializer;

    @Override
    public void setSerializer(final CObjectSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public int create(final CatTransaction transaction) {
        StringBuilder sql = new StringBuilder()
                .append("insert into ")
                .append(tableName)
                .append("(trans_id,target_class,target_method,retried_count,create_time,last_time,version,status,invocation,role,error_msg)")
                .append(" values(?,?,?,?,?,?,?,?,?,?,?)");
        try {
            final byte[] serialize = serializer.serialize(transaction.getParticipants());
            return executeUpdate(sql.toString(),
                    transaction.getTransId(),
                    transaction.getTargetClass(),
                    transaction.getTargetMethod(),
                    transaction.getRetriedCount(),
                    transaction.getCreateTime(),
                    transaction.getLastTime(),
                    transaction.getVersion(),
                    transaction.getStatus(),
                    serialize,
                    transaction.getRole(),
                    transaction.getErrorMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return JTAEnum.ERROR.getCode();
        }
    }

    @Override
    public int remove(final String transId) {
        String sql = "delete from " + tableName + " where trans_id = ? ";
        return executeUpdate(sql, transId);
    }

    @Override
    public int update(final CatTransaction transaction) throws RuntimeException {
        final Integer currentVersion = transaction.getVersion();
        transaction.setLastTime(new Date());
        transaction.setVersion(transaction.getVersion() + 1);
        String sql = "update " + tableName + " set last_time = ?,version =?,retried_count =?,invocation=?,status=?  where trans_id = ? and version=? ";
        try {
            final byte[] serialize = serializer.serialize(transaction.getParticipants());
            return executeUpdate(sql,
                    transaction.getLastTime(),
                    transaction.getVersion(),
                    transaction.getRetriedCount(),
                    serialize,
                    transaction.getStatus(),
                    transaction.getTransId(),
                    currentVersion);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateFailTransaction(final CatTransaction transaction) throws RuntimeException {
        String sql = "update " + tableName + " set  status=? ,error_msg=? ,retried_count =?,last_time = ?   where trans_id = ?  ";
        transaction.setLastTime(new Date());
        executeUpdate(sql, transaction.getStatus(),
                transaction.getErrorMsg(),
                transaction.getRetriedCount(),
                transaction.getLastTime(),
                transaction.getTransId());
    }

    @Override
    public void updateParticipant(final CatTransaction transaction) throws RuntimeException {
        String sql = "update " + tableName + " set invocation=?  where trans_id = ?  ";
        try {
            final byte[] serialize = serializer.serialize(transaction.getParticipants());
            executeUpdate(sql, serialize, transaction.getTransId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int updateStatus(final String id, final Integer status) throws RuntimeException {
        String sql = "update " + tableName + " set status=?  where trans_id = ?  ";
        return executeUpdate(sql, status, id);
    }

    @Override
    public CatTransaction findByTransId(final String transId) {
        String selectSql = "select * from " + tableName + " where trans_id=?";
        List<Map<String, Object>> list = executeQuery(selectSql, transId);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().filter(Objects::nonNull)
                    .map(this::buildByResultMap).collect(Collectors.toList()).get(0);
        }
        return null;
    }

    @Override
    public List<CatTransaction> listAllByDelay(final Date date) {
        String sb = "select * from " + tableName + " where last_time < ?  and status = " + JTAEnum.BEGIN.getCode();
        List<Map<String, Object>> list = executeQuery(sb, date);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream()
                    .filter(Objects::nonNull)
                    .map(this::buildByResultMap)
                    .collect(Collectors.toList());
        }
        return null;
    }

    private CatTransaction buildByResultMap(final Map<String, Object> map) {
        CatTransaction mythTransaction = new CatTransaction();
        mythTransaction.setTransId((String) map.get("trans_id"));
        mythTransaction.setRetriedCount((Integer) map.get("retried_count"));
        mythTransaction.setCreateTime((Date) map.get("create_time"));
        mythTransaction.setLastTime((Date) map.get("last_time"));
        mythTransaction.setVersion((Integer) map.get("version"));
        mythTransaction.setStatus((Integer) map.get("status"));
        mythTransaction.setRole((Integer) map.get("role"));
        byte[] bytes = (byte[]) map.get("invocation");
        try {
            final List<CatParticipant> participants = serializer.deSerialize(bytes, CopyOnWriteArrayList.class);
            mythTransaction.setParticipants(participants);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mythTransaction;
    }

    @Override
    public void init(final String modelName, final CatConfig catConfig) {
        MysqlConfig config = catConfig.getDbConfig();
        dataSource = new DruidDataSource();
        dataSource.setUrl(config.getUrl());
        dataSource.setDriverClassName(config.getDriverClass());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setInitialSize(config.getInitialSize());
        dataSource.setMaxActive(config.getMaxActive());
        dataSource.setMinIdle(config.getMinIdle());
        dataSource.setMaxWait(config.getMaxWait());
        dataSource.setValidationQuery(config.getValidationQuery());
        dataSource.setTestOnBorrow(config.getTestOnBorrow());
        dataSource.setTestOnReturn(config.getTestOnReturn());
        dataSource.setTestWhileIdle(config.getTestWhileIdle());
        dataSource.setPoolPreparedStatements(config.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(config.getMaxPoolPreparedStatementPerConnectionSize());
        this.tableName = ComUtils.buildDbTableName(modelName);
        executeUpdate(buildCreateTableSql(config.getDriverClass(), tableName));
    }

    @Override
    public String getScheme() {
        return CatConfig.create().getRepositorySupport();
    }

    private int executeUpdate(final String sql, final Object... params) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("executeUpdate-> " + e.getMessage());
        }
        return 0;
    }

    private List<Map<String, Object>> executeQuery(final String sql, final Object... params) {
        List<Map<String, Object>> list = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                int columnCount = md.getColumnCount();
                list = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> rowData = new HashMap<>(16);
                    for (int i = 1; i <= columnCount; i++) {
                        rowData.put(md.getColumnName(i), rs.getObject(i));
                    }
                    list.add(rowData);
                }
            }
        } catch (SQLException e) {
            logger.error("executeQuery-> " + e.getMessage());
        }
        return list;
    }

    public String buildCreateTableSql(final String driverClassName, final String tableName) {
        StringBuilder createTableSql = new StringBuilder();
        String dbType = "mysql";
        if (driverClassName.contains("mysql")) {
            dbType = "mysql";
        } else if (driverClassName.contains("sqlserver")) {
            dbType = "sqlserver";
        } else if (driverClassName.contains("oracle")) {
            dbType = "oracle";
        }
        switch (dbType) {
            case "mysql":
                createTableSql.append("CREATE TABLE `")
                        .append(tableName).append("` (\n")
                        .append("  `trans_id` varchar(64) NOT NULL,\n")
                        .append("  `target_class` varchar(256) ,\n")
                        .append("  `target_method` varchar(128) ,\n")
                        .append("  `retried_count` int(3) NOT NULL,\n")
                        .append("  `create_time` datetime NOT NULL,\n")
                        .append("  `last_time` datetime NOT NULL,\n")
                        .append("  `version` int(6) NOT NULL,\n")
                        .append("  `status` int(2) NOT NULL,\n")
                        .append("  `invocation` longblob,\n")
                        .append("  `role` int(2) NOT NULL,\n")
                        .append("  `error_msg` text ,\n")
                        .append("   PRIMARY KEY (`trans_id`),\n")
                        .append("   KEY  `status_last_time` (`last_time`,`status`) USING BTREE \n")
                        .append(")");
                break;

            case "oracle":
                createTableSql
                        .append("CREATE TABLE `")
                        .append(tableName)
                        .append("` (\n")
                        .append("  `trans_id` varchar(64) NOT NULL,\n")
                        .append("  `target_class` varchar(256) ,\n")
                        .append("  `target_method` varchar(128) ,\n")
                        .append("  `retried_count` int(3) NOT NULL,\n")
                        .append("  `create_time` date NOT NULL,\n")
                        .append("  `last_time` date NOT NULL,\n")
                        .append("  `version` int(6) NOT NULL,\n")
                        .append("  `status` int(2) NOT NULL,\n")
                        .append("  `invocation` BLOB ,\n")
                        .append("  `role` int(2) NOT NULL,\n")
                        .append("  `error_msg` CLOB  ,\n")
                        .append("  PRIMARY KEY (`trans_id`)\n")
                        .append(")");
                break;
            case "sqlserver":
                createTableSql
                        .append("CREATE TABLE `")
                        .append(tableName)
                        .append("` (\n")
                        .append("  `trans_id` varchar(64) NOT NULL,\n")
                        .append("  `target_class` varchar(256) ,\n")
                        .append("  `target_method` varchar(128) ,\n")
                        .append("  `retried_count` int(3) NOT NULL,\n")
                        .append("  `create_time` datetime NOT NULL,\n")
                        .append("  `last_time` datetime NOT NULL,\n")
                        .append("  `version` int(6) NOT NULL,\n")
                        .append("  `status` int(2) NOT NULL,\n")
                        .append("  `invocation` varbinary ,\n")
                        .append("  `role` int(2) NOT NULL,\n")
                        .append("  `error_msg` varchar(8000) ,\n")
                        .append("  PRIMARY KEY (`trans_id`)\n")
                        .append(")");
                break;
            default:
                throw new RuntimeException("dbType类型不支持,目前仅支持mysql oracle sqlserver.");
        }
        return createTableSql.toString();
    }
}
