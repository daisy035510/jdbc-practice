package org.exmaple;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 커넥션 관련된 건 커낵션 manager가 모두 관리하도록 처리함
 */
public class ConnectionManager {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PW = "";
    private static final int MAX_POOL_SIZE = 40;
    private static final DataSource ds;

    // DataSource 는 1개이기 때문에 static으로 초기화  -> 커넥션풀을 하나만 가지도록 설정을 함
    static {

        /**
         * jdbc에서 핵심포인트 -> HikariDataSource
         */
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(DB_DRIVER); // 변수 자동 생성 : 커맨드 + alt + c
        hikariDataSource.setJdbcUrl(DB_URL);
        hikariDataSource.setUsername(DB_USERNAME);
        hikariDataSource.setPassword(DB_PW);
        hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE); // 얼마만큼 커넥션을 가질 수 있는지 설정 가능함
        hikariDataSource.setMinimumIdle(MAX_POOL_SIZE); // to-do 확인 필요

        ds = hikariDataSource;

    }


    // static method로 설정해줘도 상관없다
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }

}
