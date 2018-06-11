var login = angular.module('login', []);

login.controller('CtrlLogin', function($scope, $window, ServiceLogin) {
	$scope.message = "";
	$scope.login = function() {
		var user = {};
		user = angular.copy($scope.user);
		
		ServiceLogin.login(user).then(function(response){
			if(response.data != ''){
				
				$window.localStorage.setItem("webProj",response.data);
		        $window.location.href = "http://localhost:8080/WEB_Project/home.html";
			}
			else{
				$scope.message = 'Login failed!';
			}
		});
	};

	$scope.register = function() {
        var landingUrl = "http://localhost:8080/WEB_Project/register.html";
        $window.location.href = landingUrl;
	};

});

login.factory('ServiceLogin', [ '$http', function($http) {
	var service = {};
	
	service.login = function(user){
		return $http.post('http://localhost:8080/WEB_Project/server/user/login', user);
	}
	
	return service;
} ]);