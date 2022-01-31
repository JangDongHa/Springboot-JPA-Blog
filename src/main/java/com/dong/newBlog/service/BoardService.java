package com.dong.newBlog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dong.newBlog.dto.ReplySaveRequestDTO;
import com.dong.newBlog.model.Board;
import com.dong.newBlog.model.Reply;
import com.dong.newBlog.model.User;
import com.dong.newBlog.repository.BoardRepository;
import com.dong.newBlog.repository.ReplyRepository;
import com.dong.newBlog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private UserRepository userRepository;

	// 글쓰기
	@Transactional
	public void writing(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	// 댓글쓰기
	@Transactional
	public void writingReply(ReplySaveRequestDTO rDTO) { // content, username
		replyRepository.mSave(rDTO.getUserId(), rDTO.getBoardId(), rDTO.getContent());
	}

	@Transactional(readOnly = true)
	public Page<Board> listContents(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board viewDetails(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("(BoardService.viewDetails) Can not Find Id : " + id);
		});
	}

	@Transactional
	public void delete(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void deleteReply(int id) {

		replyRepository.deleteById(id);
	}

	@Transactional
	public void update(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("(BoardService.update) Can not Find Id : " + id);
		}); // 영속화 진행
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(Service가 종료될 때) 트랙잭션이 종료
		// 이 때 Dirty Checking 발생 (영속화 되어있는 Board값이 바뀌었기 때문에)
		// 따라서, 자동으로 업데이트 되서 Flush(Commit)가 진행
	}
	
	@Transactional
	public void raiseViewCount(int id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("(BoardService.update) Can not Find Id : " + id);
		}); // 영속화 진행
		
		board.setCount(board.getCount()+1);
	}

	public Reply findReplyId(int replyId) {
		return replyRepository.getById(replyId);
	}
}
