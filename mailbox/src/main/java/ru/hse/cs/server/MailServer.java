package ru.hse.cs.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailServer {

    private static final Logger logger = Logger.getLogger(MailServer.class.getName());

    private static final int DEFAULT_PORT = 8888;

    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            logger.info(() -> "Started server, '" + serverSocket + "'");
            logger.info(() -> "Press any key to stop");
            new Thread(() -> acceptClients(serverSocket)).start();
            System.in.read();
        }
        // logger.info(() -> "Server stopped");
    }

    private static void acceptClients(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> serveClient(socket)).start();
            } catch (IOException e) {
                if (serverSocket.isClosed()) {
                    break;
                }
                logger.log(Level.WARNING, e, () -> "Cannot serve client");
            }
        }
    }

    private static void serveClient(Socket socket) {
        try (socket;
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            int read;

            while ((read = inputStream.read()) != -1) {
                outputStream.write(read);
                outputStream.flush();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
