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
	}
	
}
index.init();