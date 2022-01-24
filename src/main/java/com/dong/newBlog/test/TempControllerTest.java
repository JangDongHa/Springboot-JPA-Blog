package com.dong.newBlog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	// http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome");
		// Spring File Return 기본 경로 : src/main/resources/static
		// 만약 return "home.html"로 리턴하면 src/main/resources/statichome.html로 설정됨
		return "/home.html";
	}

	@GetMapping("/temp/img")
	public String getImg() {
		return "/a.png";
	}

	@GetMapping("/temp/jsp")
	public String getJsp() {
		// Prefix : /WEB-INF/views/
		// Suffix : .jsp
		// 풀네임 : /WEB-INF/views//test.jsp.jsp
		// Prefix에서 views/ 에서 /를 빼주거나 리턴할 때 /를 빼줘야함
		// 그렇지만 요즘에는 / 안빼줘도 알아서 잘 걸러줌
		// 뒤에 있는 jsp도 빼줘야함
		return "/test";
	}
}
