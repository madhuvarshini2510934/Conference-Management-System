package com.cms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ReviewerServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8083), 0
        );

        server.createContext(
                "/reviewer-papers",
                ReviewerServer::handlePapers
        );

        server.createContext(
                "/review-paper",
                ReviewerServer::handleReview
        );

        server.start();

        System.out.println(
                "Reviewer server started at http://localhost:8083"
        );
    }

    private static void handlePapers(HttpExchange exchange)
            throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String response = getAllPapers();

        sendResponse(exchange, response);
    }

    private static String getAllPapers() {

        StringBuilder result = new StringBuilder();

        String sql = "SELECT paper_id, title, author_name, status, "
                   + "reviewer_name FROM papers";

        try {

            var con = DBConnection.getConnection();
            var ps = con.prepareStatement(sql);
            var rs = ps.executeQuery();

            while (rs.next()) {

                result.append(rs.getInt("paper_id"))
                      .append("|")
                      .append(rs.getString("title"))
                      .append("|")
                      .append(rs.getString("author_name"))
                      .append("|")
                      .append(rs.getString("status"))
                      .append("|")
                      .append(rs.getString("reviewer_name"))
                      .append("\n");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private static void handleReview(HttpExchange exchange)
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

        String paperId = URLDecoder.decode(
                data[0].split("=")[1],
                StandardCharsets.UTF_8
        );

        String reviewerName = URLDecoder.decode(
        data[1].split("=")[1],
        StandardCharsets.UTF_8
);

String comments = URLDecoder.decode(
        data[2].split("=")[1],
        StandardCharsets.UTF_8
);

String recommendation = URLDecoder.decode(
        data[3].split("=")[1],
        StandardCharsets.UTF_8
);

boolean success = ReviewPaper.review(
        Integer.parseInt(paperId),
        reviewerName,
        comments,
        recommendation
);

        String response = success ? "success" : "failed";

        sendResponse(exchange, response);
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

        OutputStream os = exchange.getResponseBody();

        os.write(responseBytes);

        os.close();
    }
}