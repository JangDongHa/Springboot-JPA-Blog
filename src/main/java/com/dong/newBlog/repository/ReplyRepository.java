package com.dong.newBlog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dong.newBlog.dto.ReplySaveRequestDTO;
import com.dong.newBlog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	
	// mSave라는 메서드는 JpaRepository에 정해진 메서드가 아닌 자체적인 메서드이기 때문에
	// @Query 라는 메서드로 직접 알려주어야 함
	// nativeQuery = true -> 내가 적은 쿼리가 작동할 수 있게 해줌
	// Modifying 을 걸어주고 int값을 리턴해야함(SELECT 제외)
	@Modifying
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate)"
			+ " VALUES(?1,?2,?3,now())", nativeQuery = true)
	int mSave(int userId, int boardId, String content);  // 업데이트 된 행의 갯수 리턴(SELECT 제외 전부 동일)
}
