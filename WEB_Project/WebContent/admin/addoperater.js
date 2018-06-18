var addOp = angular.module('addOp', []);

addOp.controller('CtrlAddop', function($scope, $window, ServiceAddOp) {
	$scope.hide = true;
	var cookie = {};
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	else{
		$scope.hide = false;
	}
	
	$scope.message = "";
	$scope.addOp = function(){
		if($scope.parameter == null){
			$scope.message = "Dodavanje neuspesno";
			return;
		}else{
			ServiceAddOp.addOp($scope.parameter, cookie).then(function(response){
				if(response.data == "true"){
					$scope.message = "Uspesno ste dodali operatera";
				}else{
					$scope.message = "Dodavanje neuspesno";
				}
			});
		}
	}
	
	

});

addOp.factory('ServiceAddOp', [ '$http', function($http) {
	var service = {};
	
	service.addOp = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/user/addoperater', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}

	
	return service;
} ]);