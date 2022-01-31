<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>

	<%@ include file="../layout/header.jsp"%>

	<form>
		<div class="form-group">
			<label for="email">Email:</label> <input type="email" class="form-control" id="email" placeholder="Enter Email" name="email" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group">
			<label for="username">Username:</label> <input type="text" class="form-control" id="username" placeholder="Enter username" name="uname" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group">
			<label for="password">Password:</label> <input type="password" class="form-control" id="password" placeholder="Enter password" name="pswd" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group form-check">
			<label class="form-check-label"> <input class="form-check-input" type="checkbox" name="remember" required> I agree on blabla.
				<div class="valid-feedback">Valid.</div>
				<div class="invalid-feedback">Check this checkbox to continue.</div>
			</label>
		</div>
	</form>
	<button id="btn-save" class="btn btn-primary">회원가입</button>
	
	<script src="/js/user.js"></script>
	<%@ include file="../layout/footer.jsp"%>
</body>
</html>



