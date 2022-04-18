package ru.hse.cs.client;

import ru.hse.cs.model.Mail;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ClientHandler {

    private final static Scanner scanner = new Scanner(System.in);
    private static String username;

    public static void main(String[] args) {
        MailClient mailClient = new MailClient();

        boolean flagConnection;
        do {
            flagConnection = false;
            try {
                String host = readInputString("Введите IP-адрес сервера:");
                int port = readInputIntNumber("Введите порт сервера:");
                mailClient.startClient(host, port);
            } catch (IOException e) {
                System.err.println("Не удалось подключиться к серверу, проверьте корректность хоста и порта!");
                flagConnection = true;
            }
        } while (flagConnection);

        username = readInputString("Подключение успешно, введите Ваше имя:");
        System.out.println("Привет, " + username);

        String menuItem = readInputString(printMenu());
        while (!Objects.equals(menuItem, "3")) {
            if (Objects.equals(menuItem, "1")) {
                try {
                    getInbox(mailClient);
                } catch (IOException e) {
                    System.err.println("При получении письма произошла ошибка!");
                }
            } else if (Objects.equals(menuItem, "2")) {
                try {
                    sendMail(mailClient);
                } catch (IOException e) {
                    System.err.println("При отправке письма произошла ошибка!");
                }
            } else {
                System.out.println("Команда не распознана, попробуйте еще раз!");
            }
            menuItem = readInputString(printMenu());
        }

        mailClient.closeClient();
        System.out.println("До свидания!");
    }

    private static void sendMail(MailClient mailClient) throws IOException {
        String receiverName = readInputString("Введите имя получателя:");
        String textOfLetter = readInputString("Введите текст письма:");

        String serverResponse = mailClient.sendMail(new Mail(receiverName, username, textOfLetter));
        System.out.println(serverResponse);
    }

    private static void getInbox(MailClient mailClient) throws IOException {
        List<Mail> mails = mailClient.getInbox(username);

        System.out.println("Всего было получено " + mails.size() + " писем.");
        for (Mail mail : mails) {
            System.out.println("Письмо от " + mail.senderName() + ":");
            System.out.println("> " + mail.text());
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