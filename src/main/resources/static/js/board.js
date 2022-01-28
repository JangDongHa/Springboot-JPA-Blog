let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
		$("#btn-update").on("click", () => {
			this.updateById();
		});
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});
		$("#btn-reply-delete").on("click", () => {
			this.deleteReply();
		});
	},

	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		}

		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글 작성이 완료되었습니다. ");
			location.href = "/";
		}).fail(function(error) {
			alert("글 작성 실패!");
			alert(error);
		});
	},

	deleteById: function() {
		let id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json"
		}).done(function(resp) {
			alert("글 삭제가 완료되었습니다. ");
			location.href = "/";
		}).fail(function(error) {
			alert("글 삭제 실패!");
			alert(error);
		});
	},

	updateById: function() {
		let id = $("#id").val();

		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		}

		$.ajax({
			type: "PUT",
			url: "/api/board/" + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글 수정이 완료되었습니다. ");
			location.href = "/";
		}).fail(function(error) {
			alert("글 수정 실패!");
			alert(error);
		});
	},

	replySave: function() {
		let data = {
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};

		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("댓글 작성이 완료되었습니다. ");
			location.href = `/board/${data.boardId}`;
		}).fail(function(error) {
			alert("댓글 작성 실패!");
			alert(error);
		});
	},

	deleteReply: function() {
		let boardId = $("#boardId").val();
		let replyId = $("#replyId").val();
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
		}).done(function(resp) {
			alert("댓글 삭제가 완료되었습니다. ");
			location.href = `/board/${boardId}`;
		}).fail(function(error) {
			alert("댓글 삭제 실패!");
			alert(error);
		});
	}

}
index.init();