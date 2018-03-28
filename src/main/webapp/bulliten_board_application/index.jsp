<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>


<head>
<link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">

<script src="js/msgBoard.js"></script>
<script src="js/sha512.js"></script>
<script src="js/pwdHash.js"></script>
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">MessageBoard</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
      </ul>
      <ul class="nav navbar-nav navbar-right">
		<li class="dropdown">
		
		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Hello, ${name} <span class="caret"></span></a>
		  <ul class="dropdown-menu">
		
		<c:choose>
    		<c:when test="${name=='Guest'}">
        	<form action="LoginServlet" id="login" method="post">
			<input type="text" placeholder="Username" name="uname" size="30" />
			<input type="password" placeholder="Password" name="pass" size="30" />
			<div class="login-btn-group">		
			<input class="btn btn-primary"  type="submit" onClick="processLogin()" value="Sign In" />
			<input type="button" class="btn btn-success" data-toggle="modal" data-target="#registerUser" value="Register"/>
			</div>
			</form>
   			</c:when>    
    		<c:otherwise>
        		<li><a href="LogoutServlet">Logout</a></li>
    		</c:otherwise>
		</c:choose>

		  </ul>
		</li>

      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="container">
	<div class="row text-center">
		<!-- Button to trigger new post modal -->
		<c:choose>
    		<c:when test="${name=='Guest'}">
        	
   			</c:when>    
    		<c:otherwise>
		<button type="button" class="btn btn-success" data-toggle="modal" ng-show="loggedIn" data-target="#newPost"><span class="glyphicon glyphicon-plus"></span></button>
    		</c:otherwise>
		</c:choose>
		<a class="btn btn-info" ng-click="getData()"><span class="glyphicon glyphicon-refresh"></span></a>
	</div>
	<br/>
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
		
		<c:forEach items="${posts}" var="post">
			
				<div class="panel panel-default">
					<div class="panel-heading ">
						<h3 class="panel-title"><strong>${post.title}</strong></h3>
						<p><strong>${post.author}</strong>, on ${post.date}</p>
					</div>
					<div class="panel-body">
						<p>${post.message}</p>
					</div>
				</div>
				
			</c:forEach>
		</div>
	</div>
</div>



<!-- New Post Modal -->
<div class="modal fade" id="newPost" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">New Post</h4>
      </div>
      <div class="modal-body">
        <form action="MessageBoard.do" method="POST" class="form-horizontal">
  			<input class="form-control" type="text" maxlength = "32" placeholder="Title" name="title"/>
  			<textarea class="form-control" placeholder="Message" rows="3" maxlength="140" name="message"></textarea>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
         <input type="submit" class="btn btn-primary" value="Post">
		 </form>
      </div>
    </div>
  </div>
</div>

<!-- Register User Modal -->
<div class="modal fade" id="registerUser" tabindex="-1" role="dialog" aria-labelledby="registerUserLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Register User</h4>
      </div>
      <div class="modal-body">
        <form id="register" action="RegisterUser" method="POST" class="form-horizontal">
			<input class="form-control" maxlength="16" type="text" placeholder="Username" name="uname"></input>
			<input class="form-control" type="email" placeholder="Email" name="email"></input>
			<input class="form-control" type="password" placeholder="Password" name="pass"></input>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
         <input type="submit" onclick="processRegister()" class="btn btn-success" value="Register">
		 </form>
      </div>
    </div>
  </div>
</div>

<footer class="container">
	<div class="row">
		<div class="col-md-4">
		<p>Need an account?</p>
		<p><a type="button" class="btn btn-success" data-toggle="modal" data-target="#registerUser">Register</a></p>	
		</div>
	</div>
</footer>
<script src="../bootstrap/js/jquery.min.js"></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>
</body>
</html>