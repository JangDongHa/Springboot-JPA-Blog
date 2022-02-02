package com.dong.newBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.dong.newBlog.model.KakaoProfile;
import com.dong.newBlog.model.OAuthToken;
import com.dong.newBlog.model.User;
import com.dong.newBlog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 /인 index.jsp 역시 허용
// static 이하에 있는 /js/**, /css/**, /image/** 역시 허용
// 결론적으로, 인증이 필요없는 곳은 /auth로 모두 허용

@Controller
public class UserController {
	
	@Value("${kakao.key}")
	private String kakaoKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	// 매개변수에 기본 자료형 매개변수를 받으면 자동으로 쿼리스트링에서 찾음
	// public @ResponseBody String kakaoCallback(String code) 이런식으로 하면 rest처럼 사용가능
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) throws JsonMappingException, JsonProcessingException { // Data를 리턴해주는 컨트롤러 함수가 됨(Rest처럼)
		
		// POST 방식으로 Key=Value 타입의 데이터를 요청해야함 (카카오쪽으로)
		// 이 때 필요한 라이브러리 : RestTemplate
		// 이 밖에서도 Retrofit2(Android에서 많이 쓰임), OkHttp 등도 존재
		// Header 만들어주고 파라미터 덩어리 만들고(이후에는 하나하나 변수로 만들어서 쓰는 것이 좋음)
		// HttpEntity에 params 와 header를 넣어줌
		// ResponseEntity 정의
		
		RestTemplate rTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "0c41f3aa58c09917f905ac72d4ac7a56");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, httpHeaders);
		ResponseEntity<String> response = rTemplate.exchange(
					"https://kauth.kakao.com/oauth/token",
					HttpMethod.POST,
					kakaoTokenRequest,
					String.class
				);
				// Http 요청 주소 : 토큰 주소
				// 메서드 방식
				// 데이터
				// 응답받을 데이터 형식
		
		// Gson, Json Simple, ObjectMapper
		// 이러한 라이브러리를 통해 body에 있는 데이터를 모델화 해서 매핑시켜 데이터 추출 가능
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) { // 파싱 실패(Getter,Setter가 없는 경우 등)
			e.printStackTrace();
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("oAuth : " + oAuthToken.getAccess_token());
		
		RestTemplate rTemplate2 = new RestTemplate();
		HttpHeaders httpHeaders2 = new HttpHeaders();
		httpHeaders2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		httpHeaders2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = 
				new HttpEntity<>(httpHeaders2);
		ResponseEntity<String> response2 = rTemplate2.exchange(
					"https://kapi.kakao.com/v2/user/me",
					HttpMethod.POST,
					kakaoProfileRequest,
					String.class
				);
		
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) { // 파싱 실패(Getter,Setter가 없는 경우 등)
			e.printStackTrace();
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		String kakaoEmail = kakaoProfile.getKakao_account().getEmail();
		// User Object에 필요한 정보 : username, password, email
		System.out.println("Kakao ID : " + kakaoProfile.getId());
		System.out.println("Kakao Email : " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("Kakao username : " + kakaoProfile.getProperties().getNickname());
		//String tempPassword = UUID.randomUUID().toString();
		System.out.println("Password : kakaoKey");
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getProperties().getNickname())
				.password(kakaoKey)
				.email(kakaoEmail)
				.oauth("kakao")
				.authStatus(true)
				.build();
		// 가입자 혹은 비가입자인지 확인
		if (userService.findUser(kakaoEmail) == null) { // This is new User
			userService.insertUser(kakaoUser);
		}
		Authentication authentication = authenticationManager.
				authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getEmail(), kakaoKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("/user/authEmail")
	public String authEmailForm() {
		return "/user/authEmail";
	}
	
	@GetMapping("/auth/signupConfirm")
	public String signupConfirm(String authKey, String email) {
		System.out.println("/auth/signupConfirm");
		User authUser = userService.findUser(email);
		
//		System.out.println("authUser Email : " + authUser.getEmail());
//		System.out.println("authUser AuthKey : " + authUser.getAuthKey());
//		
//		System.out.println("import Email : " + email);
//		System.out.println("import authKey : " + authKey);
		if (authUser.getEmail().equals(email))
			if (authUser.getAuthKey().equals(authKey)) { // 인증 성공
				authUser.setAuthStatus(true);
				userService.updateUser(authUser, false);
			}
		
		return "index";
	}
	
	
}
