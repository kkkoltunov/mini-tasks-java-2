package ru.hse.cs.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hse.cs.model.Mail;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClientServe {
    private final ObjectMapper jackson;
    private final List<Mail> mails;
    private final Logger logger;

    public ClientServe(Logger logger) {
        jackson = new ObjectMapper();
        mails = new ArrayList<>();
        this.logger = logger;
    }

    public void serveClient(Socket socket) throws IOException {
        try (socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {

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
                            .filter(value -> value.receiverName().equals(requestBody.substring(4)))
                            .toList();
                    mails.removeAll(selectMails);

                    logger.info(() -> "SELECT MAILS: " + selectMails);
                    out.write(jackson.writeValueAsString(selectMails) + "\n");
                    out.flush();
                }
            }
        }
    }
}
