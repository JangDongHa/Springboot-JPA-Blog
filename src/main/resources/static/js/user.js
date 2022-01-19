let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		}); // btn-save가 클릭이 되면
	},
	
	save: function() {
		// data object	에 Value 삽입
		let data = {
			email: $("#email").val(),
			username: $("#username").val(),
			password: $("#password").val()
		}
		
		//console.log(data);
		$.ajax().done().fail(); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}
}
index.init();