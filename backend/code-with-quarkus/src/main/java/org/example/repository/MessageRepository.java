package org.example.repository;

import java.util.List;

import org.example.entity.Message;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageRepository implements PanacheRepository<Message> {

    // Get chat between two users
    public List<Message> findChat(String user1, String user2) {
        return list(
            "(sender = ?1 AND receiver = ?2) OR (sender = ?2 AND receiver = ?1) ORDER BY timestamp",
            user1, user2
        );
    }
}