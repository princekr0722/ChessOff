package com.chessoff.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TestController {

	List<Object> list = new ArrayList<>();

	@GetMapping("/apicount")
	List<?> apiCount(HttpServletRequest request) {
		list.add(request.getRemoteAddr());
		return list;
	}

	@MessageMapping("/gameroom")
	@SendTo("/gameroom/2")
	public String greeting(@Payload String message) {
		System.out.println(message);
		return message;
	}

}
