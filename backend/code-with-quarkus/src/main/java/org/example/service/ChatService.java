package org.example.service;

import java.util.List;

import org.example.entity.Message;
import org.example.repository.MessageRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ChatService {

    @Inject
    MessageRepository messageRepository;

    // Save message
    public Message saveMessage(Message message) {
        messageRepository.persist(message);
        return message;
    }

    // Get chat between two users
    public List<Message> getChat(String user1, String user2) {
        return messageRepository.findChat(user1, user2);
    }
}