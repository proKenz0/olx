package org.example.interfaces;

import org.example.models.Message;
import org.example.models.OlxThread;

import java.io.IOException;
import java.util.List;

public interface IMessagesService {
    List<Message> getMessageList(String threadId) throws Exception;
    List<Message> getUnreadMessageList(String threadId) throws Exception;
    void sendMessage(String threadId, String message) throws Exception;
    boolean isSendMessage(String threadId) throws Exception;
}
