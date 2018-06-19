var test = angular.module('test', []);

test.controller('CtrlTest', function($scope, $window, ServiceOceniTest) {
	$scope.hide = true;
	var cookie = {};
	cookie = $window.localStorage.getItem("webProj");
	var test = JSON.parse($window.localStorage.getItem("test"));
	
	if(cookie == null || test == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	else{
		$scope.testPhotos = [];
		$scope.photo = {};
		$scope.counter = 0;
		
		var param = {};
		param.fileName = test.slika1;
		getPhoto($scope, ServiceOceniTest, param, cookie, 0);
		
		var param1 = {};
		param1.fileName = test.slika2;
		getPhoto($scope, ServiceOceniTest, param1, cookie, 1);

		var param2 = {};
		param2.fileName = test.slika3;
		getPhoto($scope, ServiceOceniTest, param2, cookie, 2);
		
		var param3 = {};
		param3.fileName = test.slika4;
		getPhoto($scope, ServiceOceniTest, param3, cookie, 3);
		
		var param4 = {};
		param4.fileName = test.slika5;
		getPhoto($scope, ServiceOceniTest, param4, cookie, 4);
		
		var param5 = {};
		param5.fileName = test.slika6;
		getPhoto($scope, ServiceOceniTest, param5, cookie, 5);
		
		var param6 = {};
		param6.fileName = test.slika7;
		getPhoto($scope, ServiceOceniTest, param6, cookie, 6);
		
		var param7 = {};
		param7.fileName = test.slika8;
		getPhoto($scope, ServiceOceniTest, param7, cookie, 7);
		
		var param8 = {};
		param8.fileName = test.slika9;
		getPhoto($scope, ServiceOceniTest, param8, cookie, 8);
		
		var param9 = {};
		param9.fileName = test.slika10;
		getPhoto($scope, ServiceOceniTest, param9, cookie, 9);
		
		$scope.hide = false;
	}
	

	$scope.previous = function(){
		if($scope.counter > 0){
			$scope.photo = $scope.testPhotos[--$scope.counter];
		}
	};
	
	
	$scope.next = function(){
		if($scope.counter < 9){
			$scope.photo = $scope.testPhotos[++$scope.counter];
		}
	};
	
	$scope.message = "";
	$scope.oceni = function(){
		$scope.parameter.username = test.user;
		ServiceOceniTest.oceni($scope.parameter, cookie).then(function(response){
			$scope.message = "Uspesno!";
		});
	};
	
	

});

test.factory('ServiceOceniTest', [ '$http', function($http) {
	var service = {};
	
	service.getTestPhoto = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/test/getphoto', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.oceni = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/test/oceni', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}

	
	return service;
} ]);


var getPhoto = function($scope, ServiceOceniTest, param, cookie, i){
	ServiceOceniTest.getTestPhoto(param, cookie).then(function(response){
		$scope.testPhotos[i] = angular.copy(response.data);
		$scope.photo = $scope.testPhotos[0];
	});
}