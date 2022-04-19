package ru.hse.cs.server;

import java.io.*;

public class ServerHandler {

    private final static int PORT = 8888;

    public static void main(String[] args) throws IOException {
        MailServer mailServer;
        try {
            mailServer = new MailServer(PORT);
        } catch (IOException e) {
            System.out.println("Не удалось развернуть сервер на порте: " + PORT);
            return;
        }
        mailServer.startAcceptClients();

        System.in.read();
        mailServer.close();
    }
}