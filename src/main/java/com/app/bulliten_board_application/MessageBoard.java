package com.app.bulliten_board_application;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.sql.Date;
import java.sql.*;
//import com.google.gson.Gson;
import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class MessageBoard
 */
@WebServlet("/MessageBoard.do")
public class MessageBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String dbUsername;
	private static String dbPassword;
	private static String dbUrl;

	/**
	 * @throws SQLException
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageBoard() throws SQLException {

		super();
		// TODO Auto-generated constructor stub
		new Driver();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String author = "";
		try {
			Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("user")) {
						author = cookies[i].getValue();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (author == null || author == "") {
			request.setAttribute("name", "Guest");
		} else {
			request.setAttribute("name", author);
		}

		Properties config = new Properties();
		config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

		dbUrl = config.getProperty("url");
		dbUsername = config.getProperty("username");
		dbPassword = config.getProperty("password");

		// response.setContentType("application/json");
		// Gson gson = new Gson();
		try {
			ArrayList<Post> posts = getMessages();
			System.out.println(posts.get(0).getDate());
			Collections.sort(posts, new Comparator<Post>() {
				public int compare(Post o1, Post o2) {
					return o2.getDate().compareTo(o1.getDate());
				}
			});
			System.out.println(posts.get(0).getDate());
			request.setAttribute("posts", posts);
			// response.getWriter().write(gson.toJson(posts));
			request.getRequestDispatcher("index.jsp").forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Properties config = new Properties();
		config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

		dbUrl = config.getProperty("url");
		dbUsername = config.getProperty("username");
		dbPassword = config.getProperty("password");

		Post post = new Post();

		Cookie[] cookies = request.getCookies();
		String author = "";
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("user")) {
				author = cookies[i].getValue();
			}
		}
		if (author == null || author == "") {
			response.sendRedirect("/MessageBoard");
		} else {
			post.setAuthor(author);
			post.setTitle(request.getParameter("title"));
			post.setMessage(request.getParameter("message"));

			Date d = new Date(System.currentTimeMillis());
			post.setDate(d);

			try {
				postMessage(post);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.sendRedirect("/MessageBoard");
		}
	}

	private static void postMessage(Post p) throws SQLException {

		Connection conn = null;
		conn = DriverManager.getConnection(dbUrl + "?" + "user=" + dbUsername + "&password=" + dbPassword);

		PreparedStatement ps = null;
		String query = "INSERT INTO posts (author,title,body,datePosted) VALUES (?,?,?,?)";

		ps = conn.prepareStatement(query);
		ps.setString(1, p.getAuthor());
		ps.setString(2, p.getTitle());
		ps.setString(3, p.getMessage());
		ps.setDate(4, p.getDate());

		ps.executeUpdate();

		conn.close();

	}

	private static ArrayList<Post> getMessages() throws SQLException {

		Connection conn = null;

		conn = DriverManager.getConnection(dbUrl + "?" + "user=" + dbUsername + "&password=" + dbPassword);
		Statement stmt = conn.createStatement();
		String query = "SELECT * FROM posts";

		ResultSet rs = stmt.executeQuery(query);

		ArrayList<Post> posts = new ArrayList<Post>();

		while (rs.next()) {
			Post p = new Post();
			p.setAuthor(rs.getString("author"));
			p.setTitle(rs.getString("title"));
			p.setMessage(rs.getString("body"));
			p.setDate(rs.getDate("datePosted"));

			posts.add(p);
		}
		rs.close();
		stmt.close();
		conn.close();
		return posts;

	}

}
