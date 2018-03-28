package com.app.todo_web_application.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.commons.validator.routines.RegexValidator;

/**
 * Servlet Filter implementation class LoginValidationFilter
 */
@WebFilter(filterName = "LoginValidationFilter")
public class LoginValidationFilter implements Filter {

   
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		System.out.println("Filter LoginValidate");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		RegexValidator regexn = new RegexValidator(new String[]{"[a-zA-Z0-9\\._\\-]{3,}"});
		RegexValidator regexp = new RegexValidator(new String[]{"[a-zA-Z0-9\\._\\-\\@\\!\\#\\$\\%\\&\\*]{3,}"});
		
		if (name == null || "".equals(name)
                || password == null || "".equals(password)) {

            request.setAttribute("error", "One or both fields are empty");
            request.getRequestDispatcher("view/login.jsp").include(request, response);

        } else if (regexn.isValid(name) || regexp.isValid(password)) {
            
        	chain.doFilter(request, response);
        } else {
        	request.setAttribute("error", "Input error");
            request.getRequestDispatcher("view/login.jsp").include(request, response);

        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
