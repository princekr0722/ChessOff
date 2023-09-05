package com.chessoff.config.websocket;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WebSocketSessionService {

	public void closeSession(WebSocketSession session) {
		try {
			session.close(new CloseStatus(CloseStatus.POLICY_VIOLATION.getCode(), "Custom reason"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
