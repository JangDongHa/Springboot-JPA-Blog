package com.dong.newBlog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dong.newBlog.model.Board;
import com.dong.newBlog.model.User;


public interface BoardRepository extends JpaRepository<Board, Integer> {
}
