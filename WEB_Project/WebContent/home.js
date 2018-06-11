var home = angular.module('home', ['ngCookies']);


home.controller('CtrlHome', function($scope, $window, ServiceHome) {
	
	$scope.getKorpa = function() {
		var cookie = {};
		cookie = $window.localStorage.getItem("webProj");
		
		ServiceHome.getKorpa(cookie).then(function(response){
			$scope.message = response.data;
			
		});
	};
});

home.factory('ServiceHome', [ '$http', function($http) {
	var service = {};
	
	service.getKorpa = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/korpa/getKorpa', {

		    headers: {'Authorization': 'WebProject='+cookie}

		  });
	}
	
	return service;
} ]);