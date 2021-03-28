package org.example.interfaces.service;

import org.example.models.Message;
import org.example.models.OlxThread;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IMessagesService {
    List<Message> getMessageList(String threadId, Map<String, String> headers) throws Exception;
    List<Message> getUnreadMessageList(String threadId, Map<String, String> headers) throws Exception;
    void sendMessage(String threadId, String message, Map<String, String> headers) throws Exception;
    boolean isSendMessage(String threadId, Map<String, String> headers) throws Exception;
}
