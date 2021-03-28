package org.example.interfaces.service;

import org.example.models.Token;

import java.io.IOException;
import java.util.Map;

public interface ITokenService {
    public Map<String, String> getHeaders() throws Exception;
    public Token getToken();
    public void initializeToken(String refreshToken) throws IOException;
}
