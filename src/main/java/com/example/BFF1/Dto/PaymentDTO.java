package com.example.BFF1.Dto;

import java.time.LocalDateTime;

public class PaymentDTO {

    public String id;
    public String userId;
    public String ticketId;
    public double amount;
    public LocalDateTime paymentDate;
    public PaymentType paymentType;
    public enum PaymentType{
        CARD,
        BANK_TRANSFER,
        CRYPTO
    }

}
