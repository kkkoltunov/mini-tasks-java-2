package ru.hse.cs.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hse.cs.model.Mail;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailServer implements Closeable {
    private final ObjectMapper jackson;
    private final List<Mail> mails;
    private final ServerSocket serverSocket;

    private static final Logger logger = Logger.getLogger(MailServer.class.getName());
    private static final String POST_PREFIX = "POST";
    private static final String GET_PREFIX = "GET";
    private static final int MESSAGE_BODY_START_POST = POST_PREFIX.length() + 1;
    private static final int MESSAGE_BODY_START_GET = GET_PREFIX.length() + 1;

    public MailServer(int port) throws IOException {
        jackson = new ObjectMapper();
        serverSocket = new ServerSocket(port);
        mails = Collections.synchronizedList(new ArrayList<>());
        logger.info(() -> "Started server, '" + serverSocket + "'");
        logger.info(() -> "Press any key to stop");
    }

    @Override
    public void close() {
        try (serverSocket) {

        } catch (IOException e) {
            logger.log(Level.WARNING, "Error when closing resources!", e);
        }
    }

    public void startAcceptClients() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> serveClient(socket)).start();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Server error!", e);
                    if (serverSocket.isClosed()) {
                        break;
                    }
                }
            }
        }).start();
    }

    private void serveClient(Socket socket) {
        try (socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            while (true) {
                String requestBody = in.readLine();
                if (requestBody == null) {
                    continue;
                }
                logger.info(() -> "REQUEST: " + requestBody);

                if (requestBody.startsWith(POST_PREFIX)) {
                    mails.add(jackson.readValue(requestBody.substring(MESSAGE_BODY_START_POST), Mail.class));
                    writeResponse("Сервер получил письмо!", out);
                } else if (requestBody.startsWith(GET_PREFIX)) {
                    List<Mail> selectMails = mails.stream()
                            .filter(value -> value.receiverName().equals(requestBody.substring(MESSAGE_BODY_START_GET)))
                            .toList();
                    mails.removeAll(selectMails);
                    logger.info(() -> "SELECT MAILS: " + selectMails);

                    writeResponse(jackson.writeValueAsString(selectMails), out);
                } else {
                    logger.log(Level.WARNING, "Bad request!");
                    close();
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while serve client!", e);
        }
    }

    private void writeResponse(String response, BufferedWriter out) throws IOException {
        out.write(response + "\n");
        out.flush();
    }
}
