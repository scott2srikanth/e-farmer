package com.app.todo_web_application.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class UserAccountService {

private static List<User> userList = new ArrayList<User>();
	
	public List<User> retrieveAccountDetails(int user_id,Connection conn) {
		userList.clear();
		try {
			 
			if (conn != null && !conn.isClosed()) {
				ResultSet rs = null;

				Statement stmt = conn.createStatement();
				String sql = "select * from login where user_id=" + user_id;
				System.out.println(sql);
				rs = stmt.executeQuery(sql);

				if (rs != null) {
					
					while (rs.next()) {
						User user=new User();
						user.setUser_id(rs.getInt("user_id"));
						user.setUsername(rs.getString("username"));
						user.setFullname(rs.getString("fullname"));
						user.setEmail(rs.getString("email"));
						userList.add(user);
			        }  
					
				}
					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
	
	public int updateUser(User user, Connection conn) {
		int update=0;
		try {
			if (conn != null && !conn.isClosed()) {

				Statement stmt = conn.createStatement();
				
				String sql = "update login set fullname='"+user.getFullname()+"', email='"+user.getEmail()+"' where user_id="+user.getUser_id();
				System.out.println(sql);
				update=stmt.executeUpdate(sql);
	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return update;
	}
	
	
}
