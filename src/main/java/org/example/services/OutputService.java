package org.example.services;

import org.example.interfaces.service.IOutputService;

public class OutputService implements IOutputService {

    private static OutputService instance;

    private OutputService(){}

    public static OutputService getInstance(){
        if (instance == null)
            instance = new OutputService();
        return instance;
    }

    @Override
    public void display(String message) {
        System.out.println(message);
    }

    @Override
    public void displayInRaw(String message) {
        System.out.print(message);
    }
}
