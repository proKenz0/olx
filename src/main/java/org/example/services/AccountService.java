package org.example.services;

import org.example.interfaces.service.*;
import org.example.models.OlxThread;
import org.example.operations.Sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class AccountService implements IAccountService, Runnable {

    private IOlxThreadsService threadsService;
    private IMessagesService messagesService;
    private IOutputService outputService;
    private ITokenService tokenService;
    private String name;
    private final String hasUnreadMessageSound = "src/main/resources/lyalya.wav";
    private final String sendMessageSound = "src/main/resources/Papich.wav";
    private String standartMessage;


    public AccountService(IOlxThreadsService threadsService, IMessagesService messagesService,
                          IOutputService outputService,ITokenService tokenService, String name, String standartMessage) throws Exception {
        this.threadsService = threadsService;
        this.messagesService = messagesService;
        this.outputService = outputService;
        this.tokenService = tokenService;
        this.name = name;
        this.standartMessage = standartMessage;
    }

    //Refresh messages in map
    private void refreshToken() throws Exception {

        if (tokenService.getToken().getExpires_in() == 0){
            tokenService.initializeToken(tokenService.getToken().getRefresh_token());
        }
    }

    //Sends standart answers to all unread threads (users)

    private void giveStandartAnswers() throws Exception {
        try {
            refreshToken();
            String result = "";
            for (OlxThread thread : threadsService.getUnreadThreads(tokenService.getHeaders())) {
                if (result.isEmpty()) {
                    result ="\n" + name + ": \n";
                }
                if (messagesService.isSendMessage(thread.getId(),tokenService.getHeaders())) {

                    result += "Has unread message\n";
                    Sound.playSound(hasUnreadMessageSound).setVolume(0.65f);
                    continue;
                }
                messagesService.sendMessage(thread.getId(), standartMessage, tokenService.getHeaders());
                Sound.playSound(sendMessageSound).setVolume(0.8f);
                result += "Message was send to client\n";
            }
            outputService.displayInRaw(result);
        } catch (IOException e){
            throw new IOException(e.getMessage() + "\n" + name);
        } catch (Exception e) {
            throw new Exception(e.getMessage() + "\n" + name);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
                try {
                    giveStandartAnswers();
                    Thread.currentThread().sleep(10000);
                } catch (InterruptedException e) {
                   outputService.display("IOException in run\n" + e.getMessage());
                   e.printStackTrace();
                } catch (Exception e) {
                    outputService.display(e.getMessage());
                    e.printStackTrace();
                    try {
                        Thread.currentThread().sleep(10000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
        }
    }
}

//    @Override
//    public void printAllMessages() {
//        for (Map.Entry<OlxThread, List<Message>> entry : threadMessage.entrySet()){
//            outputService.display(entry.getKey() + ":");
//            outputService.display("");
//            for (Message message : entry.getValue()){
//                outputService.display(message.toString());
//            }
//            outputService.display("");
//        }
//    }
//
//    @Override
//    public void printUnreadThreadsMessages() {
//        for (OlxThread thread : threadsService.getUnreadThreads()){
//            outputService.display(thread + ":");
//            outputService.display("");
//            for (Message message : threadMessage.get(thread)){
//                outputService.display(message.toString());
//            }
//            outputService.display("");
//        }
//    }
//    @Override
//    public void printUnreadMessages() {
//        try {
//            for (OlxThread thread : threadsService.getUnreadThreads()) {
//                outputService.display(thread + ":");
//                outputService.display("");
//                for (Message message : messagesService.getUnreadMessageList(thread.getId())) {
//                    outputService.display(message.toString());
//                }
//                outputService.display("");
//            }
//        } catch (IOException e){
//            outputService.display(e.getMessage());
//        } catch (Exception e) {
//            outputService.display(e.getMessage());
//        }
//    }
