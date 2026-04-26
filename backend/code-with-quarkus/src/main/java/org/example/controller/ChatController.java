package org.example.controller;

import java.util.List;

import org.example.entity.Message;
import org.example.service.ChatService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/chat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChatController {

    @Inject
    ChatService chatService;

    // Save message
    @POST
    public Message sendMessage(Message message) {
        return chatService.saveMessage(message);
    }

    // Get chat history
    @GET
    @Path("/{user1}/{user2}")
    public List<Message> getChat(@PathParam("user1") String user1,
                                @PathParam("user2") String user2) {
        return chatService.getChat(user1, user2);
    }
}