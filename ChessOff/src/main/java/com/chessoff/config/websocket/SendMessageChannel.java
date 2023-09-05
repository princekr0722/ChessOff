package com.chessoff.config.websocket;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.chessoff.models.chess.PieceMove;
import com.chessoff.models.dto.SendableMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SendMessageChannel {
	
	public Message<byte[]> getSendableMessage(final Message<?> message, StompHeaderAccessor accessor) {
		String destination = accessor.getDestination();
		String receiver = destination.substring(destination.lastIndexOf("/") + 1);
		
		byte[] payload = (byte[])message.getPayload();
		
		PieceMove pieceMove = deserializePieceMove(payload);

        // Create a new message with the modified content
        Message<byte[]> modifiedMsg = MessageBuilder.withPayload(serializePieceMove(pieceMove))
                .copyHeaders(message.getHeaders())
                .setHeaders(accessor)
                .build();
        
        // Verify the move here
        
        return modifiedMsg;
	}
	
	

	// Helper method to deserialize the payload (assuming it's a JSON representation
	// of ChatMessage)
	private PieceMove deserializePieceMove(byte[] payload) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(payload, PieceMove.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] serializePieceMove(PieceMove receiveMessage) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		try {
			return objectMapper.writeValueAsBytes(receiveMessage);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
