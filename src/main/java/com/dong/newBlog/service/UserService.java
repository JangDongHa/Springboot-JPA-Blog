package com.dong.newBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dong.newBlog.model.RoleType;
import com.dong.newBlog.model.User;
import com.dong.newBlog.repository.UserRepository;

// Spring이 Component Scan을 통해 bean에 등록 (= IOC)
@Service
public class UserService {
	
	@Value("${kakao.key}")
	private String kakaoKey;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Transactional (readOnly = true)
	public User findUser(String email) {
		return userRepository.findByEmail(email).orElseGet(()->{
			return null;
		}); // get 하면 없으면 null을 뿌려줌 (확실x), orElseGet은 해당 메서드와 같이 null을 정의해줄 때 사용
	}

	// 회원가입 서비스
	@Transactional
	public String insertUser(User user) {
		String decodePass = user.getPassword();
		user.setRole(RoleType.USER); // 권한 설정
		user.setPassword(encoder.encode(user.getPassword())); // 비밀번호 해싱
		String authKey = mailSendService.sendAuthMail(user);
		user.setAuthKey(authKey);
		userRepository.save(user);
		return decodePass;
	}
	
	
	@Transactional
	public User updateUser(User user, boolean passwordChange) {
		// 수정 시에는 영속성 컨텍스트 User Object를 영속화시키고 영속화된 User Object를 수정
		User persistenceUser = userRepository.findByEmail(user.getEmail()).orElseThrow(()->{
			return new IllegalArgumentException("(UserService.updateUser) Can not Find Email : " + user.getEmail());
		});
		if (persistenceUser.getOauth() == null && passwordChange) // oAuth 유저가 아닌 경우
			persistenceUser.setPassword(encoder.encode(user.getPassword())); // 비밀번호 인코딩
		else if (persistenceUser.getOauth() != null) // oAuth 유저인 경우
			user.setPassword(kakaoKey);
		persistenceUser.setUsername(user.getUsername());
		
		return user;
		
		
		// 현재 세션 Authentication Token 확인
		// System.out.println("test : " + SecurityContextHolder.getContext().getAuthentication().getName());
		
		// 회원수정 함수 종료 시(서비스 종료 시)
		// Transaction이 종료되면서 Commit 진행
		// = 변화가 감지되면 Dirty Checking이 되어 변화가 된 것을 자동으로 Update 해서 Commit함
	}

//	@Transactional(readOnly = true) // SELECT 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료 (readOnly를 하면 정합성 유지 가능)
//	public User selectUser(User user) {
//		return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
//	}
}
