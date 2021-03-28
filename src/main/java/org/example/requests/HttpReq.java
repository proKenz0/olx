package org.example.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
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

        URL url = null;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("MalformedURLException in get");
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)  url.openConnection();
        } catch (IOException e) {
            throw new IOException("IOException in openConnection int get");
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new ProtocolException("ProtocolException in setRequestMethod in get");
        }
        connection.setReadTimeout(3000);
        connection.setConnectTimeout(3000);

        //Adding headers
        for (var entry : headers.entrySet()){
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        String line = "";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();

        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new IOException("IOException in readLine in get");
            }
            response.append(line);
        }

        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new IOException("IOException in close in get");
        }

        return response.toString();
    }
    public static String postRequest(String uri, String args, Map<String, String> headers) throws IOException {

        String postData = args;

        URL url = null;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("MalformedURLException in post");
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new IOException("IOException in post connection");
        }

        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new ProtocolException("ProtocolException in post");
        }
        connection.setDoOutput(true);
        connection.setReadTimeout(3000);
        connection.setConnectTimeout(3000);

        //Adding headers
        for (var entry : headers.entrySet()){
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        //Adding post data
        OutputStream outputStream = null;
        try {
            outputStream = connection.getOutputStream();
        } catch (IOException e) {
            throw new IOException("IOException in connection.getOutputStream()");
        }
        try {
            outputStream.write(postData.getBytes());
        } catch (IOException e) {
            throw new IOException("IOException in outputStream.write()");
        }
        try {
            outputStream.flush();
        } catch (IOException e) {
            throw new IOException("IOException in outputStream.flush()");
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new IOException("IOException in outputStream.close()");
        }

        String line = "";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();

        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new IOException("IOException in bufferedReader.readLine()");
            }
            response.append(line);
        }

        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new IOException("IOException in bufferedReader.close()");
        }

        return response.toString();
    }
}
