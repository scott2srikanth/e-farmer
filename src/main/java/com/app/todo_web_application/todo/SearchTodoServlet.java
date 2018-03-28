package com.app.todo_web_application.todo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class SearchTodoServlet
 */
@WebServlet("/todoSearch.do")
public class SearchTodoServlet extends HttpServlet {
	
	@Resource(name = "jdbc/demo1")
    private DataSource ds;
	Connection conn;
	
	
	
	private TodoService todoService = new TodoService();
	private static final long serialVersionUID = 1L;
      private List<Todo> searchList =null;
      
 
  	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		int user_id=(int) request.getSession().getAttribute("user_id");
		String keyword=request.getParameter("keyword");
		try
		{
			conn = ds.getConnection();
			request.setAttribute("keyword", keyword);
			searchList=todoService.searchTodos(user_id,keyword,conn);
			request.setAttribute("searchSize", searchList.size());
			request.setAttribute("todos1", searchList);
			
			//response.setHeader("X-XSS-Protection", "1; mode=block");
			
			request.getRequestDispatcher("todo_web_application/view/todoSearch.jsp").forward(request, response);
			

			
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
		//doGet(request, response);
	}

}
