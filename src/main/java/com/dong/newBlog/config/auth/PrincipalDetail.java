package com.dong.newBlog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dong.newBlog.model.User;

import lombok.Getter;

// Spring Security가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails Type의 Object를
// Spring Security의 고유한 세션 저장소에 저장
// 그 때 저장되는 Object가 UserDetails Type의 PrinciplaDetail이 저장됨
@Getter
public class PrincipalDetail implements UserDetails{
	private User user; // Composition
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	// 계정이 만료되지 않았는지에 대한 결과를 return (true : 만료되지 않음)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 잠겨있지 않았는지에 대한 결과를 return (true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	// 비밀번호가 만료되지 않았는지에 대한 결과를 return (true : 만료되지 않음)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 활성화(사용가능) 되어있는지 리턴(true : 활성화)
	@Override
	public boolean isEnabled() {
		if (user.isAuthStatus())
			return true;
		return false;
	}
	
	// 계정이 가지고 있는 권한 목록을 Return
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();  // ArrayList를 따라가다보면 Collection 타입임(부모가)

		collectors.add(()->{
			return "ROLE_" + user.getRole();
		});
//		collectors.add(new GrantedAuthority() {
//		
//		@Override
//		public String getAuthority() {
//			// TODO Auto-generated method stub
//			return "ROLE_" + user.getRole(); // "ROLE_" + ... : Spring에서 규정한 규칙 (ROLE_USER 이런식으로)
//		}
//	});
		return collectors;
	}
}
