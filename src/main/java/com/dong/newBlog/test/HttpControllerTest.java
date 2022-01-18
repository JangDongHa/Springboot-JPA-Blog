package com.dong.newBlog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dong.newBlog.test.Member.MemberBuilder;

// 사용자가 요청 -> 응답 : 컨트롤러의 역할
// 요청에 대한 응답을 Data로 처리해주는 것 : RestController (Return : 문자 그 자체)
// 요청에 대한 응답을 파일(html 등)으로 처리해주는 것 : Controller (Return : 해당 경로 이하에 있는 파일)
@RestController
public class HttpControllerTest {
	
	// 인터넷 브라우저 요청은 무조건 get 요청 (브라우저 요청 : URL을 직접 입력하는 것)
	// http://localhost:8080/http/get (want Select)
	// @RequestParam 으로 하면 변수 하나하나씩 받을 수 있고 Member 처럼 객체 자체를 매개변수로 받을 수 있음
	// 추가적으로, password는 Security 설정을 안하면 자동으로 Default Password 정보가 저장(username도)
	// 이후에 username과 password를 새로 받아도 String이 아닌 List이나 배열 같은 형태로 저장되기 때문에 새로 추가됨
	@GetMapping("/http/get")
	public String getTest(Member m) { // Member m Mapping : MessageConverter
		return "This is Get Test" + " " + m.getId() + " " + m.getUsername() + " " + m.getPassword() + " " + m.getEmail();
	}
	
	// http://localhost:8080/http/post (want Insert)
	// Post 같은 경우 Body에 데이터를 적어서 던짐
	// 객체 데이터 중 하나라도 전달할 시, 오류가 발생하진 않지만 하나도 전달되지 않으면 오류 발생 (Bad Request)
	// PostMapping에서 데이터 하나를 받고 싶으면 @RequestBody	 Annotation 사용
	@PostMapping("/http/post") // raw Data : text/plain, application/json ({"id" : 3, ....} 이런식으로)
	public String postTest(@RequestBody Member m) { // Member m Mapping : MessageConverter
		return "This is Post Test" + " " + m.getId() + " " + m.getUsername() + " " + m.getPassword() + " " + m.getEmail();
	}
	
	// http://localhost:8080/http/put (want Update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "This is Put Test" + " " + m.getId() + " " + m.getUsername() + " " + m.getPassword() + " " + m.getEmail();
	}
	
	// http://localhost:8080/http/delete (want Delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "This is Delete Test";
	}
	
}
