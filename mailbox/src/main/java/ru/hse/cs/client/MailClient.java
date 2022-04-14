package ru.hse.cs.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class MailClient {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Введите IP-адрес и порт сервера:");
        String HOST = scanner.next();
        Integer PORT = scanner.nextInt();

        try (Socket socket = new Socket(HOST, PORT);
             InputStream inputStream = socket.getInputStream();
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            System.out.println("Подключение успешно, введите Ваше имя:");
            String userName = scanner.next();
            System.out.println("Привет, " + userName);

            printMenu();
            String menuItem = scanner.next();
            while (!Objects.equals(menuItem, "3")) {
                if (Objects.equals(menuItem, "1")) {

                } else if (Objects.equals(menuItem, "2")) {

                } else {
                    System.out.println("Команда не распознана, попробуйте еще раз!");
                }
                printMenu();
                menuItem = scanner.next();
            }
            System.out.println("До свидания!");

            bufferedWriter.write("abc");
            bufferedWriter.flush();

            int read;
            while ((read = inputStream.read()) != -1) {
                System.out.print((char) read);
            }

        }
    }

    private static void sendMail(BufferedWriter bufferedWriter) {
        System.out.println("Введите имя получателя:");
        String nameRecipient = scanner.next();

        System.out.println("Введите текст письма:");
        String textOfLetter = scanner.next();
    }

    public static void printMenu() {
        System.out.println("""
                1. Просмотреть входящие письма
                2. Отправить письмо
                3. Выйти
                """);
    }
}
