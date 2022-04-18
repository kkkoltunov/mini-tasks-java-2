package ru.hse.cs.model;

import java.util.Objects;

public record Mail(String receiverName, String senderName, String text) {
    public Mail {
        Objects.requireNonNull(receiverName, "Имя получателя не должно быть null!");
        Objects.requireNonNull(senderName, "Имя отправителя не должно быть null!");
        Objects.requireNonNull(text, "Сообщение не должно быть null!");

        if (receiverName.isBlank()) {
            throw new NullPointerException("Имя получателя не должно быть пустым!");
        }

        if (senderName.isBlank()) {
            throw new NullPointerException("Имя отправителя не должно быть пустым!");
        }

        if (text.isBlank()) {
            throw new NullPointerException("Сообщение не должно быть пустым!");
        }
    }
}
