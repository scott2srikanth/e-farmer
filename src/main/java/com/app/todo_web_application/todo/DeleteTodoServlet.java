package com.app.todo_web_application.todo;

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
 * Servlet implementation class DeleteTodoServlet
 */
@WebServlet("/todoDelete.do")
public class DeleteTodoServlet extends HttpServlet {
	@Resource(name = "jdbc/demo1")
    private DataSource ds;
	Connection conn;
	private static final long serialVersionUID = 1L;
       
	private TodoService todoService = new TodoService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//int user_id=Integer.parseInt((String) request.getSession().getAttribute("user_id"));
		int todo_id=Integer.parseInt((String) request.getParameter("todo_id"));
		try
		{
			conn = ds.getConnection();
			todoService.deleteTodo(todo_id,conn);
			response.sendRedirect("todoList.do");
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

}
