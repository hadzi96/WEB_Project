var blockUsr = angular.module('blockUsr', []);

blockUsr.controller('CtrlBlockUsr', function($scope, $window, ServiceBlockUsr) {
	$scope.hide = true;
	var cookie = {};
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	else{
		ServiceBlockUsr.getUsers(cookie).then(function(response){
			$scope.resultSet = response.data;
			$scope.hide = false;
		});
	}
	
	
	
	$scope.message = "";
	$scope.block = function(username){
		console.log(username);
		if(username == null){
			$scope.message = "Error";
			return;
		}else{
			var param = {};
			param.username = username;
			ServiceBlockUsr.blockUsr(param, cookie).then(function(response){
				$window.location.href = "http://localhost:8080/WEB_Project/operater/blockuser.html";
			});
		}
	}
	
	

});

blockUsr.factory('ServiceBlockUsr', [ '$http', function($http) {
	var service = {};
	
	service.blockUsr = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/user/block', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getUsers = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getusers',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}

	
	return service;
} ]);