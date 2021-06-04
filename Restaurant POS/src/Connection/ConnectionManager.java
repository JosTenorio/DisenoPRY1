/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import com.google.common.collect.ImmutableList;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author JOS
 */
public class ConnectionManager {
    private HikariDataSource connectionPool;
    
    public void setUpPool() throws SQLException {

        // Initialize connection pool
        HikariConfig config = new HikariConfig();
        config
            .setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
        config.setUsername("sqlserver"); // e.g. "root", "sqlserver"
        config.setPassword("BigRoo85"); // e.g. "my-password"
        config.addDataSourceProperty("databaseName", "SmrPrototype ");

        config.addDataSourceProperty("socketFactoryClass",
            "com.google.cloud.sql.sqlserver.SocketFactory");
        config.addDataSourceProperty("socketFactoryConstructorArg", "hallowed-trail-313100:us-central1:sgr-prototype");
        config.setConnectionTimeout(10000); // 10s

        this.connectionPool = new HikariDataSource(config);
    }
    
    public Connection getConnection() throws SQLException {
        setUpPool();
        Connection conn = connectionPool.getConnection();    
        return conn;
    }
}