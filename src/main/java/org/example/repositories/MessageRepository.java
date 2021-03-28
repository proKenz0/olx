package org.example.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.interfaces.repositories.IMessageRepository;
import org.example.models.DataWrapper;
import org.example.models.Message;
import org.example.operations.JsonParser;
import org.example.requests.HttpReq;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MessageRepository implements IMessageRepository {

    private static final String MESSAGES_START = "https://www.olx.ua/api/partner/threads/";
    private static final String MESSAGES_END = "/messages";
    private static final String THREAD_COMMAND_START = "https://www.olx.ua/api/partner/threads/";
    private static final String THREAD_COMMAND_END = "/commands";
    private static final String COMMAND_READ = "command=mark-as-read";


    @Override
    public List<Message> getMessageList(String threadId, Map<String, String> headers) throws Exception {
        try {
            String response = HttpReq.getRequest(getMessagesUrl(threadId), headers);
            return JsonParser.parseJson(response, new TypeReference<DataWrapper<Message>>() {
            }).getData();
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException(e.getMessage() + "\nException in MessageRepository getMessageList");
        }
    }

    @Override
    public void sendMessage(String threadId, String message, Map<String, String> headers) throws Exception {
        try {
            HttpReq.postRequest(getMessagesUrl(threadId), message, headers);
            HttpReq.postRequest(getCommandUrl(threadId), COMMAND_READ, headers);
        } catch (IOException e) {
            throw new IOException(e.getMessage() + "\nIOException in MessageRepository in sendMessage");
        }
    }

    private String getMessagesUrl(String id){
        return MESSAGES_START + id + MESSAGES_END;
    }

    public String getCommandUrl(String id){
        return THREAD_COMMAND_START + id + THREAD_COMMAND_END;
    }
}
