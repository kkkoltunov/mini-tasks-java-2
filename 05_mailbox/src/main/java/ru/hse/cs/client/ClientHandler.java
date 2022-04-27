package ru.hse.cs.client;

import ru.hse.cs.model.Mail;
import ru.hse.cs.model.MailBoxException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ClientHandler {

    private Scanner scanner;
    private PrintStream printStream;
    private MailClient mailClient;
    private String username;

    public ClientHandler(Scanner scanner, PrintStream printStream, MailClient mailClient) {
        this.scanner = scanner;
        this.printStream = printStream;
        this.mailClient = mailClient;
    }

    public ClientHandler(Scanner scanner, PrintStream printStream) {
        this.scanner = scanner;
        this.printStream = printStream;
    }

    public static void main(String[] args) {
        ClientHandler clientHandler = new ClientHandler(new Scanner(System.in), new PrintStream(System.out));
        clientHandler.start();
    }

    public void start() {
        if (mailClient == null) {
            mailClient = createConnection();
        }

        final MailClient mailClient1 = mailClient;
        try (mailClient1) {
            username = readInputString("Подключение успешно, введите Ваше имя:");
            printStream.println("Привет, " + username);

            String menuItem = readInputString(printMenu());
            while (!Objects.equals(menuItem, "3")) {
                if (Objects.equals(menuItem, "1")) {
                    try {
                        getInbox(mailClient);
                    } catch (IOException e) {
                        printStream.println("При получении письма произошла ошибка: " + e.getMessage());
                    }
                } else if (Objects.equals(menuItem, "2")) {
                    try {
                        sendMail(mailClient);
                    }  catch (MailBoxException e) {
                        printStream.println(e.getMessage());
                    } catch (IOException e) {
                        printStream.println("При отправке письма произошла ошибка: " + e.getMessage());
                    }
                } else {
                    printStream.println("Команда не распознана, попробуйте еще раз!");
                }
                menuItem = readInputString(printMenu());
            }
        }

        printStream.println("До свидания!");
    }

    private MailClient createConnection() {
        MailClient mailClient = null;

        boolean flagConnection;
        do {
            flagConnection = false;
            try {
                String host = readInputString("Введите IP-адрес сервера:");
                int port = readInputIntNumber("Введите порт сервера:");
                mailClient = new MailClient(host, port);
            } catch (IOException e) {
                printStream.println("Не удалось подключиться к серверу, проверьте корректность хоста и порта!");
                flagConnection = true;
            }
        } while (flagConnection);

        return mailClient;
    }

    private void sendMail(MailClient mailClient) throws IOException, MailBoxException {
        String receiverName = readInputString("Введите имя получателя:");
        String textOfLetter = readInputString("Введите текст письма:");

        mailClient.sendMail(new Mail(receiverName, username, textOfLetter));
        printStream.println("Сервер получил письмо!");
    }

    private void getInbox(MailClient mailClient) throws IOException {
        List<Mail> mails = mailClient.getInbox(username);

        printStream.println("Всего было получено " + mails.size() + " писем.");
        for (Mail mail : mails) {
            printStream.println("Письмо от " + mail.senderName() + ":");
            printStream.println("> " + mail.text());
        }
    }

    private String readInputString(String message) {
        printStream.println(message);
        return scanner.next();
    }

    public int readInputIntNumber(String message) {
        printStream.println(message);
        int input = 0;

        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                break;
            }
            scanner.next();
            printStream.println("✗✗✗ Данные введены некорректно! Попробуйте еще раз. ✗✗✗");
            printStream.println(message);
        }

        return input;
    }

    private String printMenu() {
        return """
                1. Просмотреть входящие письма
                2. Отправить письмо
                3. Выйти""";
    }
}