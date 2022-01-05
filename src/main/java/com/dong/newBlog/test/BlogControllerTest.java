package com.dong.newBlog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Spring이 com.dong.newBlog 패키지 이하를 스캔해서 모든 파일을 메모리에 new 하는 것은
// 아니고, 특정 Annotation이 붙어있는 class 파일들을 new 해서(IOC) 스프링컨테이너에 관리
@RestController
public class BlogControllerTest {
	
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>Hello Springboot</h1>";
	}
}
