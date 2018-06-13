var korpa = angular.module('korpa', []);

korpa.controller('CtrlKorpa', function($scope, $window, ServiceKorpa) {
	var cookie = {};
	var userLock = {};
	$scope.korpaItems = [];
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	ServiceKorpa.getUserLock(cookie).then(function(response){
		if(response.data == -1){
			$window.location.href = "http://localhost:8080/WEB_Project/";
		}
		userLock = response.data;
		console.log(userLock);
	});
	
	$scope.message = "";
	ServiceKorpa.getKorpa(cookie).then(function(response){
		if(response.data == ""){
			$window.location.href = "http://localhost:8080/WEB_Project/";
		}else{
			$scope.korpaItems = response.data;
			if($scope.korpaItems.length == 0)
				$scope.message = "Prazna korpa";
			
			for(var i = 0; i < $scope.korpaItems.length; i++) {
				var param ={};
				param.id =  $scope.korpaItems[i].idSlike;
				open($scope, ServiceKorpa, param, cookie, i);
				getPhoto($scope, ServiceKorpa, param, cookie, i);
			}
			
			ServiceKorpa.getCards(cookie).then(function(response){
				$scope.creditCards = response.data;
			});
			
		}
	});
	
	
	
	
	
	$scope.open = function(id) {
		$window.localStorage.setItem("itemId", id);
		$window.location.href = "http://localhost:8080/WEB_Project/item.html";
	};
	
	
	$scope.buy = function() {
		console.log($scope.parameter);
		
		if($scope.parameter == null){
			return;
		}else{
			if($scope.parameter.creditCardNew == null){
				//koristi staru
			}else{
				//koristi novu
			}
		}
		
			
	};
	

});

korpa.factory('ServiceKorpa', [ '$http', function($http) {
	var service = {};
	
	service.getUserLock = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getlock', {
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getPhoto = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/getphoto', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.open = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/open', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getKorpa = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/korpa/getKorpa',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getCards = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getcards',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	return service;
} ]);


var getPhoto = function($scope, ServiceKorpa, param, cookie, i){
	ServiceKorpa.getPhoto(param, cookie).then(function(response){
		$scope.korpaItems[i].photo = angular.copy(response.data);
		if(i == $scope.korpaItems.length-1 ){
			$scope.hide = false;
		}
	});
}


var open = function($scope, ServiceKorpa, param, cookie, i){
	ServiceKorpa.open(param, cookie).then(function(response){
		$scope.item = angular.copy(response.data);
		$scope.korpaItems[i].ime = $scope.item.ime;
	});
}