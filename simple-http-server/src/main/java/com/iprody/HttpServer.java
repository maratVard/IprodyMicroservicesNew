package com.iprody;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class HttpServer {
    /**
     * Starts a simple HTTP server on port 8080
     * that serves static files from the "static" directory.
     *
     * @param args command-line arguments (not used)
     * @throws IOException if an I/O error
     * occurs when opening the socket or reading files
     */
    public static void main(final String[] args) throws IOException {
        final int serverPort = 8080;
        ServerSocket serverSocket = new ServerSocket(serverPort);
        System.out.println("Server started at http://localhost:8080");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");

            var reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(),
                            StandardCharsets.UTF_8));

            var writer = new PrintWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()));

            String line = reader.readLine();
            if (line == null || line.isEmpty()) {
                socket.close();
                continue;
            }
            System.out.println(line);

            String[]parts = line.split(" ");
            String path = parts[1];
            String fileName = path.startsWith("/") ? path.substring(1) : path;

            File file = new File("static", fileName);

            if (file.exists() && file.isFile()) {
               String contentType = getContentType(fileName);
               long contentLength = file.length();

                writer.println("HTTP/1.1 200 Ok");
                writer.println("Content-Type: " + contentType);
                writer.println("Content-Length: " + contentLength);
                writer.println();
                writer.flush();

                try (var fileInput = new FileInputStream(file)) {
                    fileInput.transferTo(socket.getOutputStream());
                }
            } else {
                String error = "404 Not Found";
                writer.println("HTTP/1.1 404 Not Found");
                writer.println("Content-Type: text/plain");
                writer.println("Content-Length: " + error.length());
                writer.println();
                writer.println(error);
                writer.flush();
            }
            socket.close();
        }
    }
    private static String getContentType(final String fileName) {
        String ext = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            ext = fileName.substring(i + 1).toLowerCase();
        }
        return switch (ext) {
            case "html" -> "text/html";
            case "css" -> "text/css";
            case "js" -> "application/javascript";
            case "txt" -> "text/plain";
            default -> "application/octet-stream";
        };
    }
    private HttpServer() {
        throw new UnsupportedOperationException("Utility class");
    }

}
