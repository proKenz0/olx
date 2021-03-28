package org.example.interfaces.service;


import org.example.models.OlxThread;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IOlxThreadsService {
    List<OlxThread> getThreadList(Map<String, String> headers) throws IOException, Exception;
    List<OlxThread> getUnreadThreads(Map<String, String> headers) throws Exception;
}
