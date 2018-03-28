package com.app.bulliten_board_application;
import java.sql.Date;

/**
 * Post bean 
 */
public class Post {
	
	
	private String author;
	private String title;
	private String message;
	private Date date;
	
	public Post(){
		
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
