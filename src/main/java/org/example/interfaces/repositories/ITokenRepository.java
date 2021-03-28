package org.example.interfaces.repositories;

import org.example.models.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface ITokenRepository {
    Token getToken(String refreshToken) throws IOException;

    static List<String> getRefreshTokens(String path) throws FileNotFoundException {
        File file = new File(path);

        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("FileNotFoundException in getRefreshTokens");
        }

        List<String> refreshTokens = new ArrayList<>();
        while (scanner.hasNextLine()) {
            refreshTokens.add(scanner.nextLine());
        }

        return refreshTokens;
    }
}
