<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.css">

<!-- Website CSS style -->
<link rel="stylesheet" type="text/css" href="assets/css/main.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/isteven-multi-select.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/angular-growl.min.css">

<!-- Website Font style -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">

<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Passion+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Oxygen'
	rel='stylesheet' type='text/css'>

<title>Admin</title>
</head>
<body ng-app="homeApp" ng-controller="homeCtrl">
	<div growl></div>
	<div ui-view></div>

	<script type="text/javascript">
		var isLoggedIn = ${isLoggedIn?c};
		<#if user?has_content>
			var username = "${user.username!}";
			var bitcoinAmount = "${user.bitcoinAmount!}";
			var bitcoinAddress =  "${user.bitcoinAddress!}";
		</#if>
	</script>
	<script type="text/javascript" src="assets/js/angular.min.js"></script>
	<script type="text/javascript" src="assets/js/ui-router.min.js"></script>

	<script type="text/javascript"
		src="assets/controller/home-controller.js"></script>
</body>
</html>