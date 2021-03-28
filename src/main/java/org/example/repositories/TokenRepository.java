package org.example.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.interfaces.repositories.ITokenRepository;
import org.example.models.Token;
import org.example.olx_config.AppData;
import org.example.operations.JsonParser;
import org.example.requests.HttpReq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TokenRepository implements ITokenRepository {
    private final String TOKEN_URL  = "https://www.olx.ua/api/open/oauth/token";

    @Override
    public Token getToken(String refreshToken) throws IOException {
        try {
            String response = HttpReq.postRequest(TOKEN_URL, getRefreshTokenArgs(refreshToken),
                    new HashMap<>());
            return JsonParser.parseJson(response, new TypeReference<Token>() {});
        } catch (IOException e) {
            throw new IOException(e.getMessage() +"\nException in TokenRepository in getToken");
        }
    }

    private String getRefreshTokenArgs(String refreshToken){

        return "grant_type=refresh_token" +
                "&refresh_token=" + refreshToken +
                "&client_id=" + AppData.CLIENT_ID +
                "&client_secret=" + AppData.CLIENT_SECRET;
    }
}
