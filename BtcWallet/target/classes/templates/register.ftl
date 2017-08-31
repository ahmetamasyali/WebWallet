<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.css">

<!-- Website CSS style -->
<link rel="stylesheet" type="text/css" href="assets/css/main.css">

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
<body ng-app="registerApp" ng-controller="registerCtrl">
	<div class="container">
		<div class="row main">
			<div class="panel-heading">
				<div class="panel-title text-center">
					<a href="/"><h1 class="title">Web Wallet</h1></a>
					<hr />
				</div>
			</div>
			<div class="main-login main-center">
				<form class="form-horizontal" method="post" action="#">
					<div class="alert alert-success" ng-show="!error && message">
						<strong>{{message}}!</strong>
					</div>
					<div class="alert alert-danger" ng-show="error && message">
						<strong> {{message}}</strong>
					</div>
					<div class="form-group">
						<label for="name" class="cols-sm-2 control-label">Your
							Name</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa"
									aria-hidden="true"></i></span> <input type="text" class="form-control"
									ng-model="user.name" name="name" id="name"
									placeholder="Enter your Name" />
							</div>
						</div>
					</div>



					<div class="form-group">
						<label for="username" class="cols-sm-2 control-label">Username</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-users fa"
									aria-hidden="true"></i></span> <input type="text" class="form-control"
									ng-model="user.username" name="username" id="username"
									placeholder="Enter your Username" />
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="password" class="cols-sm-2 control-label">Password</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
									ng-model="user.password" type="password" class="form-control"
									name="password" id="password" placeholder="Enter your Password" />
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="confirm" class="cols-sm-2 control-label">Confirm
							Password</label>
						<div class="cols-sm-10">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="fa fa-lock fa-lg" aria-hidden="true"></i></span> <input
									ng-model="user.passwordConfirm" type="password"
									class="form-control" name="confirm" id="confirm"
									placeholder="Confirm your Password" />
							</div>
						</div>
					</div>

					<div class="form-group ">
						<button type="button" ng-click="register()"
							class="btn btn-primary btn-lg btn-block login-button">Register</button>
					</div>
					<div class="login-register">
						<a href="login">Login</a>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="assets/js/angular.min.js"></script>
	<script type="text/javascript"
		src="assets/controller/register-controller.js"></script>
</body>
</html>