package com.cms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class LoginServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        server.createContext(
                "/login",
                LoginServer::handleLogin
        );

        server.start();

        System.out.println(
                "Login server started at http://localhost:8080"
        );
    }

    private static void handleLogin(
            HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod()
                .equalsIgnoreCase("POST")) {

            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        String[] data = body.split("&");

        String email = URLDecoder.decode(
                data[0].split("=")[1],
                StandardCharsets.UTF_8
        );

        String password = URLDecoder.decode(
                data[1].split("=")[1],
                StandardCharsets.UTF_8
        );

        String result =
                LoginUser.login(email, password);

        sendResponse(exchange, result);
    }

    private static void sendResponse(
            HttpExchange exchange,
            String response) throws IOException {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin",
                "*"
        );

        byte[] responseBytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                200,
                responseBytes.length
        );

        OutputStream os =
                exchange.getResponseBody();

        os.write(responseBytes);
        os.close();
    }
}