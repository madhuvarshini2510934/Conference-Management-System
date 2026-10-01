package com.cms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class PaperServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081), 0
        );

        server.createContext("/submit-paper", PaperServer::handleSubmit);

        server.start();

        System.out.println(
                "Paper server started at http://localhost:8081"
        );
    }

    private static void handleSubmit(HttpExchange exchange)
            throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String body = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        String[] data = body.split("&");

        String title = data[0].split("=")[1].replace("+", " ");
        String authorName = data[1].split("=")[1].replace("+", " ");

        boolean success = SubmitPaper.submit(title, authorName);

        String response = success ? "success" : "failed";

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin", "*"
        );

        byte[] responseBytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                200,
                responseBytes.length
        );

        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}