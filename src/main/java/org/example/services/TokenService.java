package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.interfaces.ITokenService;
import org.example.olx_config.AppData;
import org.example.olx_config.Token;
import org.example.olx_config.UrlAdress;
import org.example.operations.JsonParser;
import org.example.requests.HttpReq;

import javax.lang.model.type.NullType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TokenService implements ITokenService {
    private static AppData appData = new AppData();
    private Token token;
    private final String TOKEN_URL  = "https://www.olx.ua/api/open/oauth/token";

    public TokenService(String refreshToken) throws IOException {
        initializeToken(refreshToken);
    }

    private void initializeToken(String refreshToken) throws IOException {
        try {
            String response = HttpReq.postRequest(TOKEN_URL, getRefreshTokenArgs(refreshToken),
                    new HashMap<>());
            token = JsonParser.parseJson(response, new TypeReference<Token>() {});
        } catch (IOException e) {
            throw new IOException("Exception in initializeToken");
        }
    }

    @Override
    public Map<String, String> getHeaders() throws Exception {
        if(token == null)
            throw new Exception("Token = null");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token.getAccess_token());
        headers.put("Version", "2.0");
        return headers;
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
    private String getRefreshTokenArgs(String refreshToken){

        return "grant_type=refresh_token" +
                "&refresh_token=" + refreshToken +
                "&client_id=" + appData.getClient_id() +
                "&client_secret=" + appData.getClient_secret();
    }

    public static List<String> getRefreshTokens(String path) throws FileNotFoundException {
        File file = new File(path);

        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("FileNotFoundException in getRefreshTOkens");
        }

        List<String> refreshTokens = new ArrayList<>();
        while (scanner.hasNextLine()) {
            refreshTokens.add(scanner.nextLine());
        }

        return refreshTokens;
    }

}
//    private static String getTokenArgs(){
//
//        return "grant_type=authorization_code" +
//                "&scope=" + appData.getScope() +
//                "&client_id=" + appData.getClient_id() +
//                "&client_secret=" + appData.getClient_secret() +
//                "&code=" + appData.getCode();
//    }
