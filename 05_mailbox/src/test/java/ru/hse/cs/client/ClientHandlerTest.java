package ru.hse.cs.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hse.cs.model.Mail;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class ClientHandlerTest {
    private MailClient mailClient;

    @BeforeEach
    void setUp() {
        mailClient = Mockito.mock(MailClient.class);
    }

    @Test
    void getEmptyInbox() throws IOException {
        given(mailClient.getInbox("abc"))
                .willReturn(List.of());

        ClientHandler clientHandler = new ClientHandler(
                new Scanner("abc\n1\n3"),
                new PrintStream(System.out),
                mailClient);
        clientHandler.start();

        then(mailClient).should().getInbox("abc");
    }

    @Test
    void getInbox() throws IOException {
        given(mailClient.getInbox("abc"))
                .willReturn(List.of(
                        new Mail("abc", "def", "some text"),
                        new Mail("abc", "sdf", "yet some text")
                ));

        ClientHandler clientHandler = new ClientHandler(
                new Scanner("abc\n1\n3"),
                new PrintStream(System.out),
                mailClient);
        clientHandler.start();

        then(mailClient).should().getInbox("abc");
    }

    @Test
    void sendMail() throws IOException {
        ClientHandler clientHandler = new ClientHandler(
                new Scanner("abc\n2\ndef\ntext\n3"),
                new PrintStream(System.out),
                mailClient);
        clientHandler.start();

        then(mailClient).should().sendMail(new Mail("def", "abc", "text"));
    }
}