package com.dong.newBlog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dong.newBlog.config.auth.PrincipalDetail;
import com.dong.newBlog.dto.ResponseDTO;
import com.dong.newBlog.model.User;
import com.dong.newBlog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;


	// loginProc 를 만들지 않는 이유
	// Spring Security 가 로그인 정보를 가로채게 할 것이기 때문에
	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save()");
		userService.insertUser(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); // 해당 자바 오브젝트를 JSON으로 자동 변환(Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDTO<Integer> update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principal,
			HttpSession session) {
		System.out.println("UserApiController : update()");
		if (principal.getUsername().equals(user.getEmail()))
			user = userService.updateUser(user);
		// 여기서는 Transaction이 종료되기 때문에 DB에 있는 값은 변경이 되었지cff만
		// 세션값은 변경되지 않은 상태
		
		System.out.println("userE : " + user.getEmail());
		System.out.println("userP : " + user.getPassword());
		
		// 세션 등록 (서비스 단에서 하게 되버리면 수정된 정보가 적용(Commit)되지 않은 상태에서 검사를 진행하므로
		// 인증이 실패할 수 있기 때문에 Service->Controller로 돌아온 후에 토큰을 적용시켜주는 것이 좋음
		// 여기 토큰 등록할 때 넣는 getPassword는 정제되기 전(인코딩 되기 전 순수한 패스워드) 패스워드를 넣는 것
		Authentication authentication = authenticationManager.
				authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
//		Authentication authentication = // 새로운 authentication 토큰 생성
//				new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		securityContext.setAuthentication(authentication); // 강제 주입
//		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext); // 세션에 securityContext 주입
//		현재는 SPRING_SECURITY_CONTEXT에 직접 접근이 불가능해서 사용할 수 없음
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
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
