var app = angular.module('messageBoard', ['ngCookies']);
	
app.controller('messagesController',['$scope','$cookies','$http', function($scope,$cookies, $http) {
	
	var username = $cookies.get('user');
	if(username == null || username == ""){
		$scope.username = "Guest";
		$scope.loggedIn = false;
	}
	else{
		$scope.username = username;
		$scope.loggedIn = true;
	}
	$http.get('MessageBoard').then(function(response){
		$scope.data = response.data;
	},function(){
		var data = [{"author":"Admin","title":"ERROR","message":"There was an error getting the messages from the server!","date":"Dec 31, 1969"}];
		$scope.data=data;
	});
	
	$scope.getData = function(){
		$http.get('MessageBoard').then(function(response){
			$scope.data = response.data;
		},function(){
			var data = [{"author":"Admin","title":"ERROR","message":"There was an error getting the messages from the server!","date":"Dec 31, 1969"}];
			$scope.data=data;
		});
	}
}]);
app.filter('reverse', function() {
  return function(items) {
	  	if(items != null)
	    return items.slice().reverse();
	  };
	});