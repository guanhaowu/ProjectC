package com.bramgussekloo.projects.statements;

import com.bramgussekloo.projects.Security.SecurityConfig;
import com.bramgussekloo.projects.database.DatabaseConnection;
import com.bramgussekloo.projects.dataclasses.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserStatements {
    public static ArrayList<User> getAllUsers() throws SQLException {
        Connection conn = new DatabaseConnection().getConnection();
        ArrayList<User> list = new ArrayList<>();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users");
        ResultSet result = preparedStatement.executeQuery();
        if (!result.next()) {
            throw new SQLException("No data in database");
        } else {
            do {
                list.add(getResult(result.getInt("id"), result));
            } while (result.next());
            return list;
        }
    }

    public static User createUser(User user) throws SQLException {
        boolean userExists = false;
        try {
            ArrayList<User> users = getAllUsers();
            for (User userFromList : users) {
                if (user.getUser_name().equals(userFromList.getUser_name())) {
                    userExists = true;
                    break;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Can't check if user already exists or not: " + e.getMessage());
        }
        if (!userExists) {
            if (!user.getPassword().isEmpty() && user.getPassword().length() > 6) {
                Connection conn = new DatabaseConnection().getConnection();
                User newUser = SecurityConfig.HashUserPassword(user);
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?)");
                preparedStatement.setString(1, newUser.getUser_name());
                preparedStatement.setString(2, newUser.getPassword());
                preparedStatement.setString(3, newUser.getAuthority());
                preparedStatement.setBoolean(4, newUser.getEnabled());
                preparedStatement.execute();
                return newUser;
            } else {
                throw new SQLException("Password is not long enough. It has to be at least a length of 6.");
            }
        } else {
            throw new SQLException("User already exists.");
        }
    }

    private static User deleteUser(String username){
        return new User();
    }

    private static User getResult(Integer id, ResultSet resultSet) throws SQLException {
        String user_nameResult = resultSet.getString("user_name");
        String passwordResult = resultSet.getString("password");
        String authorityResult = resultSet.getString("authority");
        Boolean enabledResult = resultSet.getBoolean("enabled");
        return new User(id, user_nameResult, passwordResult, authorityResult, enabledResult);
    }
}
