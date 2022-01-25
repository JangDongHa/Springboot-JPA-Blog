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

	<div class="container">
		<form>
			<input type="hidden" id="id" value="${board.id}"/>
			<div class="form-group">
				<label for="title">Title</label> <input value="${board.title}" type="text" class="form-control" placeholder="Enter Title" id="title">

			</div>
			<div class="form-group">
				<label for="content">Content</label>
				<textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
			</div>
		</form>
	</div>

	<script>
		$('.summernote').summernote({
			placeholder : '글을 입력하세요.',
			tabsize : 2,
			height : 300
		});
	</script>

	<button id="btn-update" class="btn btn-primary">수정</button>

	<script src="/js/board.js"></script>
	<%@ include file="../layout/footer.jsp"%>
</body>
</html>



