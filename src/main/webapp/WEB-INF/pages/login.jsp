<%@ page language="java" contentType="text/html; charset=UTF-8"%> 
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Document</title>
	<link href="/static/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" />
	<script src="/static/jquery-1.12.4/jquery.min.js"></script>
    <script src="/static/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</head>
<style>
	.login-form-wrapper{
		width: 260px;
		height: 230px;
		padding: 20px;
		position: absolute;
		left: 50%;
		top: 50%;
		margin-left: -130px;
		margin-top: -115px;
		border: 1px solid #ccc;
		border-radius: 4px;
	}
</style>
<body>
<div class="login-form-wrapper">
	<form action="/userLogin" method="POST" id="loginForm">
	  <div class="form-group">
	    <label for="name">Name</label>
	    <input type="text" class="form-control" id="name" placeholder="Name" name="name">
	  </div>
	  <div class="form-group">
	    <label for="password">Password</label>
	    <input type="password" class="form-control" id="password" placeholder="Password" name="password">
	  </div>
	  <button type="submit" class="btn btn-default pull-right" id="submit">Submit</button>
	</form>
	<div>${loginMsg}</div>
</div>
	
</body>
<script>
console.log($)
	$(document.body)
		.on('click', '#submit', function() {
			$('#loginForm').submit()
		})
</script>
</html>