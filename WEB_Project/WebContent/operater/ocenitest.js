var oceniTest = angular.module('oceniTest', []);

oceniTest.controller('CtrlOceniTest', function($scope, $window, ServiceOceniTest) {
	$scope.hide = true;
	var cookie = {};
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	else{
		ServiceOceniTest.getNeocenjene(cookie).then(function(response){
			$scope.resultSet = response.data;
			$scope.hide = false;
		});
	}
	
	
	$scope.open = function(test){
		$window.localStorage.setItem("test",JSON.stringify(test));
		$window.location.href = "http://localhost:8080/WEB_Project/operater/test.html";
	}
	
	
	

});

oceniTest.factory('ServiceOceniTest', [ '$http', function($http) {
	var service = {};
	
	service.getNeocenjene = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/test/getneocenjene',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}

	
	return service;
} ]);