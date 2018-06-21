var item = angular.module('item', []);

item.controller('CtrlItem', function($scope, $window, ServiceItem) {
	var cookie = {};
	var userLock = {};
	var id = $window.localStorage.getItem("itemId");
	$scope.searchResult = [];
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null || id == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	ServiceItem.getUserLock(cookie).then(function(response){
		if(response.data == -1){
			$window.location.href = "http://localhost:8080/WEB_Project/";
		}
		userLock = response.data;
		console.log(userLock);
	});
	
	var parameter = {};
	parameter.id = id;
	ServiceItem.open(parameter, cookie).then(function(response){
		if(response.data == ""){
			$window.location.href = "http://localhost:8080/WEB_Project/home.html";
		}else{
			$scope.item = response.data;
			var param = {};
			param.id = $scope.item.id;
			getPhoto($scope, ServiceItem, param, cookie);			
		}
	});

	$scope.message = "";
	$scope.addToKorpa = function() {
		try{
			var parameter = {};
			parameter = angular.copy($scope.parameter);
			parameter.idSlike = $scope.item.id;
			ServiceItem.addToKorpa(parameter, cookie).then(function(response){
				if(response.data = "true"){
					$scope.message = "Slika dodata u korpu";
				}else{
					$scope.message = "Dodavanje u korpu Error";
				}
			});
		}catch(error){
			$scope.message = "Dodavanje u korpu Error";
		}
	}
	
	$scope.ocenaMessage= "";
	$scope.oceni = function(){
		$scope.ocenaParam.idSlike = $scope.item.id;
		
		ServiceItem.oceni($scope.ocenaParam, cookie).then(function(response){
			if(response.data == "true" || response.data == true){
				$scope.ocenaMessage= "Uspesno ocenjena";
			}else{
				$scope.ocenaMessage= "Neuspesna ocenjivanje. Napomena: mogu samo kupljene slike da se ocenjuju";
			}
		});
	}
	
	$scope.komentarMessage= "";
	$scope.send = function(){
		$scope.komentarParam.receiver = $scope.item.autor;
		
		ServiceItem.send($scope.komentarParam, cookie).then(function(response){
			if(response.data == "true" || response.data == true){
				$scope.komentarMessage= "Uspesno slanje";
			}else{
				$scope.komentarMessage= "Neuspesna slanje. Napomena: Mogu se komentarisati autori od kojih je kupljena slika";
			}
		});
	}

});

item.factory('ServiceItem', [ '$http', function($http) {
	var service = {};
	
	service.getUserLock = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getlock', {
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.open = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/open', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getPhoto = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/getphoto', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.addToKorpa = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/korpa/addItem', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.oceni = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/photo/oceni', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.send = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/user/comment', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	return service;
} ]);


var getPhoto = function($scope, ServiceItem, param, cookie){
	ServiceItem.getPhoto(param, cookie).then(function(response){
		$scope.item.photo = angular.copy(response.data);
		$scope.item.cene = $scope.item.cene.split(';');
		$scope.item.rezolucije = $scope.item.rezolucije.split(';');
		$scope.hide = false;
	});
}