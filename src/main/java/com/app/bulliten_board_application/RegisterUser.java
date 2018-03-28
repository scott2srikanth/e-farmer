package com.app.bulliten_board_application;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserData u = new UserData();
		if(usernameValid(request.getParameter("uname"))&&emailValid(request.getParameter("email")))
		{
			try {
				u = AddUser(request.getParameter("uname"),request.getParameter("email"),request.getParameter("pass"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(u != null){
				//Success
				response.sendRedirect("/MessageBoard");
			}
			else{
				response.getWriter().write("Uh oh, something went wrong!!");
			}
		}		
		else
		{
			response.getWriter().write("Username or Email is invalid");
		}
		
	}
	
	UserData AddUser(String username, String email, String passHash) throws SQLException{
		new Driver();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/mboard_db?"+"user=root&password=17121981");
		String query = "SELECT * FROM USERS WHERE USERNAME=?;";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet result = null;
		ps.setString(1, username);
		result = ps.executeQuery();
		if(!result.next()){
			PasswordTools pt = new PasswordTools();
			//Create our user bean and fill it with info.
			UserData user = new UserData();
			
			user.setSalt(pt.GenerateSalt());
			user.setUsername(username);
			user.setEmail(email);
			String storedHash = pt.get_SHA_512_SecurePassword(passHash, user.getSalt());
			user.setPassword(storedHash);
			
			PreparedStatement insertPS = null;
			query = "INSERT INTO USERS (USERNAME,EMAIL,PASSWORDHASH,SALT)VALUES(?,?,?,?);";
			try{
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mboard_db?"+"user=root&password=17121981");
				insertPS = con.prepareStatement(query);
				insertPS.setString(1, user.getUsername());
				insertPS.setString(2, user.getEmail());
				insertPS.setString(3, user.getPassword());
				insertPS.setString(4, user.getSalt());
				insertPS.executeUpdate();
				con.close();
				//USER ADDED
				return user;
			} catch (SQLException e) {e.printStackTrace();}	
			return null;
		}
		else
		{
			return null;
		}
	}
	
	boolean usernameValid(String username) {
		Pattern regex = Pattern.compile("^[A-Za-z0-9_-]{3,15}$");
	    Matcher matcher = regex .matcher(username);
        return matcher.matches();
	}
	boolean emailValid(String email) {
		Pattern regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = regex .matcher(email);
        return matcher.find();
	}

}
