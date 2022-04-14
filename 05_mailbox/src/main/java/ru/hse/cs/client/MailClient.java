package ru.hse.cs.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hse.cs.model.Mail;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class MailClient {

    private final static Scanner scanner = new Scanner(System.in);
    private final static ObjectMapper jackson = new ObjectMapper();
    private final static Logger logger = Logger.getLogger(MailClient.class.getName());
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String username;

    public static void main(String[] args) {
        String HOST = readInputString("Введите IP-адрес сервера:");
        Integer PORT = readInputIntNumber("Введите порт сервера:");

        try {
            try (Socket clientSocket = new Socket(HOST, PORT)) {
                logger.info(() -> "Started client, '" + clientSocket + "'");
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                username = readInputString("Подключение успешно, введите Ваше имя:");
                System.out.println("Привет, " + username);

                String menuItem = readInputString(printMenu());
                while (!Objects.equals(menuItem, "3")) {
                    if (Objects.equals(menuItem, "1")) {
                        receiveMail();
                    } else if (Objects.equals(menuItem, "2")) {
                        sendMail(username);
                    } else {
                        System.out.println("Команда не распознана, попробуйте еще раз!");
                    }
                    menuItem = readInputString(printMenu());
                }
            } finally {
                System.out.println("До свидания!");
                if (!Objects.isNull(in)) {
                    in.close();
                }
                if (!Objects.isNull(out)) {
                    out.close();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void sendMail(String senderName) throws IOException {
        String receiverName = readInputString("Введите имя получателя:");
        String textOfLetter = readInputString("Введите текст письма:");

        String newMail = jackson.writeValueAsString(new Mail(receiverName, senderName, textOfLetter));
        out.write("POST " + newMail + "\n");
        out.flush();
        logger.info(() -> "POST LETTER: " + newMail);

        String serverResponse = in.readLine();
        System.out.println(serverResponse);
    }

    private static void receiveMail() throws IOException {
        out.write("GET " + username + "\n");
        out.flush();

        String serverResponse = in.readLine();
        logger.info(() -> "GET LETTERS: " + serverResponse);

        List<Mail> mails = jackson.readValue(serverResponse, new TypeReference<List<Mail>>() {
        });
        System.out.println("Всего было получено " + mails.size() + " писем.");
        for (Mail mail : mails) {
            System.out.println("Письмо от " + mail.getSenderName() + ":");
            System.out.println("> " + mail.getText());
        }
    }

    private static String readInputString(String message) {
        System.out.println(message);
        return scanner.next();
    }

    public static int readInputIntNumber(String message) {
        System.out.println(message);
        int input = 0;

        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                break;
            }
            scanner.next();
            System.out.println("✗✗✗ Данные введены некорректно! Попробуйте еще раз. ✗✗✗");
            System.out.println(message);
        }

        return input;
    }

    private static String printMenu() {
        return """
                1. Просмотреть входящие письма
                2. Отправить письмо
                3. Выйти""";
    }
}