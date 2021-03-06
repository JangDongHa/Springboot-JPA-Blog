package com.dong.newBlog.controller.api;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dong.newBlog.config.auth.PrincipalDetail;
import com.dong.newBlog.config.auth.PrincipalDetailService;
import com.dong.newBlog.dto.ReplySaveRequestDTO;
import com.dong.newBlog.dto.ResponseDTO;
import com.dong.newBlog.model.Board;
import com.dong.newBlog.model.Reply;
import com.dong.newBlog.model.RoleType;
import com.dong.newBlog.model.User;
import com.dong.newBlog.service.BoardService;
import com.dong.newBlog.service.UserService;

@RestController
public class BoardApiController {
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PrincipalDetailService principalDetailService;

	@PostMapping("/api/board")
	public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principalDetail) {
		System.out.println("BoardApiController : save()");
		boardService.writing(board, principalDetail.getUser());
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDTO<Integer> deleteById(@PathVariable int id, Principal principal){
		System.out.println("BoardApiController : delete()");
		//Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Board currentBoard = boardService.viewDetails(id);
		if (principal.getName().equals(currentBoard.getUser().getEmail())) {// ?????? ?????? ???????????? ?????? ??? ???????????? ?????? ???????????? ?????? ??????
			boardService.delete(id);
		}
		else {
			return new ResponseDTO<Integer>(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1);
		}
			
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDTO<Integer> updateById(@PathVariable int id,@RequestBody Board board, Principal principal){
		System.out.println("BoardApiController : update()");
		Board currentBoard = boardService.viewDetails(id);
		if (principal.getName().equals(currentBoard.getUser().getEmail())) // ?????? ?????? ???????????? ?????? ??? ???????????? ?????? ???????????? ?????? ??????
			boardService.update(id, board);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	// Data ??? ?????? ????????? ?????????????????? dto??? ???????????? ?????? ?????? ??????
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDTO<Integer> replySave(@RequestBody ReplySaveRequestDTO replyDTO, @AuthenticationPrincipal PrincipalDetail principalDetail) {
		System.out.println("BoardReplyApiController : replySave()");
		replyDTO.setUserId(principalDetail.getUser().getId());
		boardService.writingReply(replyDTO);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDTO<Integer> deleteReply(@PathVariable int boardId, @PathVariable Integer replyId, Principal principal) throws Exception{
		System.out.println("BoardApiController : deleteReply()");
		Reply currentReply = boardService.findReplyId(replyId);
		if (currentReply.getUser().getEmail().equals(principal.getName())) {
			boardService.deleteReply(replyId);
		}
		else 
			throw new Exception();
			
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
}
