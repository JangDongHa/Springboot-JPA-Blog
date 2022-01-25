package com.dong.newBlog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dong.newBlog.config.auth.PrincipalDetail;
import com.dong.newBlog.config.auth.PrincipalDetailService;

// Bean 등록 : Spring  Container에서 객체를 관리할 수 있게 하는 것
// Configuration : config 관련 Bean 등록
// Security는 컨트롤러에서 제어하기 전에 먼저 모든 Request를 가로채서 필터링 진행
// 아래 3개는 세트로 Security Config 관련 Annotation임
@Configuration // Bean 등록 (IOC 관리)
@EnableWebSecurity // Filter를 거는 것 (=필터 추가) = Spring Security가 이미 활성화 되어있지만 추가적인 설정을 여기서 하겠다는 것
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 것
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;

	@Bean // return 하는 메서드를 Spring이 관리함 = 해당 메서드를 어디서든 사용 가능
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); // BCrypt : Security가 들고있는 메소드
	}
	
	// Security가 대신 로그인을 하기 위해 아이디/패스워드를 가로채게 되는데
	// 해당 패스워드가 무엇으로 해시가 되서 회원가입이 되었는지 알아야함
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable() // csrf 토큰 비활성화(test 시 걸어두는 것이 좋음)
		.authorizeRequests() // auth Request가 들어왔는데
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") // 그 URL이 /auth/.... 등등 이면
				.permitAll() // 모두 허용
				.anyRequest() // 그러나 그 외 나머지 모든 request는
				.authenticated()// 인증이 되어야 이용 가능
		.and() // 그리고
				.formLogin() // 인증(FormLogin)이 필요한 경우에는
				.loginPage("/auth/loginForm") // 자동으로 loginPage로 이동하게
				.loginProcessingUrl("/auth/loginProc") // 이후에 스프링 Security가 해당 주소로 요청이 오는 로그인을 가로채서 대신 로그인 진행
				.defaultSuccessUrl("/"); // 로그인이 성공하면 기본적으로 "/"로 이동
	}
}
