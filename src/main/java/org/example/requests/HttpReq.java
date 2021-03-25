package org.example.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class HttpReq {


//    public static String getRequest(String uri, Map<String, String> headers) throws Exception {
//        List<String> listHeaders = new ArrayList<>();
//        for (var entry : headers.entrySet()) {
//            listHeaders.add(entry.getKey());
//            listHeaders.add(entry.getValue());
//        }
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(uri))
//                .header(listHeaders.get(0), listHeaders.get(1))
//                .header(listHeaders.get(2), listHeaders.get(3))
//                .build();
//
//        return client.sendAsync(request, BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .join();
//    }
//
//    public static String postRequest(String uri, String args, Map<String, String> headers){
//        List<String> listHeaders = new ArrayList<>();
//        for (var entry : headers.entrySet()) {
//            listHeaders.add(entry.getKey());
//            listHeaders.add(entry.getValue());
//        }
//        HttpRequest request;
//        if (listHeaders.size() > 0) {
//            request = HttpRequest.newBuilder()
//                    .uri(URI.create(uri))
//                    .header(listHeaders.get(0), listHeaders.get(1))
//                    .header(listHeaders.get(2), listHeaders.get(3))
//                    .POST(BodyPublishers.ofString(args))
//                    .build();
//        }
//        else {
//            request = HttpRequest.newBuilder()
//                    .uri(URI.create(uri))
//                    .POST(BodyPublishers.ofString(args))
//                    .build();
//        }
//
//        return HttpClient.newHttpClient()
//                .sendAsync(request, BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .join();
//    }
    public static String getRequest(String uri, Map<String, String> headers) throws IOException {

        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(3000);
        connection.setConnectTimeout(3000);

        //Adding headers
        for (var entry : headers.entrySet()){
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        String line = "";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }

        bufferedReader.close();

        return response.toString();
    }
    public static String postRequest(String uri, String args, Map<String, String> headers) throws IOException {

        String postData = args;

        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setReadTimeout(3000);
        connection.setConnectTimeout(3000);

        //Adding headers
        for (var entry : headers.entrySet()){
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        //Adding post data
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(postData.getBytes());
        outputStream.flush();
        outputStream.close();

        String line = "";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }

        bufferedReader.close();

        return response.toString();
    }
}
