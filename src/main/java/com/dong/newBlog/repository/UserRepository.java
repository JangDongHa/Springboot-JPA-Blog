package com.dong.newBlog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dong.newBlog.model.User;

// 현재 Class의 JpaRepository는 User 테이블을 관리하고 User 테이블의 PK는 Integer이다.
// = 현재 Class는 User 테이블을 참조할거고 PK는 Integer이다
// 아무것도 적지 않아도 CRUD 기능 사용 가능(extends 따라가보면 존재함)
// = DAO
// 자동으로 bean등록이 됨(DI 가능)
// 원래는 @Repository로 명시가 필요했지만 이젠 필요x
public interface UserRepository extends JpaRepository<User, Integer> {
	// JPA Naming 쿼리 전략
	// findBy~And~이나 findBy~ 으로 적으면 자동으로 SELECT * FROM WHRER username = ? 이런식으로 쿼리문
	// 실행
	// 이것을 JPA Naming 쿼리 전략이라고 함
//	User findByEmailAndPassword(String email, String password);

	// 혹은 이런식으로 직접 명시도 가능
//	@Query(value = "SELECT * FROM USER FROM WHERE username=?1AND password=?2", nativeQuery = true)
//	User login(String username, String password);
	
	// SELECT * FROM USER WHERE EMAIL = 1?
	Optional<User> findByEmail(String email);
}
