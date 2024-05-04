package com.amoura.payments.repository;

import com.amoura.payments.entities.Payment;
import com.amoura.payments.entities.PaymentStatus;
import com.amoura.payments.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {


    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);

 }
