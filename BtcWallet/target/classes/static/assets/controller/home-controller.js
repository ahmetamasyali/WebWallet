var app = angular.module('homeApp', ['ui.router']);

app.config([
	'$stateProvider',
	'$urlRouterProvider',
	function($stateProvider, $urlRouterProvider) {

		$stateProvider
		.state('home', {
			url: '/',
			templateUrl: "assets/views/main.html",
			controller: "homeCtrl"
		});

		$urlRouterProvider.otherwise('/');
	}]);



app.controller('homeCtrl', ['$scope','$http', function($scope,$http) {


	$scope.isLoggedIn = isLoggedIn;
	if(isLoggedIn){
		$scope.username = username;
		$scope.bitcoinAmount = bitcoinAmount;
		$scope.bitcoinAddress = bitcoinAddress;
	}else{
		window.location.href = "/login";
	}


	$scope.logout = function(){
		window.location.href = "/logout";
	}
	
	$scope.sendBtc = function(){
		$http.get("send?address="+$scope.sendAddress+"&amount="+$scope.btcToSend).then(function(success){
			if(success.data == "SUCCESS"){
				window.location.reload();
			}
		});
	}

	if(isLoggedIn){
		
		$http.get("getTransactions").then(function(success){
			$scope.transactions = success.data;
		});
	}



}]);

