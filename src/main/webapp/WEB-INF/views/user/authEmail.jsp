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
			<label for="email">Email:</label> <input name="username" type="email" class="form-control" id="email" placeholder="Enter Email" name="email" required>
			<div class="valid-feedback">Valid.</div>
			<div class="invalid-feedback">Please fill out this field.</div>
		</div>
	</form>
	<button id="btn-auth" class="btn btn-primary">인증</button>

	<script src="/js/user.js"></script>
	<%@ include file="../layout/footer.jsp"%>
</body>
</html>



