package com.cms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class PaperViewServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8082), 0
        );

        server.createContext(
                "/my-papers",
                PaperViewServer::handlePapers
        );

        server.start();

        System.out.println(
                "Paper view server started at http://localhost:8082"
        );
    }

    private static void handlePapers(HttpExchange exchange)
            throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        URI requestURI = exchange.getRequestURI();

        String query = requestURI.getQuery();

        String authorName = "";

        if (query != null && query.startsWith("authorName=")) {
            authorName = URLDecoder.decode(
                    query.substring("authorName=".length()),
                    StandardCharsets.UTF_8
            );
        }

        String response =
                ViewPapers.getPapersByAuthor(authorName);

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