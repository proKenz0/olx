package org.example.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.interfaces.repositories.IOlxThreadsRepository;
import org.example.models.DataWrapper;
import org.example.models.OlxThread;
import org.example.operations.JsonParser;
import org.example.requests.HttpReq;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OlxThreadsRepository implements IOlxThreadsRepository {

    private final String THREADS_URL = "https://www.olx.ua/api/partner/threads";

    @Override
    public List<OlxThread> getThreadList(Map<String, String> headers) throws Exception {
        try {
            String response = HttpReq.getRequest(THREADS_URL, headers);
            return JsonParser.parseJson(response, new TypeReference<DataWrapper<OlxThread>>() {
            }).getData();
        } catch (JsonProcessingException e) {
            throw new IOException(e.getMessage() + "\nJsonProcessingException in OlxThreadsRepository in getThreadList");
        } catch (IOException e) {
            throw new IOException(e.getMessage() + "\nIOException in OlxThreadsRepository in getThreadList");
        }
    }
}
