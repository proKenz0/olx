package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.interfaces.IMessagesService;
import org.example.interfaces.ITokenService;
import org.example.models.DataWrapper;
import org.example.models.Message;
import org.example.olx_config.UrlAdress;
import org.example.operations.JsonParser;
import org.example.requests.HttpReq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessagesService implements IMessagesService {

    private final String COMMAND_READ = "command=mark-as-read";
    private static final String MESSAGES_START = "https://www.olx.ua/api/partner/threads/";
    private static final String MESSAGES_END = "/messages";
    private static final String THREAD_COMMAND_START = "https://www.olx.ua/api/partner/threads/";
    private static final String THREAD_COMMAND_END = "/commands";

    @Override
    public List<Message> getMessageList(String threadId, Map<String, String> headers) throws Exception {
        try {
            String response = HttpReq.getRequest(getMessagesUrl(threadId), headers);
            return JsonParser.parseJson(response, new TypeReference<DataWrapper<Message>>() {
            }).getData();
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException(e.getMessage() + "\nException in getMessageList");
        }
    }
    @Override
    public List<Message> getUnreadMessageList(String threadId, Map<String, String> headers) throws Exception {
        try {
            String response = HttpReq.getRequest(getMessagesUrl(threadId), headers);
            return JsonParser.parseJson(response, new TypeReference<DataWrapper<Message>>() {
            }).getData().stream().filter(x->x.isIs_read()==false).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new IOException(e.getMessage() + "\nJsonProcessingException in getUnreadMessageList");
        } catch (IOException e) {
            throw new IOException(e.getMessage() + " \nIOException in getUnreadMessageList");
        }
    }

    @Override
    public void sendMessage(String threadId, String message, Map<String, String> headers) throws Exception {
        try {
            HttpReq.postRequest(getMessagesUrl(threadId), message, headers);
            HttpReq.postRequest(getCommandUrl(threadId), COMMAND_READ, headers);
        } catch (IOException e) {
            throw new IOException(e.getMessage() + "\nIOException in sendStandartMessage");
        }
    }

    @Override
    public boolean isSendMessage(String threadId, Map<String, String> headers) throws Exception {
        return getMessageList(threadId, headers).stream().anyMatch(x-> x.getType().equals("sent"));
    }

    private String getMessagesUrl(String id){

        return MESSAGES_START + id + MESSAGES_END;
    }

    public String getCommandUrl(String id){

        return THREAD_COMMAND_START + id + THREAD_COMMAND_END;
    }
}
