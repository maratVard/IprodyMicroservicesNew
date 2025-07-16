package com.iprody;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
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
            if(line == null || line.isEmpty()) {
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
                writer.println("Content-Length: "+ error.length());
                writer.println();
                writer.println(error);
                writer.flush();
            }
            socket.close();
        }
    }
    private static String getContentType(String fileName) {
        String ext = "";
        int i = fileName.lastIndexOf('.');
        if (i>0) {
            ext = fileName.substring(i+1).toLowerCase();
        }
        return switch (ext) {
            case "html" -> "text/html";
            case "css" -> "text/css";
            case "js" -> "application/javascript";
            case "txt" -> "text/plain";
            default -> "application/octet-stream";
        };
    }
}
