var register = angular.module('register', []);

register.controller('CtrlRegister', function($scope, $window, ServiceRegister) {

	$scope.message = '';
	$scope.register = function() {
		
		$scope.register = function() {
			var user = {};
			user = angular.copy($scope.user);
			$scope.message = 'Registration i progress...';
			ServiceRegister.register(user).then(function(response){
				if(response.data == 'true'){
					
					$scope.message = 'Registration successfull. \nPlease check e-mail!';
				}
				else{
					$scope.message = 'Registration failed!';
				}
			});
		};
		
	};

});

register.factory('ServiceRegister', [ '$http', function($http) {
	var service = {};
	
	service.register = function(user){
		return $http.post('http://localhost:8080/WEB_Project/server/user/register', user);
	}

	return service;
} ]);