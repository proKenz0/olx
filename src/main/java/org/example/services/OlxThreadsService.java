package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.interfaces.IOlxThreadsService;
import org.example.interfaces.ITokenService;
import org.example.models.DataWrapper;
import org.example.models.OlxThread;
import org.example.olx_config.UrlAdress;
import org.example.operations.JsonParser;
import org.example.requests.HttpReq;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OlxThreadsService implements IOlxThreadsService {

    private List<OlxThread> threads;
    private ITokenService tokenService;
    private final String THREADS_URL = "https://www.olx.ua/api/partner/threads";


    public OlxThreadsService(ITokenService tokenService) throws Exception {
        this.tokenService = tokenService;
        this.threads = getThreadList();
    }

    @Override
    public List<OlxThread> getThreadList() throws Exception {
        try {
            String response = HttpReq.getRequest(THREADS_URL, tokenService.getHeaders());
            threads = JsonParser.parseJson(response, new TypeReference<DataWrapper<OlxThread>>() {
            }).getData();
        } catch (JsonProcessingException e) {
            throw new IOException("JsonProcessingException in getThreadList");
        } catch (IOException e) {
            throw new IOException("IOException in getThreadList");
        }
        return threads;
    }

    @Override
    public List<OlxThread> getUnreadThreads() {
        return threads.stream().filter(thr->thr.getUnread_count() > 0).collect(Collectors.toList());
    }
}
