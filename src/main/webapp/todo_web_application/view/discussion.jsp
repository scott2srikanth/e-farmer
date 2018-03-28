<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/todo_web_application/common/header.jspf" %>
<%@include file="/todo_web_application/common/nav_logout.jspf" %> 

 

 
 <div class="container">
 	<H3 align="center">Discussion Panel</H3>
 	
 	<form method="POST" action="discussion.do">
	<fieldset class="form-group">
			<label>Topic</label> <input name="topic" type="text"
				class="form-control" required="" autofocus=""/> <BR />
		</fieldset>
		<fieldset class="form-group">
			<label>Description</label> <textarea rows="3" name="description" type="text"
				class="form-control" required="" autofocus=""/></textarea> <BR />
		</fieldset>
		
		<input name="add" type="submit" class="btn btn-success" value="Post Message" />
	</form>
	
	
	
	<br>
 	<table class="table table-striped">
		<caption>Discussion panel</caption>
		<tbody>
			<c:forEach items="${messages}" var="message">
			
				<tr>
					<td>
						<div style="float:left;width:50%;"><b>${message.topic}</b></div>
  						<div style="float:right;width:50%;">${message.time}</div>
  					</td>
				</tr>
				<tr>
					<td><i>${message.description}</i></td>
				</tr>
				<tr><td></td></tr>
				<tr><td></td></tr>
				
			</c:forEach>
		</tbody>
	</table>

	<p>
		<font color="red">${errorMessage}</font>
	</p>
 	
 </div>
 
