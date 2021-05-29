package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.interfaces.repositories.ITokenRepository;
import org.example.interfaces.service.ITokenService;
import org.example.models.Token;
import org.example.operations.JsonParser;
import org.example.requests.HttpReq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TokenService implements ITokenService {

    private Token token;
    private ITokenRepository tokenRepository;

    public TokenService(ITokenRepository tokenRepository, String refreshToken) throws IOException {
        this.tokenRepository = tokenRepository;
        initializeToken(refreshToken);
        System.out.println(token);
    }

    @Override
    public void initializeToken(String refreshToken) throws IOException {
        try {
            token = tokenRepository.getToken(refreshToken);
        } catch (IOException e) {
            throw new IOException(e.getMessage() + "\nException in TokenService in initializeToken");
        }
    }

    @Override
    public Map<String, String> getHeaders() throws Exception {
        if (token == null)
            throw new Exception("Token = null");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token.getAccess_token());
        headers.put("Version", "2.0");
        return headers;
    }

    @Override
    public Token getToken() {
        return token;
    }

}
//    private String getRefreshTokenArgs(String refreshToken) throws JsonProcessingException {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Map<String, String> args = new HashMap<>();
//        args.put("grant_type", "refresh_token");
//        args.put("refresh_token", refreshToken);
//        args.put("client_id", appData.getClient_id());
//        args.put("client_secret", appData.getClient_secret());
//
//        String str = mapper.writeValueAsString(args);
//        System.out.println(str);
//        return str;
//    }

//    private static String getTokenArgs(){
//
//        return "grant_type=authorization_code" +
//                "&scope=" + appData.getScope() +
//                "&client_id=" + appData.getClient_id() +
//                "&client_secret=" + appData.getClient_secret() +
//                "&code=" + appData.getCode();
//    }
