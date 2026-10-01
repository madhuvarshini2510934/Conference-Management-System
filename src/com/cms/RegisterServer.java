package com.cms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class RegisterServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8084), 0
        );

        server.createContext(
                "/register",
                RegisterServer::handleRegister
        );

        server.start();

        System.out.println(
                "Register server started at http://localhost:8084"
        );
    }

    private static void handleRegister(
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

        String name = URLDecoder.decode(
                data[0].split("=")[1],
                StandardCharsets.UTF_8
        );

        String email = URLDecoder.decode(
                data[1].split("=")[1],
                StandardCharsets.UTF_8
        );

        String password = URLDecoder.decode(
                data[2].split("=")[1],
                StandardCharsets.UTF_8
        );

        String role = URLDecoder.decode(
                data[3].split("=")[1],
                StandardCharsets.UTF_8
        );

        try {

            RegisterUser.register(
                    name,
                    email,
                    password,
                    role
            );

            sendResponse(exchange, "success");

        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(exchange, "failed");
        }
    }

    private static void sendResponse(
            HttpExchange exchange,
            String response) throws IOException {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin",
                "*"
        );

        byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                200,
                bytes.length
        );

        OutputStream os =
                exchange.getResponseBody();

        os.write(bytes);
        os.close();
    }
}