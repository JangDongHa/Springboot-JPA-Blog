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

	<%@ include file="../layout/header.jsp"%>

	<div class="row">
		<div class="col-xs-2 col-md-2"></div>
		<div class="col-xs-8 col-md-8">
			<h2 class="text-center">게시글 보기</h2>
			<p>&nbsp;</p>
			<div class="table table-responsive">
				<table class="table">
					<tr>
						<th class="success">글번호</th>
						<td id="id">${board.id}</td>
						<th class="success">조회수</th>
						<td>${board.count}</td>
					</tr>

					<tr>
						<th class="success">작성자</th>
						<td>${board.user.username}</td>
						<th class="success">작성일</th>
						<td>${board.createDate}</td>
					</tr>

					<tr>
						<th class="success">이메일</th>
						<td colspan="3">${board.user.email}</td>
					</tr>

					<tr>
						<th class="success">제목</th>
						<td colspan="3">${board.title}</td>
					</tr>

					<tr>
						<th class="success">글 내용</th>
						<td colspan="3">${board.content}</td>
					</tr>
					<tr>
						<td colspan="4" class="text-center"><input type="button" class="btn btn-success" value="답글 쓰기" onclick="location.href='#'"> 
						<c:if test="${board.user.id == principal.user.id}">
						<a href="/board/${board.id}/updateForm" type="button" class="btn btn-warning" onclick="#" >수정하기</a>
						<input id="btn-delete" type="button" class="btn btn-danger" value="삭제하기">
						</c:if>
						<input type="button" class="btn btn-primary" value="목록보기" onclick="history.back()">
						</td>
					</tr>
				</table>
			</div>

		</div>
	</div>

	<script src="/js/board.js"></script>
	<%@ include file="../layout/footer.jsp"%>
</body>
</html>



