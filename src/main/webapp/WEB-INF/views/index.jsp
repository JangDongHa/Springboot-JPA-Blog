<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>

	<%@ include file="layout/header.jsp"%>

	<div class="container">
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">제목 적는 부분</h4>
				<a href="#" class="btn btn-primary">상세보기</a>
			</div>
		</div>
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">제목 적는 부분</h4>
				<a href="#" class="btn btn-primary">상세보기</a>
			</div>
		</div>
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">제목 적는 부분</h4>
				<a href="#" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</div>

	<%@ include file="layout/footer.jsp"%>
</body>
</html>



