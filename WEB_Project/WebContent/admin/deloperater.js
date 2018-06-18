var delOp = angular.module('delOp', []);

delOp.controller('CtrlDelOp', function($scope, $window, ServiceDelOp) {
	$scope.hide = true;
	var cookie = {};
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	else{
		ServiceDelOp.getOperaters(cookie).then(function(response){
			$scope.resultSet = response.data;
			$scope.hide = false;
		});
	}
	
	
	
	$scope.message = "";
	$scope.delOp = function(username){
		if(username == null){
			$scope.message = "Brisanje neuspesno";
			return;
		}else{
			var param = {};
			param.username = username;
			ServiceDelOp.delOp(param, cookie).then(function(response){
				if(response.data == "true"){
					$window.location.href = "http://localhost:8080/WEB_Project/admin/deloperater.html";
				}else{
					$scope.message = "Brisanje neuspesno";
				}
			});
		}
	}
	
	

});

delOp.factory('ServiceDelOp', [ '$http', function($http) {
	var service = {};
	
	service.delOp = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/user/deloperater', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getOperaters = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getoperaters',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}

	
	return service;
} ]);