let index = {
	init: function() {
		$("#btn-save").on("click", () => { // function(){} 대신 ()=>{} 을 쓰는 이유 : this를 binding 하기 위해
			$("[btn-save]").attr("disabled", true);
			this.save();
		}); // btn-save가 클릭이 되면
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-auth").on("click", () => {
			this.authE();
		});
	},

	save: function() {
		// data object	에 Value 삽입
		let data = {
			email: $("#email").val(),
			username: $("#username").val(),
			password: $("#password").val()
		}

		//console.log(data);
		// ajax 호출 시 기본(Default)은 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert(post) 요청
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
		// 따라서, dataType: "json" 이라고 굳이 명시해주지 않아도 자동으로 변환해줌
		$.ajax({
			// 회원가입 수행 요청 (100초 가정)
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), // http body data(let data는 js object이므로 json화 하여 뿌려줘야함)
			contentType: "application/json; charset=utf-8", // body data Type
			dataType: "json" // 서버로부터 응답이 왔을 때 기본적으로 원래 모든 것이 버퍼로 오기 때문에 String으로 옴
			// 하지만 생긴게 json이라면 javaScript Object로 변경해주는 것
		}).done(function(resp) { // resp에 dataType에 따른 값이 들어옴
			// 성공 시
			alert("회원가입 완료 ");
			//console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			// 실패 시 (서버로부터 데이터 응답이 오지 않은 경우)
			alert("중복된 Username 혹은 Email이 존재합니다."); // 그 이상의 오류가 존재할 수 있으니 책갈피1
			//alert(JSON.stringify(error))
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	},
	
	update: function() {
		let data = {
			email: $("#email").val(),
			username: $("#username").val(),
			password: $("#password").val()
		}

		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("회원 수정 완료 ");
			location.href = "/";
		}).fail(function(error) {
			alert("회원 수정 실패");
		});
	},
	
	authE: function() {
		let data = $("#email").val()
		
		alert(data);

		$.ajax({
			type: "POST",
			url: "/api/user/authEmail",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(resp) {
			alert("Email Authentication");
			location.href = "/";
		}).fail(function(error) {
			alert("Error Email Auth");
		}); 
	}
	
}
index.init();