package org.exmaple;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 라이브러리성
 */
public class JdbcTemplate {
    /**
     * PreparedStatement 대신에 PreparedStatementSetter를 사용하는 이유는
     * Connection 객체를 바깥에 선언하는 것을 방지하기 위해서이다
     */

    public void executeUpdate(User user, String sql, PreparedStatementSetter pss) throws SQLException {

        // to-do try-with-resource
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstat = con.prepareStatement(sql)) {
            pss.setter(pstat);
            pstat.executeUpdate();

        }
    }

    /**
     * TO-DO :try-with-resource로 변경
     * return 값이 Object인 이유는 User에 종속이 걸리기 때문
     */
    public Object executeQuery(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {


//        try (Connection con = ConnectionManager.getConnection();
//             PreparedStatement pstat = con.prepareStatement(sql);
//             ResultSet rs = pstat.executeQuery()) {
//
//            pss.setter(pstat);
//
//            Object obj = null;
//            if(rs.next()) {
//                return rowMapper.map(rs);
//            }
//            return obj;
//
//        }

        Connection con          = ConnectionManager.getConnection();
        PreparedStatement pstat = con.prepareStatement(sql);
        ResultSet rs            = null;

        try {
            con = ConnectionManager.getConnection();
            pstat = con.prepareStatement(sql);
            pss.setter(pstat);

            rs = pstat.executeQuery();

            Object obj = null;
            if(rs.next()) {
                return rowMapper.map(rs);
            }
            return obj;
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(con != null) {
                pstat.close();
            }
            if(pstat != null) {
                pstat.close();
            }
        }
    }
}
