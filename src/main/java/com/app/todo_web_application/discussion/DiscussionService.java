package com.app.todo_web_application.discussion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiscussionService {
		
	private static List<Message> messages = new ArrayList<Message>();
	
	public List<Message> retrieveMessage(Connection conn) {
		messages.clear();
		try {
			 
			if (conn != null && !conn.isClosed()) {
				ResultSet rs = null;

				Statement stmt = conn.createStatement();
				String sql = "select * from message";
				System.out.println(sql);
				rs = stmt.executeQuery(sql);

				if (rs != null) {
					
					while (rs.next()) {
						Message m=new Message();
						m.setMessage_id(rs.getInt("message_id"));
						m.setTopic(rs.getString("topic"));
						m.setDescription(rs.getString("description"));
						m.setTime(rs.getString("time"));
						messages.add(m);
			        }  
					
				}
					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(messages.size());
		return messages;
	}

	public void addMessage(Message message, Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {

				Statement stmt = conn.createStatement();
				String sql = "insert into message(topic,description,time) values('"+message.getTopic()+"','"+message.getDescription()+"', now())";
				System.out.println(sql);
				int insert = stmt.executeUpdate(sql);
				if(insert==1)
				{
					messages.add(message);
				}	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
