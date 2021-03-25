package org.example;


import org.example.olx_config.UrlAdress;
import org.example.requests.HttpReq;
import org.example.services.AccountService;
import org.example.interfaces.IAccountService;
import org.example.services.MessagesService;
import org.example.services.OlxThreadsService;
import org.example.services.TokenService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App
{

    public static final String REFRESH_TOKENS_FILE_PATH = "src/main/resources/refreshTokens.txt";
    public static final String NAMES_FILE_PATH = "src/main/resources/names.txt";

    public static void main( String[] args ) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
//            List<String> refreshTokens = TokenService.getRefreshTokens(REFRESH_TOKENS_FILE_PATH);
//            List<String> names = TokenService.getRefreshTokens(NAMES_FILE_PATH);
//            List<Runnable> services = new ArrayList<>();
//
//            services.add(new AccountService(new OlxThreadsService(new TokenService(refreshTokens.get(9))),
//                        new MessagesService(new TokenService(refreshTokens.get(9))),names.get(9)));
//                System.out.println(9);
//
//            for (Runnable servise : services){
//                Thread thread = new Thread(servise);
//                thread.start();
//            }
            List<String> refreshTokens = TokenService.getRefreshTokens(REFRESH_TOKENS_FILE_PATH);
            List<String> names = TokenService.getRefreshTokens(NAMES_FILE_PATH);
            List<Runnable> services = new ArrayList<>();
            for (int i = 0; i < refreshTokens.size(); ++i) {

                services.add(new AccountService(new OlxThreadsService(new TokenService(refreshTokens.get(i))),
                        new MessagesService(new TokenService(refreshTokens.get(0))),names.get(i)));
                System.out.println(i);
            }

            for (Runnable servise : services){
                Thread thread = new Thread(servise);
                thread.start();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("buy");
    }
}
