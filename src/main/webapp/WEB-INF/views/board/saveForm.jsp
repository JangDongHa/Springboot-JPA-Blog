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

	<div class="container">
		<form>
			<div class="form-group">
				<label for="title">Title</label> <input type="text" class="form-control" placeholder="Enter Title" id="title">

			</div>
			<div class="form-group">
				<label for="content">Content</label>
				<textarea class="form-control summernote" rows="5" id="content"></textarea>
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

	<button id="btn-save" class="btn btn-primary">글 작성</button>

	<script src="/js/board.js"></script>
	<%@ include file="../layout/footer.jsp"%>
</body>
</html>



