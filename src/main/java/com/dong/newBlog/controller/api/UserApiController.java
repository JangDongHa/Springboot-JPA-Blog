package com.dong.newBlog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dong.newBlog.dto.ResponseDTO;
import com.dong.newBlog.model.RoleType;
import com.dong.newBlog.model.User;
import com.dong.newBlog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;

	@PostMapping("/api/user")
	public ResponseDTO<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save()");
		user.setRole(RoleType.USER);
		int result = userService.insertUser(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), result); // 해당 자바 오브젝트를 JSON으로 자동 변환(Jackson)
	}

//	@PostMapping("/api/user/login")
//	public ResponseDTO<Integer> login(@RequestBody User user, HttpSession session) { // DI로 세션을 받아도 됨
//		System.out.println("UserApiController : login()");
//		User principal = userService.selectUser(user);
//		if (principal != null) { // 로그인 성공 시
//			session.setAttribute("principal", principal);
//			return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
//		}
//		else{ // 실패 시
//			return null;
//		}
//		
//	}
}
