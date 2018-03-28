<%@ page isErrorPage="true" import="java.io.*" contentType="text/plain"%>
<%@include file="/todo_web_application/common/header.jspf" %>
<%@include file="/todo_web_application/common/nav_login.jspf" %> 


<div class="container">
 
 	Message:
<%=exception.getMessage()%>
<p>
Issue has been created and notified to developer/administrator.....
</p>

 
 </div>



<%@include file="/todo_web_application/common/footer.jspf" %>

