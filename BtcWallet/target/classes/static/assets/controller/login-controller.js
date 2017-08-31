var app = angular.module('loginApp', []);

app.controller('loginCtrl',['$scope', '$http', function($scope,$http) {

	$scope.login = function(){
		$http({
			method: 'POST',
			url: '/login',
			data: JSON.stringify($scope.user),
			headers: {
		        "Content-Type": "application/json"
		    }
		}).then(function (success){
			 if(success.data){
				 if( success.data.status == 'SUCCESS'){
					 $scope.error = false;
					 $scope.message = 'SUCCESS';
					 
					
					 setTimeout(function() {
						 window.location.href = "/login";
						}, 500);
				 }else{
					 $scope.error = true;
					 $scope.message = success.data.message;
				 }
				
				
			 }
		},function (error){
			 $scope.error = true;
			 $scope.message = 'Something Went Wrong :(';
		});
	}

}]);