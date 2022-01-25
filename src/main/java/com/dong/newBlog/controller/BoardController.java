package com.dong.newBlog.controller;

import org.springframework.security.core.Authentication;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dong.newBlog.config.auth.PrincipalDetail;
import com.dong.newBlog.model.Board;
import com.dong.newBlog.service.BoardService;
import org.springframework.data.domain.Sort;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({ "", "/" })
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		model.addAttribute("boards", boardService.listContents(pageable));
		return "index"; // Controller는 viewResolver 작동
	}
	
	// USER 권한이 필요(글쓰기)
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}")
	public String boardFindById(Model model, @PathVariable int id) {
		model.addAttribute("board", boardService.viewDetails(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model, Principal principal) {
		Board currentBoard = boardService.viewDetails(id);
		// 현재 세션의 Email과 Board Email이 같으면 같은 사용자이므로 정상 진행
		if (principal.getName().equals(currentBoard.getUser().getEmail())) {
			model.addAttribute("board", boardService.viewDetails(id));
			return "board/updateForm";
		}
		else // 다른 경우, 해당 Board 재출력
			return boardFindById(model, id);
	}
}
