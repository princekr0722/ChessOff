package com.chessoff.config.websocket;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import com.chessoff.exceptions.GameRoomException;
import com.chessoff.models.dto.GameRoom;
import com.chessoff.service.GameRoomService;
import com.sun.security.auth.UserPrincipal;

import org.springframework.messaging.support.ChannelInterceptor;

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {

	@Autowired
	private GameRoomService gameRoomService;

	@Autowired
	private SendMessageChannel sendMessageChannel;
	
	@Autowired
	private WebSocketSessionService socketSessionService;

	@Override
	public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
		final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		String playerIdentifier = accessor.getFirstNativeHeader(WebSocketConstants.PLAYER_IDENTIFIER_HEADER);

		if (StompCommand.CONNECT == accessor.getCommand()) {
			isValidSocketRequest(accessor);

			accessor.getSessionAttributes().put("PlayerIdentifier", playerIdentifier);

		} else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {

			String destination = accessor.getDestination(); // ex: /gameroom/RoomNumberspsoesdkjsuenlkrie
			String roomNo = destination.substring(destination.lastIndexOf("/") + 1);

			GameRoom room = isValidSocketRequest(accessor, roomNo);

			room.incrementTotalSubscription();
			accessor.setUser(new UserPrincipal(playerIdentifier));

			if (room.getTotalSubscription() == 2) {
				// start the game
			} else {
				new Thread(() -> {
					WebSocketSession session = (WebSocketSession) accessor.getHeader("simpSession");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					socketSessionService.closeSession(session);
				}).start();
			}

		} else if (StompCommand.SEND == accessor.getCommand()) {
//			String destination = accessor.getDestination();
//			String receiver = destination.substring(destination.lastIndexOf("/") + 1);

			Message<byte[]> modifiedMsg = sendMessageChannel.getSendableMessage(message, accessor);
			return modifiedMsg;
		}
		return message;
	}

	private GameRoom isValidSocketRequest(StompHeaderAccessor accessor) {
		String roomNo = (String) accessor.getFirstNativeHeader(WebSocketConstants.ROOM_NO_HEADER);
		GameRoom room = isValidSocketRequest(accessor, roomNo);

		return room;
	}

	private GameRoom isValidSocketRequest(StompHeaderAccessor accessor, String roomNo) {
		String playerIdentifier = (String) accessor.getFirstNativeHeader(WebSocketConstants.PLAYER_IDENTIFIER_HEADER);

		GameRoom room = gameRoomService.findGameRoom(roomNo);
		if (room == null)
			throw new GameRoomException("Invalid room number");

		if (!room.getPlayerOneIdentifier().equals(playerIdentifier)
				&& !room.getPlayerTwoIdentifier().equals(playerIdentifier)) {
			throw new GameRoomException("You does not belong to the room you're trying to subscribe.");
		}

		return room;
	}
}