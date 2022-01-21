package com.dong.newBlog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dong.newBlog.model.User;
import com.dong.newBlog.repository.UserRepository;

// Spring이 Component Scan을 통해 bean에 등록 (= IOC)
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public int insertUser(User user) {
		try {
			userRepository.save(user);
			return 1;
		}catch(Exception e) {
			//e.printStackTrace();
			//System.out.println("UserService : insertUser() : " + e.getMessage());
			return -1;
		}
	}
}
