package org.example.services;

import org.example.interfaces.repositories.IOlxThreadsRepository;
import org.example.interfaces.service.IOlxThreadsService;
import org.example.models.OlxThread;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OlxThreadsService implements IOlxThreadsService {

    private IOlxThreadsRepository olxThreadsRepository;
    private static OlxThreadsService instance;

    private OlxThreadsService(IOlxThreadsRepository olxThreadsRepository) {
        this.olxThreadsRepository = olxThreadsRepository;
    }

    public static OlxThreadsService getInstance(IOlxThreadsRepository olxThreadsRepository){
        if(instance == null)
            instance = new OlxThreadsService(olxThreadsRepository);
        return instance;
    }

    @Override
    public List<OlxThread> getThreadList(Map<String, String> headers) throws Exception {
        try {
            return olxThreadsRepository.getThreadList(headers);
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\nException in OlxThreadsService in getThreadList");
        }
    }

    @Override
    public List<OlxThread> getUnreadThreads(Map<String, String> headers) throws Exception {
        try {
            return olxThreadsRepository.getThreadList(headers).stream().filter(thr -> thr.getUnread_count() > 0)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\nException in OlxThreadsService in getUnreadThreads");
        }
    }
}
