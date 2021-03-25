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
import java.util.stream.Collectors;

public class MessagesService implements IMessagesService {

    private String standartMessage;
    private String commandRead;
    private ITokenService tokenService;
    private static final String MESSAGES_START = "https://www.olx.ua/api/partner/threads/";
    private static final String MESSAGES_END = "/messages";
    private static final String THREAD_COMMAND_START = "https://www.olx.ua/api/partner/threads/";
    private static final String THREAD_COMMAND_END = "/commands";

    public MessagesService(ITokenService tokenService) {
        this.standartMessage = "text=Доброго дня.\n" +
                "Ціна - 90 грн.\n" +
                "Самовивозу немає.\n" +
                "Доставка новою поштою або олх.\n" +
                "Залишіть номер вайберу, пришлемо фото чохлів на вашу модель.";

        this.commandRead = "command=mark-as-read";
        this.tokenService = tokenService;
    }

    @Override
    public List<Message> getMessageList(String threadId) throws Exception {
        try {
            String response = HttpReq.getRequest(getMessagesUrl(threadId), tokenService.getHeaders());
            return JsonParser.parseJson(response, new TypeReference<DataWrapper<Message>>() {
            }).getData();
        } catch (JsonProcessingException e) {
            throw e;
//            throw new IOException("JsonProcessingException in getMessageList");
        } catch (IOException e) {
            throw new IOException("Exception in getMessageList");
        }
    }
    @Override
    public List<Message> getUnreadMessageList(String threadId) throws Exception {
        try {
            String response = HttpReq.getRequest(getMessagesUrl(threadId), tokenService.getHeaders());
            return JsonParser.parseJson(response, new TypeReference<DataWrapper<Message>>() {
            }).getData().stream().filter(x->x.isIs_read()==false).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new IOException("JsonProcessingException in getUnreadMessageList");
        } catch (IOException e) {
            throw new IOException("IOException in getUnreadMessageList");
        }
    }

    @Override
    public void sendStandartMessage(String threadId) throws Exception {
        try {
            HttpReq.postRequest(getCommandUrl(threadId), commandRead, tokenService.getHeaders());
            HttpReq.postRequest(getMessagesUrl(threadId), standartMessage, tokenService.getHeaders());
        } catch (IOException e) {
            throw new IOException("IOException in sendStandartMessage");
        }
    }

    @Override
    public boolean isSendMessage(String threadId) throws Exception {
        return getMessageList(threadId).stream().anyMatch(x-> x.getType().equals("sent"));
    }

    private String getMessagesUrl(String id){

        return MESSAGES_START + id + MESSAGES_END;
    }

    public String getCommandUrl(String id){

        return THREAD_COMMAND_START + id + THREAD_COMMAND_END;
    }
}
