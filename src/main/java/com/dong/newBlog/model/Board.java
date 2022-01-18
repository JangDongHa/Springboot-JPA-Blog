package com.dong.newBlog.model;

import java.sql.Timestamp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 생성자 일부만 설정할 수 있음
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length = 100)
	private String title;
	
	@Lob // 굉장히 대용량 데이터를 처리할 때
	private String content; // SummerNote 라는 Library를 쓸 것임 (<html> tag가 섞여서 들어가기 때문에 굉장히 길어질 수 있음)
	
	@ColumnDefault("0") // 조회수 : 기본(Default)은 0
	private int count;
	
	// ManyToOne 기본 전략 : Eager (무조건 One을 들고오겠다)
	@ManyToOne(fetch = FetchType.EAGER) // Board : Many, User : One (한명의 유저는 여러 개의 게시글을 쓸 수 있다.). 연관관계 설
	@JoinColumn(name="userId") // User라는 객체에서 PK를 userId라는 이름으로 Join한다는 것
	private User user; // 이 게시글을 누가 적었는지 알아야하므로
	// ORM의 강점 중 하나. DB처럼 FK 넣지 않고 객체로 형지정을 할 수 있어서 OOP의 장점을 그대로 사용할 수 있다.
	
	// Lazy 이므로 하나도 안 가져올 수도 있음 (댓글이 없는 경우)
	// OneToMany 기본 전략 : Lazy (내가 필요하면 가져오고 필요하지 않으면 안 가져오겠다)
	// Reply Table에 있는 board를 참조할 것
	// MappedBy가 있으면 연관관계의 주인이 아니다. (FK가 아니므로 DB에 컬럼을 만들지 않음)
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // JoinColumn을 쓸 필요가 없음(쓴다면 DB 1정규화->원자성 위배)
	private List<Reply> reply; // 많은 Reply는 하나의 Board에 담길 수 있기 때문에(ONETOMANY)
	
	@CreationTimestamp
	private Timestamp createDate;
}
