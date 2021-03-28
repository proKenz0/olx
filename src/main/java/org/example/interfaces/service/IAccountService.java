package org.example.interfaces.service;

import org.example.models.OlxThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface IAccountService {
//    void printAllMessages();
//
//    void printUnreadThreadsMessages();
//
//    void printUnreadMessages();

    static List<String> getNames(String path) throws FileNotFoundException {
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
}
