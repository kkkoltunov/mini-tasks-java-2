package ru.hse.cs.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hse.cs.model.Mail;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MailServer {

    private final static ObjectMapper jackson = new ObjectMapper();
    private final static List<Mail> mails = new ArrayList<>();
    private final static Logger logger = Logger.getLogger(MailServer.class.getName());
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            logger.info(() -> "Started server, '" + serverSocket + "'");
            logger.info(() -> "Press any key to stop");
            new Thread(() -> acceptClients(serverSocket)).start();
            System.in.read();
        } catch (IOException e) {
            logger.info(() -> "Server stopped");
            logger.info(e::getMessage);
        }
    }

    private static void acceptClients(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        serveClient(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                if (serverSocket.isClosed()) {
                    break;
                }
            }
        }
    }

    private static void serveClient(Socket socket) throws IOException {
        try (socket) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String requestBody = in.readLine();
                if (requestBody == null) {
                    continue;
                }
                logger.info(() -> "REQUEST: " + requestBody);

                if (requestBody.startsWith("POST")) {
                    out.write("Сервер получил письмо!\n");
                    out.flush();

                    mails.add(jackson.readValue(requestBody.substring(5), Mail.class));
                } else {
                    List<Mail> selectMails = mails.stream()
                            .filter(value -> value.getReceiverName().equals(requestBody.substring(4)))
                            .toList();
                    mails.removeAll(selectMails);

                    logger.info(() -> "SELECT MAILS: " + selectMails);
                    out.write(jackson.writeValueAsString(selectMails) + "\n");
                    out.flush();
                }
            }
        } finally {
            socket.close();
            in.close();
            out.close();
        }
    }

}