package com.dong.newBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
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

//	@Transactional(readOnly = true) // SELECT 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료 (readOnly를 하면 정합성 유지 가능)
//	public User selectUser(User user) {
//		return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
//	}
}
