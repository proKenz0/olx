package org.example.services;

import org.example.interfaces.service.IOutputService;

public class OutputService implements IOutputService {
    @Override
    public void display(String message) {
        System.out.println(message);
    }

    @Override
    public void displayInRaw(String message) {
        System.out.print(message);
    }
}
