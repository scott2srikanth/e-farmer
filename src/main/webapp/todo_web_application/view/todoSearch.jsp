<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/todo_web_application/common/header.jspf" %>
<%@include file="/todo_web_application/common/nav_logout.jspf" %> 

 

 
 <div class="container">
 	<H1 align="center">TODO Search List</H1>
 	<table class="table table-striped">
		<caption>Searched for <b>${keyword}</b> and found <b>${searchSize}</b> todo's</caption>
		<thead>
			<th>Description</th>
			<th>Category</th>
		</thead>
		<tbody>
			<c:forEach items="${todos1}" var="todo">
				<tr>
					<td>${todo.description}</td>
					<td>${todo.category}</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<p>
		<font color="red">${errorMessage}</font>
	</p>
	<a class="btn btn-success" href="todoList.do">Back</a>
 	
 </div>
 
 <%@include file="/todo_web_application/common/footer.jspf" %>