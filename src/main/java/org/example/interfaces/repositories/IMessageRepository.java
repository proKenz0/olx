package org.example.interfaces.repositories;

import org.example.models.Message;

import java.util.List;
import java.util.Map;

public interface IMessageRepository {
    public List<Message> getMessageList(String threadId, Map<String, String> headers) throws Exception;
    public void sendMessage(String threadId, String message, Map<String, String> headers) throws Exception;
}
