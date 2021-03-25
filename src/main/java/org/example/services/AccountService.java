package org.example.services;

import org.example.interfaces.*;
import org.example.models.Message;
import org.example.models.OlxThread;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.*;

public class AccountService implements IAccountService, Runnable {

    private IOlxThreadsService threadsService;
    private IMessagesService messagesService;
    private IOutputService outputService;
    private String name;

    private Map<OlxThread, List<Message>> threadMessage = new HashMap<>();

    public AccountService(IOlxThreadsService threadsService, IMessagesService messagesService,
                          IOutputService outputService, String name) throws Exception {
        this.threadsService = threadsService;
        this.messagesService = messagesService;
        this.outputService = outputService;
        this.name = name;

//        refresh();
    }

    public AccountService(IOlxThreadsService threadsService, IMessagesService messagesService,
                          String name) throws Exception {
        this.threadsService = threadsService;
        this.messagesService = messagesService;
        this.name = name;
        this.outputService = new IOutputService() {
            @Override
            public void display(String message) {
                System.out.println(message);
            }
        };
//        refresh();
    }

    //Refresh messages in map
    private void refresh() throws Exception {
        for (OlxThread thread : threadsService.getThreadList()){
            threadMessage.put(thread, messagesService.getMessageList(thread.getId()));
        }
    }

    @Override
    public void printAllMessages() {
        for (Map.Entry<OlxThread, List<Message>> entry : threadMessage.entrySet()){
            outputService.display(entry.getKey() + ":");
            outputService.display("");
            for (Message message : entry.getValue()){
                outputService.display(message.toString());
            }
            outputService.display("");
        }
    }

    @Override
    public void printUnreadThreadsMessages() {
        for (OlxThread thread : threadsService.getUnreadThreads()){
            outputService.display(thread + ":");
            outputService.display("");
            for (Message message : threadMessage.get(thread)){
                outputService.display(message.toString());
            }
            outputService.display("");
        }
    }
    @Override
    public void printUnreadMessages() {
        try {
            for (OlxThread thread : threadsService.getUnreadThreads()) {
                outputService.display(thread + ":");
                outputService.display("");
                for (Message message : messagesService.getUnreadMessageList(thread.getId())) {
                    outputService.display(message.toString());
                }
                outputService.display("");
            }
        } catch (IOException e){
            outputService.display(e.getMessage());
        } catch (Exception e) {
            outputService.display(e.getMessage());
        }
    }

    public static List<String> getNames(String path) throws FileNotFoundException {
        File file = new File(path);

        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("FileNotFoundException in getNames");
        }

        List<String> names = new ArrayList<>();
        while (scanner.hasNextLine()) {
            names.add(scanner.nextLine());
        }

        return names;
    }

    //Sends standart answers to all unread threads (users)
    @Override
    public void giveStandartAnswers() {
        try {
            refresh();
            String result = name + ": \n";
            for (OlxThread thread : threadsService.getUnreadThreads()) {
                if (messagesService.isSendMessage(thread.getId())) {

                    result += "Has send messages\n";
//                    outputService.display("Has send messages");
                    continue;
                }
                messagesService.sendStandartMessage(thread.getId());
                result += "Message was send\n";
//                outputService.display("Message was send");
            }
            outputService.display(result);
        } catch (IOException e){
            outputService.display(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            outputService.display(e.getMessage());
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
                    outputService.display("");
                    giveStandartAnswers();
                    outputService.display("");
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("IOException in run");
                }
            }
    }
}
