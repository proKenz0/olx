package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.interfaces.repositories.IMessageRepository;
import org.example.interfaces.service.IMessagesService;
import org.example.models.Message;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessagesService implements IMessagesService {

    IMessageRepository messageRepository;

    public MessagesService(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getMessageList(String threadId, Map<String, String> headers) throws Exception {
        try {
            return messageRepository.getMessageList(threadId, headers);
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\nException in MessagesService in getMessageList");
        }
    }

    @Override
    public List<Message> getUnreadMessageList(String threadId, Map<String, String> headers) throws Exception {
        try {
            return messageRepository.getMessageList(threadId, headers).stream()
                    .filter(x -> x.isIs_read() == false).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new IOException(e.getMessage() + "\nJsonProcessingException in getUnreadMessageList");
        } catch (IOException e) {
            throw new IOException(e.getMessage() + " \nIOException in getUnreadMessageList");
        }
    }

    @Override
    public void sendMessage(String threadId, String message, Map<String, String> headers) throws Exception {
        try {
            messageRepository.sendMessage(threadId, message, headers);
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\nException in MessagesService in sendMessage");
        }
    }

    @Override
    public boolean isSendMessage(String threadId, Map<String, String> headers) throws Exception {
        try {
            return messageRepository.getMessageList(threadId, headers).stream().anyMatch(x -> x.getType().equals("sent"));
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\nException in MessagesService in isSendMessage");
        }
    }


}
