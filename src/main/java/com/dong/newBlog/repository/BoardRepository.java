package com.dong.newBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dong.newBlog.model.Board;


public interface BoardRepository extends JpaRepository<Board, Integer> {
}
