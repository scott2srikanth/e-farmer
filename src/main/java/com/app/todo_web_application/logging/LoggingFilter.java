package com.app.todo_web_application.logging;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.MDC;

/**
 * Servlet Filter implementation class LoggingFilter
 */
@WebFilter(filterName = "LoggingFilter")
public class LoggingFilter implements Filter {

   
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req =(HttpServletRequest) request;
	          //add the IP address
	          MDC.put("ipAddress", req.getRemoteAddr());
	          //add the hashed session ID
	          HttpSession session = req.getSession();
	          MDC.put("session", DigestUtils.sha1Hex(session.getId()));
	          //add the username
	          String username = (String) session.getAttribute("name");
	          if (username == null || "".equals(username.trim())) {
	            username = "Anonymous";
	          }
	          MDC.put("username", username);
	          //forward on to any other filters
	          chain.doFilter(request, response);
	          //clear the context, just to be safe
	          MDC.clear();
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
}
