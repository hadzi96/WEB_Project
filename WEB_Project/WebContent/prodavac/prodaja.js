var prodaja = angular.module('prodaja', []);
var reader = new FileReader();

prodaja.controller('CtrlProdaja', function($scope, $window, $interval, ServiceProdaja) {
	var cookie = {};
	var userLock = {};
	$scope.message = "";
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	ServiceProdaja.getUserLock(cookie).then(function(response){
		if(response.data == -1){
			$window.location.href = "http://localhost:8080/WEB_Project/";
		}
		userLock = response.data;
		console.log(userLock);
	});
	
	ServiceProdaja.getUserType(cookie).then(function(response){
		if(response.data != "prodavac"){			
			$window.location.href = "http://localhost:8080/WEB_Project/home.html";
		}else{
			$scope.hide = false;
		}
		console.log(response.data);
	});
	
	ServiceProdaja.getCards(cookie).then(function(response){
		console.log(response.data);
		if(response.data.length == 0){
			$scope.hideKartica = false;
		}
	});
	
	$scope.parameter = {};
	$scope.sendPhoto = function(){
		setCeneAndRez($scope);
		$scope.parameter.rezolucije = $scope.parameter.rezolucije.substring(0, $scope.parameter.rezolucije.length -1);
		console.log($scope.parameter);
		
		if($scope.hideKartica == false){
			var cardParam = {};
			cardParam.optimisticLock = userLock;
			cardParam.creditCard = $scope.parameter.kartica;
			ServiceProdaja.addCard(cardParam, cookie).then(function(response){
				if(response.data != "true"){
					$scope.message = "Error.. greska prilikom dodavanje kreditne kartice";
				}else{
					ServiceProdaja.sendPhoto($scope.parameter, cookie).then(function(response){
						if(response.data != true){
							$scope.message = "Neuspesno slanje slike.. proverite dali ste uneli sve validne parametre. Napomena: Maksimalno se može poslati 3 fotografije dnevno, a 8 nedeljno ";
							console.log(response.data);
						}else{
							$scope.message = "Slika uspesno postavljena.. Operater ce je uskoro odobriti ili odbiti";
							console.log(response.data);
						}
						
					});
				}
			});
		}else{
			ServiceProdaja.sendPhoto($scope.parameter, cookie).then(function(response){
				if(response.data != true){
					$scope.message = "Neuspesno slanje slike.. proverite dali ste uneli sve validne parametre. Napomena: Maksimalno se može poslati 3 fotografije dnevno, a 8 nedeljno ";
					console.log(response.data);
				}else{
					$scope.message = "Slika uspesno postavljena.. Operater ce je uskoro odobriti ili odbiti";
					console.log(response.data);
				}
				
			});
		}
	};
	
	
	$scope.checkPhoto = function(){
		reader.readAsDataURL($scope.parameter.photo);
		reader.onloadend = onLoadFile;
	};
	
	var setRez = function() {
		try{			
			$scope.checkPhoto();
			$scope.hideC1 = hideC1;
			$scope.hideC2 = hideC2;
			$scope.hideC3 = hideC3;
			$scope.hideSend = hideSend;
		}catch(err){
			$scope.hideC1 = true;
			$scope.hideC2 = true;
			$scope.hideC3 = true;
			$scope.hideSend = true;
		}
    }
	
	$scope.intervalIstance = $interval(setRez, 1000);

});

prodaja.factory('ServiceProdaja', [ '$http', function($http) {
	var service = {};
	
	service.getUserLock = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getlock', {
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.getUserType = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/type', {
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.sendPhoto = function(parameter, cookie){
		var fd = new FormData();
        fd.append('data', parameter.photo, 'data');
        
        var photo = JSON.stringify(parameter);
        fd.append('photo', photo);
        
        return $http.post('http://localhost:8080/WEB_Project/server/photo/send', fd, {
            transformRequest: angular.identity,
            headers: {"Content-Type": undefined,
            	"Authorization": "WebProject="+cookie}
        });
		
	}
	
	service.getCards = function(cookie){
		return $http.get('http://localhost:8080/WEB_Project/server/user/getcards',{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	service.addCard = function(parameter, cookie){
		return $http.post('http://localhost:8080/WEB_Project/server/user/addcard', parameter,{
		    headers: {'Authorization': 'WebProject='+cookie}
		  });
	}
	
	return service;
} ]);

prodaja.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
           var model = $parse(attrs.fileModel);
           var modelSetter = model.assign;
           
           element.bind('change', function(){
              scope.$apply(function(){
                 modelSetter(scope, element[0].files[0]);
              });
           });
        }
     };
  }]);

var setCeneAndRez = function($scope){
	$scope.parameter.cene = "";
	$scope.parameter.rezolucije = "";
	if($scope.parameter.cena1 != null){
		$scope.parameter.cene += $scope.parameter.cena1 +";";
		$scope.parameter.rezolucije += "1920x1080;";
	}
	if($scope.parameter.cena2 != null){
		$scope.parameter.cene += $scope.parameter.cena2 +";";
		$scope.parameter.rezolucije += "1600x900;";
	}
	if($scope.parameter.cena3 != null){
		$scope.parameter.cene += $scope.parameter.cena3 +";";
		$scope.parameter.rezolucije += "1280x720;";
	}
};

var img = new Image();
var hideC1 = true;
var hideC2 = true;
var hideC3 = true;
var hideSend = true;

function onLoadFile(event) {
	new Promise(function(resolve, reject) {
		hideC1 = true;
		hideC2 = true;
		hideC3 = true;
		hideSend = true;
		img = new Image();
	    img.src = event.target.result;
	   
	    resolve("success");
	}).then(function(value){
		if(img.width >= 1920 && img.height >= 1080){
			hideC1 = false;
			hideC2 = false;
			hideC3 = false;
			hideSend = false;
		}
		if(img.width >= 1600 && img.height >= 900){
			hideC2 = false;
			hideC3 = false;
			hideSend = false;
		}
		if(img.width >= 1280 && img.height >= 720){
			hideC3 = false;
			hideSend = false;
		}
	});
}
