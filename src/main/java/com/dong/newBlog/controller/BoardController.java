package com.dong.newBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dong.newBlog.config.auth.PrincipalDetail;
import com.dong.newBlog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({ "", "/" })
	public String index(Model model){
		model.addAttribute("boards", boardService.listContents());
		return "index"; // Controller는 viewResolver 작동
	}
	
	// USER 권한이 필요(글쓰기)
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
