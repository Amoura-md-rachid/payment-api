package com.amoura.payments.dtos;

import com.amoura.payments.entities.PaymentSatuts;
import com.amoura.payments.entities.PaymentType;


import java.time.LocalDate;

public class PaymentDTO {


    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentSatuts satuts;


}
