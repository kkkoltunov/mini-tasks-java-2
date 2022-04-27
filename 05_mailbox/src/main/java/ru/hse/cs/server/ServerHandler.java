package ru.hse.cs.server;

import java.io.IOException;

public class ServerHandler {

    private final static int PORT = 8888;

    public static void main(String[] args) throws IOException {
        try (MailServer mailServer = new MailServer(PORT)) {
            mailServer.startAcceptClients();
            System.in.read();
        } catch (IOException e) {
            System.out.println("Не удалось развернуть сервер на порте: " + PORT);
            return;
        }
    }
}