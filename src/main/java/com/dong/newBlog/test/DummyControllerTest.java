package com.dong.newBlog.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dong.newBlog.model.RoleType;
import com.dong.newBlog.model.User;
import com.dong.newBlog.repository.UserRepository;

// HTML 파일이 아니라 DATA를 Return 해주는 컨트롤러
@RestController
public class DummyControllerTest {

	// 의존성 주입(DI 하는 것)
	@Autowired
	private UserRepository userRepository;

	// Put : Update
	// Save 함수는 id를 전달하지 않으면 insert 해주고
	// Save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update 해주고
	// Save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert 를 진행

	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("Delete Failed (Can't find ID)");
		});

		userRepository.delete(user);
		return "Delete Success";
	}

	@Transactional // DB Transaction (updateUser가 종료될 때까지), 함수 종료 시 자동 Commit
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User reqUser) { // json으로 데이터를 요청하면 자동으로 Java Object로
																				// 변환(MessageConverter의 Jackson 라이브러리에서
																				// 처리)
		System.out.println("id : " + id);
		System.out.println("Pass : " + reqUser.getPassword());
		System.out.println("email : " + reqUser.getEmail());

		User originUser = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("Update Failed (Can't find ID)");
		});
		originUser.setEmail(reqUser.getEmail());
		originUser.setPassword(reqUser.getPassword());

		// userRepository.save(originUser);
		// Dirty Checking
		return originUser;
	}

	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}

	// 한 페이지 당 2건의 데이터를 리턴받을 예정
	// Pageable을 이용하여 아주 간단하게 페이징 구현이 가능함
	// @PageableDefault(size, sort, direction) Pageable pageable
	// size = 페이지 당 몇 건?, sort = 페이징 할 것?, direction = Sorting 방식?
	// ASC = URL?page=0~x번까지 -> 게시판 기준으로 하면 가장 옛날 글이 0번부터 표시되는 것 (Queue 느낌)
	// DESC = URL?page=x~0번까지 -> 게시판 기준으로는 이게 자연스러움. 최신 게시글이 0번이기 때문 (Stack 느낌)
	// URL은 /dummy/user+ ?page=숫자 <- 이런식으로 매핑하면 됨 (그냥 URL만 치면 ?page=0)
	// Return 을 Page 형으로 받을 수 있지만 필요할 때 다시 학습
	@GetMapping("/dummy/user")
	public List<User> pageList(
			@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		// pagingUser.isFirst , isLast 등 여러가지 메서드들을 제공해줌
		return pagingUser.getContent();
		// return userRepository.findAll(pageable).getContent();
	}

	// {id} : 파라미터를 전달받을 수 있음
	// http://localhost:8000/blog/dummy/user/3 (DB Select Test)
	@GetMapping("/dummy/user/{id}") // {parameter} 와 PathVariable int parameter 이름이 같아야함
	public User detail(@PathVariable int id) { // @PathVariable : {value} 값 매핑해서 매개변수화
		// findById의 return은 Optional임. 원래 User 객체를 주는 게 맞지만 DB에서 못찾는 경우가 있기 때문에
		// Optional로 제공함
		// findById.get하면 null 무시하고 그냥 쏴주는 것 (null이 리턴 될 일이 없을 경우)
		// findById.orElseGet(new Supplier) 하고 인터페이스 오버라이드 진행
//		userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				// null 이면 빈 객체를 반환
//				return new User();
//			}
//		});

//		return userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				// Spring에서 null 값을 찾을 때 IllegalArg 예외를 Throw하는 것을 권장(FindById 참조)
//				return new IllegalArgumentException("해당 유저 존재 x (ID : " + id + ")");
//			}
//			
//		});

		// 람다식 (위에 있는 Supplier를 자동으로 람다식을 통해 자바가 매핑을 진행해줌)
		return userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 사용자 존재 x (ID : " + id + ")");
		});

		// getById(), findById() 중 findById() 사용하는 것을 추천
		// orElseThrow를 통해 예외에 대한 처리가 가능하기 때문에
		// 여기서 Return은 User 객체 -> html에서는 이해할 수 없을 -> json으로 변환해서 뿌려줌(MessageConverter가
		// (Jackson이라는 라이브러리를 호출하여) 응답 시 자동 작동하여 변환 수행)
	}

	// http://localhost:8000/blog/dummy/join
	// http의 body에 username, password, email의 데이터를 가지고 요청
	// RequestParam을 이용하거나 패스워드와 이메일처럼 그냥 받아도 상관x
	// key=value 형태로 저장받아옴
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("Username is " + user.getUsername());
		System.out.println("Password is " + user.getPassword());
		System.out.println("Email is " + user.getEmail());
		if (user.getRole() == null)
			user.setRole(RoleType.USER);
		System.out.println("The user is " + user);
		try {
			userRepository.save(user);
			System.out.println("Saved User Info!");
		} catch (Exception e) {
			System.out.println("Could not save User Info Because " + e);
		}
		return "Joined in my Blog Complete";
	}
}
