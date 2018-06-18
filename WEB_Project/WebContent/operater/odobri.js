var odobri = angular.module('odobri', []);

odobri.controller('CtrlOdobri', function($scope, $window, ServiceOdobri) {
	$scope.hide = true;
	var cookie = {};
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	else{
		ServiceOdobri.getNeodobrene(cookie).then(function(response){
			$scope.resultSet = response.data;
			for(var i = 0; i < $scope.resultSet.length; i++) {
				console.log("item: " + $scope.resultSet[i].id);
				var param = {};
				param.id = $scope.resultSet[i].id;
				getPhoto($scope, ServiceOdobri, param, cookie, i);
			}
			$scope.hide = false;
		});
		
	}
	
	
	$scope.odobri = function(id){
		var param = {};
		param.id = id;
		
		ServiceOdobri.odobri(param, cookie).then(function(response){
			if(response.data == "true"){
				$window.location.href = "http://localhost:8080/WEB_Project/operater/odobri.html";
			}
		});
	}
	
	
	

});

odobri.factory('ServiceOdobri', [ '$http', function($http) {
	var service = {};
	
	service.getNeodobrene = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/photo/getneodobrene',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getPhoto = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/getphoto', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.odobri = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/odobri', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}

	
	return service;
} ]);

var getPhoto = function($scope, ServiceOdobri, param, cookie, i){
	ServiceOdobri.getPhoto(param, cookie).then(function(response){
		$scope.resultSet[i].photo = angular.copy(response.data);
	});
}