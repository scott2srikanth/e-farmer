var app = angular.module("usersApp",[]);

app.controller("RegisterUser",function($scope,$http){
	
})



/**
 * Accepts a form and hashes the password
 */

function processLogin(){
	var form = document.getElementById("login");
	var username = form.elements[0].value;
	var password = form.elements[1].value;
	
	var shaObj = new jsSHA("SHA-512","TEXT");
	shaObj.update(password);
	var hashedpw = shaObj.getHash("HEX");
	
	form.elements[1].value = hashedpw;
	
	form.submit();
	
}

function processRegister(){
	var form = document.getElementById("register");
	var username = form.elements[0].value;
	var email = form.elements[1].value;
	var password = form.elements[2].value;
	
	var shaObj = new jsSHA("SHA-512","TEXT");
	shaObj.update(password);
	var hashedpw = shaObj.getHash("HEX");
	
	form.elements[2].value = hashedpw;
	
	form.submit();
}