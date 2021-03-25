package org.example;

import org.example.interfaces.IOutputService;
import org.example.services.AccountService;
import org.example.services.MessagesService;
import org.example.services.OlxThreadsService;
import org.example.services.TokenService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Starter {

    private static final String REFRESH_TOKENS_FILE_PATH = "src/main/resources/refreshTokens.txt";
    private static final String NAMES_FILE_PATH = "src/main/resources/names.txt";

    private IOutputService outputService;

    public Starter(IOutputService outputService) {
        this.outputService = outputService;
    }

    public void start(){
        try {
            List<String> refreshTokens = TokenService.getRefreshTokens(REFRESH_TOKENS_FILE_PATH);
            List<String> names = TokenService.getRefreshTokens(NAMES_FILE_PATH);
            List<Runnable> services = new ArrayList<>();
            for (int i = 0; i < refreshTokens.size(); ++i) {
                outputService.display(String.valueOf(i));
                if (names.get(i).equals("Opera")) {
                    services.add(new AccountService(new OlxThreadsService(new TokenService(refreshTokens.get(i))),
                            new MessagesService(new TokenService(refreshTokens.get(i)), true),
                            outputService, names.get(i)));
                    continue;
                }
                services.add(new AccountService(new OlxThreadsService(new TokenService(refreshTokens.get(i))),
                        new MessagesService(new TokenService(refreshTokens.get(i))),
                        outputService, names.get(i)));
            }

            for (Runnable servise : services){
                Thread thread = new Thread(servise);
                thread.start();
            }

        } catch (FileNotFoundException e) {
            outputService.display(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            outputService.display(e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            outputService.display(e.getMessage());
            e.printStackTrace();
        }
    }
}
