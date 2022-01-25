package com.dong.newBlog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.dong.newBlog.dto.ResponseDTO;

// Exception 에 대한 Handler
// ControllerAdvice : 어디에서든 Exception이 발생해도 이쪽으로 올 수 있게
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	// value에 있는 Exception이 발생하면 자동으로 여기 있는 핸들러 내 해당 메서드로 전달을 해줌
	@ExceptionHandler(value = Exception.class)
	public void handleArgumentException(Exception e) {
		ResponseDTO<String> error = new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		System.out.println(
				"Error Status : " + error.getStatus() + ", Error Message : " + error.getData());
		// return new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
		// e.getMessage());
	}
}
