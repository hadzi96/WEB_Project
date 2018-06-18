var home = angular.module('home', []);

home.controller('CtrlHome', function($scope, $window, ServiceHome) {
	var cookie = {};
	var userLock = {};
	$scope.searchResult = [];
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	ServiceHome.getUserLock(cookie).then(function(response){
		if(response.data == -1){
			$window.location.href = "http://localhost:8080/WEB_Project/";
		}else{
			$scope.hide = false;
		}
		userLock = response.data;
		console.log(userLock);
	});
	
	ServiceHome.getUserType(cookie).then(function(response){
		if(response.data == "prodavac"){
			$scope.hideProdaja = false;
		}
		
		if(response.data == "kupac"){
			$scope.hideTest = false;
		}
		
		if(response.data == "admin"){
			$scope.hideAddOp = false;
		}
	});
	
	$scope.search = function() {
		var parameter = {};
		parameter = angular.copy($scope.parameter);
		parameter.offset = 0;
		$scope.offset = 0;
		ServiceHome.search(parameter, cookie).then(function(response){
			$scope.searchResult = response.data;
			for(var i = 0; i < $scope.searchResult.length; i++) {
				console.log("item: " + $scope.searchResult[i].id);
				var param = {};
				param.id = $scope.searchResult[i].id;
				getPhoto($scope, ServiceHome, param, cookie, i);
			}
		});
	};
	
	
	$scope.next = function() {
		var parameter = {};
		parameter = angular.copy($scope.parameter);
		$scope.offset++;
		parameter.offset = $scope.offset;
		ServiceHome.search(parameter, cookie).then(function(response){
			$scope.searchResult = response.data;
			for(var i = 0; i < $scope.searchResult.length; i++) {
				console.log("item: " + $scope.searchResult[i].id);
				var param = {};
				param.id = $scope.searchResult[i].id;
				getPhoto($scope, ServiceHome, param, cookie, i);
			}
		});
	};
	
	
	$scope.previous = function() { 
		var parameter = {};
		parameter = angular.copy($scope.parameter);
		$scope.offset--;
		parameter.offset = $scope.offset;
		ServiceHome.search(parameter, cookie).then(function(response){
			$scope.searchResult = response.data;
			for(var i = 0; i < $scope.searchResult.length; i++) {
				console.log("item: " + $scope.searchResult[i].id);
				var param = {};
				param.id = $scope.searchResult[i].id;
				getPhoto($scope, ServiceHome, param, cookie, i);
			}
		});
	};
	
	
	$scope.open = function(id) {
		$window.localStorage.setItem("itemId", id);
		$window.location.href = "http://localhost:8080/WEB_Project/item.html";
	};
	

});

home.factory('ServiceHome', [ '$http', function($http) {
	var service = {};
	
	service.getUserLock = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getlock', {
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.search = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/search', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getPhoto = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/getphoto', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getUserType = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/type', {
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	return service;
} ]);


var getPhoto = function($scope, ServiceHome, param, cookie, i){
	ServiceHome.getPhoto(param, cookie).then(function(response){
		$scope.searchResult[i].photo = angular.copy(response.data);
	});
}