var register = angular.module('register', []);

register.controller('CtrlRegister', function($scope, $window, ServiceRegister) {

	$scope.register = function() {
		var host = $window.location.host;
        var landingUrl = "http://www.google.com";
        $window.location.href = landingUrl;
	};

});

register.factory('ServiceRegister', [ '$http', function($http) {
	var service = {};

	return service;
} ]);