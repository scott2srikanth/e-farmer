package com.app.bulliten_board_application;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.Properties;

import com.mysql.jdbc.Driver;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;    
	private static String dbUsername;
	private static String dbPassword;
	private static String dbUrl;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties config = new Properties();
		config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

		 dbUrl = config.getProperty("url");
		 dbUsername = config.getProperty("username");
		 dbPassword = config.getProperty("password");
		
		String inputUsername = request.getParameter("uname");
		String inputPassword = request.getParameter("pass");
		UserData user = new UserData();
		boolean gotUser = false;
		
		try
		{
			user = getUserFromDB(inputUsername);
			if(user != null)
				gotUser = true;
			else
				response.getWriter().write("USERNAME OR PASSWORD INVALID");
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		
		//If we got the users data, check it then issue a cookie
		if(gotUser)
		{
			PasswordTools pt = new PasswordTools();
			String storedHash = user.getPassword();
			
			String salt = user.getSalt();
			String hash = pt.get_SHA_512_SecurePassword(inputPassword, salt);
			
			if(storedHash.equals(hash)){
				//create our cookie and set it's expiration to 30 minutes
				Cookie cookie = new Cookie("user",user.getUsername());
				cookie.setMaxAge(30*60);			
				response.addCookie(cookie);
				response.sendRedirect("/MessageBoard");
			}
			else
			{
				response.getWriter().write("[{\"Error\":\"Bad Password Or Username.\"}]");
			}
		}
		else{
			response.getWriter().write("[{\"Error\":\"Can't Find User\"}]");
		}		
	}
	
	UserData getUserFromDB(String username) throws SQLException{
		new Driver();
		Connection con = null;
		con = DriverManager.getConnection(dbUrl+"?"+"user="+dbUsername+"&password="+dbPassword);
		String query = "SELECT * FROM USERS WHERE USERNAME=?;";
		PreparedStatement ps = null;
		ps = con.prepareStatement(query);
		ps.setString(1,username);
		ResultSet result = null;
		result = ps.executeQuery();
		if(result.next())
		{
			UserData user = new UserData();
			user.setUsername(result.getString(1));
			user.setEmail(result.getString(2));
			user.setPassword(result.getString(3));
			user.setSalt(result.getString(4));
			con.close();
			return user;
		}else
		{
			con.close();
			return null;
		}
	}
}


