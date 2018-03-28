package com.app.todo_web_application.login;

import java.sql.*;

import com.app.todo_web_application.user.User;


public class LoginService {

	

	public boolean isUserValid(String name, String password, User user,Connection conn) {
		try {

			if (conn != null && !conn.isClosed()) {
				ResultSet rs = null;

				Statement stmt = conn.createStatement();
				String sql = "select * from login where username='" + name + "' and password='" + password + "'";
				System.out.println(sql);
				rs = stmt.executeQuery(sql);

				if (rs != null && rs.next()) {
					user.setUser_id(rs.getInt("user_id"));
					return true;

				}

			}

		} catch (SQLException e) {
			// log(e.getMessage(), e);
			e.printStackTrace();
		}
		return false;
	}

}
