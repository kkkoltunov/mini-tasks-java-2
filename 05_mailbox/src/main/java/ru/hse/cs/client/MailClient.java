package ru.hse.cs.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ru.hse.cs.model.Mail;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class MailClient {

    private final static Logger logger = Logger.getLogger(ClientHandler.class.getName());
    private final static ObjectMapper jackson = new ObjectMapper();
    private BufferedReader in;
    private BufferedWriter out;

    public void startClient(String host, int port) throws IOException {
        Socket clientSocket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        logger.info(() -> "Started client, '" + clientSocket + "'");
    }

    public void closeClient() {
        try {
            in.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to close input stream!", e);
        }

        try {
            out.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to close output stream!", e);
        }
    }

    public List<Mail> getInbox(String username) throws IOException {
        out.write("GET " + username + "\n");
        out.flush();

        String serverResponse = in.readLine();
        logger.info(() -> "GET LETTERS: " + serverResponse);

        return jackson.readValue(serverResponse, new TypeReference<>() {
        });
    }

    public String sendMail(Mail mail) throws IOException {
        String newMail = jackson.writeValueAsString(mail);
        out.write("POST " + newMail + "\n");
        out.flush();
        logger.info(() -> "POST LETTER: " + newMail);

        return in.readLine();
    }
}
