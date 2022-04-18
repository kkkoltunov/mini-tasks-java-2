package ru.hse.cs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailServer {

    private final static Logger logger = Logger.getLogger(MailServer.class.getName());

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            logger.info(() -> "Started server, '" + serverSocket + "'");
            logger.info(() -> "Press any key to stop");
            new Thread(() -> acceptClients(serverSocket)).start();
            System.in.read();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Server stopped!", e);
        }
    }

    private static void acceptClients(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        new ClientServe(logger).serveClient(socket);
                    } catch (IOException e) {
                        logger.log(Level.WARNING, "Error while serve client!", e);
                    }
                }).start();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Server error!", e);
                if (serverSocket.isClosed()) {
                    break;
                }
            }
        }
    }
}