package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.interfaces.repositories.IMessageRepository;
import org.example.interfaces.service.IMessagesService;
import org.example.models.Message;
import org.example.olx_config.AppData;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessagesService implements IMessagesService {

    private IMessageRepository messageRepository;
    private static MessagesService instance;

    private MessagesService(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public static MessagesService getInstance(IMessageRepository messageRepository){
        if (instance == null)
            instance = new MessagesService(messageRepository);
        return instance;
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
            return messageRepository.getMessageList(threadId, headers).stream().anyMatch(x -> x.getType()
                    .equals(AppData.MESSAGE_TYPE));
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\nException in MessagesService in isSendMessage");
        }
    }


}
