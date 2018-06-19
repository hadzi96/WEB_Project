var statistika = angular.module('statistika', []);

statistika.controller('CtrlStatistika', function($scope, $window, ServiceStatistika) {
	$scope.hide = true;
	var cookie = {};
	cookie = $window.localStorage.getItem("webProj");
	var test = JSON.parse($window.localStorage.getItem("test"));
	
	if(cookie == null || test == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	else{
		ServiceStatistika.getStat(cookie).then(function(response){
			$scope.statistic = response.data;
			$scope.statistic.prosecnaCena = Math.round($scope.statistic.prosecnaCena * 100) / 100;
			$scope.hide = false;
		});
	}
	
	

});

statistika.factory('ServiceStatistika', [ '$http', function($http) {
	var service = {};
	
	service.getStat = function( cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/statistika/get',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	return service;
} ]);