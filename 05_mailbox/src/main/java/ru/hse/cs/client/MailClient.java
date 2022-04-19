package ru.hse.cs.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hse.cs.model.Mail;
import ru.hse.cs.model.MailBoxException;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailClient implements Closeable {

    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    private final ObjectMapper jackson = new ObjectMapper();
    private final BufferedReader in;
    private final BufferedWriter out;
    private final Socket clientSocket;

    private static final String CORRECT_SERVER_RESPONSE = "Сервер получил письмо!";

    public MailClient(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        logger.info(() -> "Started client, '" + clientSocket + "'");
    }

    @Override
    public void close() {
        try (clientSocket; in; out) {
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error when closing resources!", e);
        }
    }

    public List<Mail> getInbox(String username) throws IOException {
        Objects.requireNonNull(username, "Имя не должно быть null!");
        if (username.isBlank()) {
            throw new IllegalArgumentException("Имя не должно быть пустым!");
        }

        writeRequest("GET " + username);
        String serverResponse = in.readLine();
        logger.info(() -> "GET LETTERS: " + serverResponse);

        return jackson.readValue(serverResponse, new TypeReference<>() {
        });
    }

    public void sendMail(Mail mail) throws IOException, MailBoxException {
        Objects.requireNonNull(mail, "Письмо не должно быть null!");

        String newMail = jackson.writeValueAsString(mail);
        writeRequest("POST " + newMail);
        logger.info(() -> "POST LETTER: " + newMail);

        String serverResponse = in.readLine();
        if (!CORRECT_SERVER_RESPONSE.equals(serverResponse)) {
            throw new MailBoxException("Сообщение от сервера: " + serverResponse);
        }
    }

    private void writeRequest(String request) throws IOException {
        out.write(request + "\n");
        out.flush();
    }
}
