package org.example.interfaces.repositories;

import org.example.models.OlxThread;

import java.util.List;
import java.util.Map;

public interface IOlxThreadsRepository {
    public List<OlxThread> getThreadList(Map<String, String> headers) throws Exception;
}
