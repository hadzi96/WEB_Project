var changepw = angular.module('changepw', []);

changepw.controller('CtrlChangePW', function($scope, $window, ServiceChangePW) {
	
	$scope.message = "";
	$scope.change = function(){
		if($scope.parameter == null){
			$scope.message = "Error";
			return;
		}else{
			ServiceChangePW.changePw($scope.parameter).then(function(response){
				if(response.data == "true"){
					$window.location.href = "http://localhost:8080/WEB_Project/";
				}else{
					$scope.message = "Error";
				}
			});
		}
	}
	
	

});

changepw.factory('ServiceChangePW', [ '$http', function($http) {
	var service = {};
	
	service.changePw = function(parameter){
		return $http.post('http://localhost:8080/WEB_Project/server/user/changepw', parameter);
	}

	
	return service;
} ]);