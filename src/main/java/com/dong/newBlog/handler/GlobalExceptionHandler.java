package com.dong.newBlog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

// Exception 에 대한 Handler
// ControllerAdvice : 어디에서든 Exception이 발생해도 이쪽으로 올 수 있게
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	// value에 있는 Exception이 발생하면 자동으로 여기 있는 핸들러 내 해당 메서드로 전달을 해줌
	@ExceptionHandler(value = IllegalArgumentException.class)
	public String handleArgumentException(IllegalArgumentException e) {
		return "<H1>" + e.getMessage() + "</H1>";
	}
}
