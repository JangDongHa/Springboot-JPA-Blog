package com.dong.newBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dong.newBlog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	
}
