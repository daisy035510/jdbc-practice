package org.exmaple;

import org.exmaple.User;

import java.sql.*;


public class UserDao {

    public void create(User user) throws SQLException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES(?, ?, ?, ?)";

        // 익명 클래스 사용 -> 람다로 변경 가능
        jdbcTemplate.executeUpdate(user, sql, pss -> {
            pss.setString(1, user.getUserId());
            pss.setString(2, user.getPassword());
            pss.setString(3, user.getName());
            pss.setString(4, user.getEmail());
        });
    }


    public User findByUserId(String userId) throws SQLException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";

        return (User) jdbcTemplate.executeQuery(sql,
                pss -> pss.setString(1, userId),
                resultSet -> new User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                ));
    }
}