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
import java.util.Map;
import java.util.stream.Collectors;

public class OlxThreadsService implements IOlxThreadsService {

    private final String THREADS_URL = "https://www.olx.ua/api/partner/threads";

    @Override
    public List<OlxThread> getThreadList(Map<String, String> headers) throws Exception {
        try {
            String response = HttpReq.getRequest(THREADS_URL, headers);
            return JsonParser.parseJson(response, new TypeReference<DataWrapper<OlxThread>>() {
            }).getData();
        } catch (JsonProcessingException e) {
            throw new IOException(e.getMessage() + "\nJsonProcessingException in getThreadList");
        } catch (IOException e) {
            throw new IOException(e.getMessage() + "\nIOException in getThreadList");
        }
    }

    @Override
    public List<OlxThread> getUnreadThreads(Map<String, String> headers) throws Exception {
        try {
            return getThreadList(headers).stream().filter(thr->thr.getUnread_count() > 0).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\nException in getUnreadThreads");
        }
    }
}
