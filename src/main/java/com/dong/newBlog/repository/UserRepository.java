package com.dong.newBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dong.newBlog.model.User;

// 현재 Class의 JpaRepository는 User 테이블을 관리하고 User 테이블의 PK는 Integer이다.
// = 현재 Class는 User 테이블을 참조할거고 PK는 Integer이다
// 아무것도 적지 않아도 CRUD 기능 사용 가능(extends 따라가보면 존재함)
// = DAO
// 자동으로 bean등록이 됨(DI 가능)
// 원래는 @Repository로 명시가 필요했지만 이젠 필요x
public interface UserRepository extends JpaRepository<User, Integer>{
	
}
