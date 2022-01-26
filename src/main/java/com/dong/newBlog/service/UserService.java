package com.dong.newBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	// 회원가입 서비스
	@Transactional
	public void insertUser(User user) {
		user.setRole(RoleType.USER); // 권한 설정
		user.setPassword(encoder.encode(user.getPassword())); // 비밀번호 해싱
		userRepository.save(user);
	}
	
	@Transactional
	public void updateUser(User user) {
		// 수정 시에는 영속성 컨텍스트 User Object를 영속화시키고 영속화된 User Object를 수정
		User persistenceUser = userRepository.findByEmail(user.getEmail()).orElseThrow(()->{
			return new IllegalArgumentException("(UserService.updateUser) Can not Find Email : " + user.getEmail());
		});
		persistenceUser.setPassword(encoder.encode(user.getPassword())); // 비밀번호 인코딩
		persistenceUser.setUsername(user.getUsername());
		
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
