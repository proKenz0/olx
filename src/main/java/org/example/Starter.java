package org.example;

import org.example.interfaces.repositories.ITokenRepository;
import org.example.interfaces.service.IAccountService;
import org.example.interfaces.service.IOutputService;
import org.example.repositories.MessageRepository;
import org.example.repositories.OlxThreadsRepository;
import org.example.repositories.TokenRepository;
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
    private final String STANDART_MESSAGE = "text=Доброго дня.\n" +
            "Є в наявності.\n" +
            "Ціна - 90 грн.\n" +
            "Скло 5д чорне/біле - 70 грн.\n" +
            "Самовивозу немає.\n" +
            "Доставка новою поштою або олх.\n" +
            "Залишіть номер вайберу, пришлемо фото чохлів на вашу модель.";
    private final String LVIV_STANDART_MESSAGE = "text=Доброго дня.\n" +
            "Є в наявності.\n" +
            "Ціна - 90 грн.\n" +
            "Скло 5д чорне/біле - 70 грн.\n" +
            "Залишіть номер вайберу, пришлемо фото чохлів на вашу модель.";

    private IOutputService outputService;

    public Starter(IOutputService outputService) {
        this.outputService = outputService;
    }

    public void start(){
        try {
            List<String> refreshTokens = ITokenRepository.getRefreshTokens(REFRESH_TOKENS_FILE_PATH);
            List<String> names = IAccountService.getNames(NAMES_FILE_PATH);
            List<Runnable> services = new ArrayList<>();

            for (int i = 0; i < refreshTokens.size(); ++i) {
                outputService.display(String.valueOf(i));

                if (names.get(i).equals("Opera")) {
                    services.add(new AccountService(new OlxThreadsService(new OlxThreadsRepository()),
                            new MessagesService(new MessageRepository()), outputService,
                            new TokenService(new TokenRepository(),
                            refreshTokens.get(i)), names.get(i), LVIV_STANDART_MESSAGE));
                    continue;
                }

                services.add(new AccountService(new OlxThreadsService(new OlxThreadsRepository()),
                        new MessagesService(new MessageRepository()), outputService,
                        new TokenService(new TokenRepository(),
                                refreshTokens.get(i)), names.get(i), STANDART_MESSAGE));
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
