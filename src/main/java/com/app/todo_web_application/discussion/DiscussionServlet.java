package com.app.todo_web_application.discussion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



/**
 * Servlet implementation class DiscussionServletå
 */
@WebServlet("/discussion.do")
public class DiscussionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/demo1")
    private DataSource ds;
	Connection conn;
	
	private DiscussionService discussionService = new DiscussionService();   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			conn = ds.getConnection();
			request.setAttribute("messages", discussionService.retrieveMessage(conn));
			request.getRequestDispatcher("todo_web_application/view/discussion.jsp").forward(request, response);
			
		}
		catch(SQLException e)
		{
			log(e.getMessage(), e);
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String description = request.getParameter("description");
		String topic = request.getParameter("topic");
		if (topic!=null && description!=null) {
		    
			   try
				{
					conn = ds.getConnection();
					Message m=new Message();
					m.setTopic(topic);
					m.setDescription(description);
					discussionService.addMessage(m,conn);
					response.sendRedirect("discussion.do");
					
				}
				catch(SQLException e)
				{
					log(e.getMessage(), e);
				}
				finally
				{
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}   
		else
		{
			request.setAttribute("error", "Confirmation password did not match");
			request.getRequestDispatcher("todo_web_application/view/registration.jsp").forward(request, response);
		}
	}

}
