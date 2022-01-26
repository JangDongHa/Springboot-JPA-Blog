package com.dong.newBlog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dong.newBlog.model.User;
import com.dong.newBlog.repository.UserRepository;

@Service // Bean 등록
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	// Spring이 로그인 요청을 가로챌 때, email, password 2개의 변수를 가로채는데
	// Password 부분 처리는 알아서 함
	// 우리가 해야할 부분은 해당 email이 DB에 있는지만 확인해주면 됨
	// loadUserByUsername 이기 때문에 프론트 단에서 email이 아니라 username으로 쏴줘야함
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User principal = userRepository.findByEmail(email)
				.orElseThrow(()->{
					System.out.println("(PrincipalDetailService.loadUserByUsername) Cannot find email : " + email);
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + email);
				});
		return new PrincipalDetail(principal); // Security Session에 User 정보 저장 (Type : UserDetails)
	}

}
