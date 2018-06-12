var home = angular.module('home', []);

home.controller('CtrlHome', function($scope, $window, ServiceHome) {
	var cookie = {};
	var userLock = {};
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	ServiceHome.getUserLock(cookie).then(function(response){
		if(response.data == -1){
			$window.location.href = "http://localhost:8080/WEB_Project/";
		}
		userLock = response.data;
		console.log(userLock);
	});
	
	
	
	$scope.getKorpa = function() {
		
		
	};
});

home.factory('ServiceHome', [ '$http', function($http) {
	var service = {};
	
	service.getKorpa = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/korpa/getKorpa', {

		    headers: {'Authorization': 'WebProject='+cookie}

		  });
	}
	
	service.getUserLock = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getlock', {

		    headers: {'Authorization': 'WebProject='+cookie}

		  });
	}
	
	return service;
} ]);