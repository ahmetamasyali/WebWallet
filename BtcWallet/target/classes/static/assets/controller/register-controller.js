var app = angular.module('registerApp', []);

app.controller('registerCtrl',['$scope', '$http', function($scope,$http) {

	$scope.register = function(){
		$http({
			method: 'POST',
			url: '/register',
			data: JSON.stringify($scope.user),
			headers: {
		        "Content-Type": "application/json"
		    }
		}).then(function (success){
			 if(success.data){
				 if( success.data.status == 'SUCCESS'){
					 $scope.message = success.data.message;
					 $scope.error = false;
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