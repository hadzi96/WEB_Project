var home = angular.module('home', []);

home.controller('CtrlHome', function($scope, $window, ServiceHome) {
	var cookie = {};
	var userLock = {};
	$scope.searchResult = [];
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	ServiceHome.getUserLock(cookie).then(function(response){
		if(response.data == -1){
			$window.location.href = "http://localhost:8080/WEB_Project/";
		}else{
			$scope.hide = false;
		}
		userLock = response.data;
		console.log(userLock);
	});
	
	$scope.search = function() {
		var parameter = {};
		parameter = angular.copy($scope.parameter);
		parameter.offset = 0;
		ServiceHome.search(parameter, cookie).then(function(response){
			$scope.searchResult = response.data;
		});
	};

});

home.factory('ServiceHome', [ '$http', function($http) {
	var service = {};
	
	service.getUserLock = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getlock', {
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.search = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/search', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	return service;
} ]);