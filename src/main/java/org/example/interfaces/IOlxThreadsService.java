package org.example.interfaces;


import org.example.models.OlxThread;

import java.io.IOException;
import java.util.List;

public interface IOlxThreadsService {
    List<OlxThread> getThreadList() throws IOException, Exception;
    List<OlxThread> getUnreadThreads();
}
