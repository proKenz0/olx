package org.example.interfaces;

import org.example.models.OlxThread;

import java.io.IOException;

public interface IAccountService {
    void printAllMessages();

    void printUnreadThreadsMessages();

    void printUnreadMessages();

    void giveStandartAnswers() throws Exception;


}
