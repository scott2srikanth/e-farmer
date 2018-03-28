<%@include file="/todo_web_application/common/header.jspf" %>
<%@include file="/todo_web_application/common/nav_logout.jspf" %> 

<div class="container">
<H1 align="center">TODO Add Action Item</H1>
	Your New Action Item:
	<form method="POST" action="todoAdd.do">
	<fieldset class="form-group">
			<label>Category</label> <input name="category" type="text"
				class="form-control" /> <BR />
		</fieldset>
		<fieldset class="form-group">
			<label>Description</label> <textarea rows="6" name="todo" type="text"
				class="form-control" /></textarea> <BR />
		</fieldset>
		
		<input name="add" type="submit" class="btn btn-success" value="Submit" />
	</form>
</div>

<%@include file="/todo_web_application/common/footer.jspf" %>