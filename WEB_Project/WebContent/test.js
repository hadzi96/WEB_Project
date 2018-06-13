var test = angular.module('test', []);

test.controller('CtrlTest', function($scope, $window, ServiceTest) {
	var cookie = {};
	var userLock = {};
	$scope.korpaItems = [];
	$scope.message = "";
	cookie = $window.localStorage.getItem("webProj");
	
	if(cookie == null){
		$window.location.href = "http://localhost:8080/WEB_Project/";
	}
	ServiceTest.getUserLock(cookie).then(function(response){
		if(response.data == -1){
			$window.location.href = "http://localhost:8080/WEB_Project/";
		}
		userLock = response.data;
		console.log(userLock);
	});
	
	ServiceTest.getUserType(cookie).then(function(response){
		if(response.data != "kupac"){
			if(response.data == "admin"){
				$window.location.href = "http://localhost:8080/WEB_Project/admin/home.html";
			}
			
			$window.location.href = "http://localhost:8080/WEB_Project/home.html";
		}else{
			$scope.hide = false;
		}
		console.log(response.data);
	});
	
	$scope.parameter= {};
	$scope.sendTest = function() {
		var parameter = {};
		parameter = angular.copy($scope.parameter);

		ServiceTest.sendTest($scope.parameter, cookie).then(function(response){
			if(response.data == "true"){
				$scope.message = "Test uspesno poslat.. operater ce uskoro oceniti Vas test"
			}else{
				$scope.message = "Greska prilikom slanja testa:" +
						"\nNapomena: svaki korisnik moze samo jednom da posalje test, moraju da se posalju svih 10 fotografija"
			}
		});
	};
	
	

});

test.factory('ServiceTest', [ '$http', function($http) {
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
	
	service.sendTest = function(parameter, cookie){
        var fd = new FormData();
        
        fd.append('photo1', parameter.photo1, 'photo1');
        fd.append('photo2', parameter.photo2, 'photo2');
        fd.append('photo3', parameter.photo3, 'photo3');
        fd.append('photo4', parameter.photo4, 'photo4');
        fd.append('photo5', parameter.photo5, 'photo5');
        fd.append('photo6', parameter.photo6, 'photo6');
        fd.append('photo7', parameter.photo7, 'photo7');
        fd.append('photo8', parameter.photo8, 'photo8');
        fd.append('photo9', parameter.photo9, 'photo9');
        fd.append('photo10', parameter.photo10, 'photo10');
        
        return $http.post('http://localhost:8080/WEB_Project/server/test/send', fd, {
            transformRequest: angular.identity,
            headers: {"Content-Type": undefined,
            	"Authorization": "WebProject="+cookie}
        });
    }
	
	return service;
} ]);

test.directive('fileModel', ['$parse', function ($parse) {
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
