package com.app.todo_web_application.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.app.todo_web_application.user.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
	
	@Resource(name = "jdbc/demo1")
    private DataSource ds;
	Connection conn;
	
   private LoginService userValidation =new LoginService();
   User user=new User();
	
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("doGet : Request from LoginServlet to login.jsp");
	   request.getRequestDispatcher("todo_web_application/view/login.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		LOG.info("doPost : Request from login.jsp to validate login");
		LOG.trace("Requested User : "+name);
		try
		{
			conn = ds.getConnection();
		
			boolean isUserValid=userValidation.isUserValid(name, password,user,conn);
			if(isUserValid)
			{
				LOG.info("User validated sucessfully");

				request.getSession().setAttribute("name", name);
				System.out.println(user.getUser_id());
				request.getSession().setAttribute("user_id", user.getUser_id());
				request.getSession().setAttribute("csrfToken", generateCSRFToken());
				//String sessionid = request.getSession().getId();
				//response.setHeader("Set-Cookie", "JSESSIONID=" +  sessionid + ";");
				LOG.info("doPost : Redirect from LoginServlet to TodoServlet");
				response.sendRedirect("todoList.do");			
			}
			else
			{
				request.setAttribute("error", "Invalid credientials");
				LOG.info("User validation failed");
				LOG.info("doPost : Request from LoginServlet to login.jsp");
				request.getRequestDispatcher("todo_web_application/view/login.jsp").forward(request, response);

			}
		}
		catch(SQLException e)
		{
			log(e.getMessage(), e);
		}
	}
	
	public static String generateCSRFToken() {
		String token = UUID.randomUUID().toString();
		return token;
	}
	
	

}
