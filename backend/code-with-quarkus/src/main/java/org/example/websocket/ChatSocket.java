package org.example.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.example.entity.Message;
import org.example.service.ChatService;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{username}")
@ApplicationScoped
public class ChatSocket {

    private static final Map<String, Session> userSessions = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    ChatService chatService;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        userSessions.put(username, session);
        System.out.println(username + " connected");
    }

    @OnMessage
    public void onMessage(String messageJson, Session session) {
        try {
            ChatMessageDTO msg = objectMapper.readValue(messageJson, ChatMessageDTO.class);

            // Save to DB
            Message entity = new Message(
                    msg.getSender(),
                    msg.getReceiver(),
                    msg.getContent()
            );
            chatService.saveMessage(entity);

            // Send to receiver if online
            Session receiverSession = userSessions.get(msg.getReceiver());
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.getAsyncRemote().sendText(messageJson);
            }

            // Also send back to sender (for UI sync)
            Session senderSession = userSessions.get(msg.getSender());
            if (senderSession != null && senderSession.isOpen()) {
                senderSession.getAsyncRemote().sendText(messageJson);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        userSessions.remove(username);
        System.out.println(username + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}