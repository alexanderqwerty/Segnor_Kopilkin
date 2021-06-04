package com.example.segnorkopilkin.ui.chart;

public class Transaction {
    private final String recipient;
    private final String sender;
    private final Long date;
    private final float sum;
    private final int id;


    public Transaction(int id, String sender, String recipient, float sum, Long date) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.sum = sum;
        this.date = date;
    }

    public Long getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public float getSum() {
        return sum;
    }
}
