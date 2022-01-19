package com.dong.newBlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	
	@GetMapping({"", "/"}) // 중괄호를 통해 다중 URL 다중 참조 가능
	public String index() {
		// URL 입력 시 /WEB-INF/views/index.jsp 참조
		return "index"; // 이렇게 적어둬도 application.yml 에 prefix, suffix가 정의되 있으므로 자동 Mapping
	}
}
