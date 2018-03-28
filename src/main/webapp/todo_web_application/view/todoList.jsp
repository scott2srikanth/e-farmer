<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/todo_web_application/common/header.jspf"%>
<%@include file="/todo_web_application/common/nav_logout.jspf"%>


<div class="container">
	<H1 align="center">TODO List</H1>
	
	
<form class="form-search" action="todoSearch.do" >
		<div class="input-append">
			<input type="text" class="search-query input-medium" name="keyword">
			<button type="submit" class="btn btn-large">Search</button>
		</div>
	</form>

	


	<table class="table table-striped">
		<caption>Your Todos are</caption>
		<thead>
			<th>Description</th>
			<th>Category</th>
			<th>Actions</th>
		</thead>
		<tbody>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.description}</td>
					<td>${todo.category}</td>
					<td>&nbsp;&nbsp;<a class="btn btn-danger"
						href="todoDelete.do?todo_id=${todo.todo_id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<p>
		<font color="red">${errorMessage}</font>
	</p>
	<a class="btn btn-success" href="todoAdd.do">Add New Todo</a>

</div>

<%@include file="/todo_web_application/common/footer.jspf"%>