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

	<form action="/auth/loginProc" method="post" class="was-validated">
		<div class="form-group">
			<label for="email">Email:</label> <input name="username" type="email" class="form-control" id="email" placeholder="Enter Email" name="email" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group">
			<label for="password">Password:</label> <input name="password" type="password" class="form-control" id="password" placeholder="Enter password" name="pswd" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
		<div class="form-group form-check">
			<label name="remember" class="form-check-label"> <input class="form-check-input" type="checkbox" name="remember" required> I agree on blabla.
				<div class="valid-feedback">Valid.</div>
				<div class="invalid-feedback">Check this checkbox to continue.</div>
			</label>
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=0c41f3aa58c09917f905ac72d4ac7a56&redirect_uri=	
http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_button.png"></a>
	</form>

	<%@ include file="../layout/footer.jsp"%>
</body>
</html>



