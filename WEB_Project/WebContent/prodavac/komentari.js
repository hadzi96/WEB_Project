var komentari = angular.module('komentari', []);

komentari.controller('CtrlKomentari', function($scope, $window, ServiceKomentari) {
	$scope.hide = true;
	var cookie = {};
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	else{
		ServiceKomentari.getComments(cookie).then(function(response){
			$scope.resultSet = response.data;
			$scope.hide = false;
		});
		
	}	

});

komentari.factory('ServiceKomentari', [ '$http', function($http) {
	var service = {};
	
	service.getComments = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getCeomments',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}

	
	return service;
} ]);