package com.dong.newBlog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM : 개발 언어 Object -> DB 로 Mapping 해주는 것
// User Class 가 자동으로 MySQL에 테이블이 생성 (= Entity)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert // Default 값 적용을 위해(값이 없으면 Default값 적용. 자세한 내용은 role 쪽 주석 참고)
@Entity // ORM으로써 현재 클래스가 DB 내 테이블 클래스라는 것을 명시해주는 Entity가 맨 아래로 오는 것이 좋음
public class User {
	// ID와 CreateDate 는 둘 다 자동으로 설정되기 때문에 값을 넣어주지 않아도 자동으로 저장이 됨
	
	@Id // Primary Key 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 넘버링
	// 해당 프로젝트에서 연결된 DB의 넘버링 전략을 따라가겠다 (IDENTITY)
	private int id; // auto_increment
	
	@Column(nullable = false, length = 30) // 컬럼으로 설정하고 nullable=false, 길이는 최대 30
	private String username; // User ID
	
	@Column(nullable = false, length = 100) // 나중에 PW를 Hash로 변경해서 비밀번호를 암호화할 예정이기 때문에 넉넉하게 부여
	private String password; // Password
	
	@Column(nullable = false, length = 50)
	private String email; // Email
	
	// Repository에 적용할 때 기본적으로 role 값을 설정해주지 않으면 null이 들어감
	// 그 이유는 기본적으로 repository.save(객체)를 진행하면 객체 전체에 대한 값을 적용하게 되는데
	// role을 받지 않은 상태로 들어가면 role 값에 null로 Value를 적용하기 때문에
	// -> role이 null이면 아예 insert할 때 role 값을 적용하지 못하게 해야함 -> @DynamicInsert
	// DynamicInsert : insert 할 때 null인 값을 제외시켜줌 (Class 위에 작성해야할 Annotation)
	//@ColumnDefault("'user'") // 'user' 로 넣어줘야함(이게 문자라는 것을 알려주기 위해서)
	@Enumerated(EnumType.STRING) // 해당 Enum의 값이 String이라는 것을 알려줌
	private RoleType role; // 나중에는 Enum을 쓰는 것이 좋다. (role : Admin, User, Manager ...)
	// Enum을 써야 도메인 설정에 유리하다(도메인 : 어떠한 범위)
	
	@CreationTimestamp // 시간이 자동으로 입력이 됨
	private Timestamp createDate;
}
