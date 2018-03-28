package com.app.todo_web_application.registration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.todo_web_application.user.User;
import com.app.todo_web_application.user.UserAccountService;

/**
 * Servlet implementation class AccountUpdateServlet
 */
@WebServlet(urlPatterns="/account-update.do", initParams={@WebInitParam(name="basePath", value="/Users/SrikanthReddy")})
public class AccountUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(AccountUpdateServlet.class);

       
	@Resource(name = "jdbc/demo1")
    private DataSource ds;
	Connection conn;
   
	private UserAccountService userAccountService = new UserAccountService();
	private static final String SAVE_DIR = "uploadFiles";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int user_id=(int) request.getSession().getAttribute("user_id");
		String name=(String) request.getSession().getAttribute("name");
		String fileName=SAVE_DIR + File.separator + name+"_"+user_id+".png";
		
		try
		{
			LOG.trace("doGet : DBConnection Open");
			conn = ds.getConnection();
			LOG.info("doGet : Retrive accout details");
			request.setAttribute("user", userAccountService.retrieveAccountDetails(user_id,conn));
			request.setAttribute("fileName", fileName);
			LOG.info("doGet : Request from AccountUpdateServlet to accountUpdate.jsp");
			request.getRequestDispatcher("todo_web_application/view/accountUpdate.jsp").forward(request, response);

			
		}
		catch(SQLException e)
		{
			LOG.error("doGet : Catch block : SQLException : "+e.getMessage());
			log(e.getMessage(), e);
		}
		finally
		{
			try {
				conn.close();
				LOG.trace("doGet : DBConnection Close");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOG.error("doGet : Finally block : SQLException : "+e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullname = request.getParameter("fullname");
		String email = request.getParameter("email");
		int user_id= (int)request.getSession().getAttribute("user_id");
		
		
		String storedToken = (String)request.getSession().getAttribute("csrfToken");
        String token = request.getParameter("token");
        //do check
        if (storedToken.equals(token)) {

        	try
    		{
        		LOG.trace("doPost : DBConnection Open");
    			conn = ds.getConnection();
    			User user=new User();
    			user.setUser_id(user_id);
    			user.setFullname(fullname);
    			user.setEmail(email);
    			LOG.info("doPost : Update user data");
    			int update=userAccountService.updateUser(user,conn);
    			if(update==1)
    			{
    				LOG.info("doPost : Update done sucessfully");
    				request.setAttribute("success", "Updated");
    				LOG.info("doPost : Redirect from AccountUpdateServlet to AccountUpdateServlet");
    				response.sendRedirect("account-update.do");
    			}
    			else
    			{
    				LOG.info("doPost : Update failed");
    				request.setAttribute("error", "Update failed");
    				LOG.info("doPost : Redirect from AccountUpdateServlet to AccountUpdateServlet");
    				response.sendRedirect("account-update.do");
    				
    			}
    			
    			
    		}
    		catch(SQLException e)
    		{
    			LOG.error("doPost : Catch block : SQLException : "+e.getMessage());
    			log(e.getMessage(), e);
    		}
    		finally
    		{
    			try {
    				conn.close();
    				LOG.trace("doPost : DBConnection Close");
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				LOG.error("doPost : Finally block : SQLException : "+e.getMessage());
    				e.printStackTrace();
    			}
    		}
        	
        } else {
               	
        }
		
		
		
		
		
		
		
		
	}

}
