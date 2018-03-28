package com.app.todo_web_application.registration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationService {
	
	public boolean isUserRegistered(String user,String fullname,String email, String password, Connection conn) {
		boolean usernameExists=true;
		try {

			if (conn != null && !conn.isClosed()) {
				ResultSet rs = null;

				Statement stmt = conn.createStatement();
				String sql = "select * from login where username='" + user + "'";
				System.out.println(sql);
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					System.out.println("aaa");
				  usernameExists = true;
				}
				else
				{
					System.out.println("sss");
					String insertSql = "insert into login (username,fullname,email, password) values('" + user + "','" + fullname + "','" + email + "', '"+password+"');";
					stmt.executeUpdate(insertSql);
					usernameExists=false;
					return usernameExists;
				}

			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usernameExists;
		
	}

}
