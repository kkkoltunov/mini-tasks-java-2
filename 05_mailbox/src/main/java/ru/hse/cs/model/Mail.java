package ru.hse.cs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mail {
    private String receiverName;
    private String senderName;
    private String text;
}
