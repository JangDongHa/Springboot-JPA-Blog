package com.dong.newBlog.test;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

// @Data : @Getter + @Setter
// @AllArgsConstructor : 생성자, @NoArgsConstructor : 빈 생성자
// @RequiredArgsConstructor : final 붙은 얘들에 대한 생성자
// @Builder : args 중에 받고 싶은 것만 일부 받아서 생성하는 생성자를 만들어줌
// Member m1 = Member.builder().password("12").email("123").username("name").build();
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
	private int id; // DB의 불변성 법칙을 유지하기 위해 final을 붙임 (특수한 경우, 안 붙여도 없음)
	private String username;
	private String password;
	private String email;
}
