package com.amoura.payments.service;

import com.amoura.payments.entities.Payment;
import com.amoura.payments.entities.PaymentSatuts;
import com.amoura.payments.entities.PaymentType;
import com.amoura.payments.entities.Student;
import com.amoura.payments.repository.PaymentsRepository;
import com.amoura.payments.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentsRepository paymentsRepository;
    private final StudentRepository studentRepository;

    public PaymentService(PaymentsRepository paymentsRepository, StudentRepository studentRepository) {
        this.paymentsRepository = paymentsRepository;
        this.studentRepository = studentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentsRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentsRepository.findById(id).orElse(null);
    }

    public List<Payment> getPaymentsByStudent(String code) {
        return paymentsRepository.findByStudentCode(code);
    }

    public List<Payment> getPaymentsByStatus(PaymentSatuts status) {
        return paymentsRepository.findBySatuts(status);
    }

    public List<Payment> getPaymentsByType(PaymentType type) {
        return paymentsRepository.findByType(type);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByCode(String code) {
        return studentRepository.findByCode(code);
    }

    public List<Student> getStudentsByProgramId(String programId) {
        return studentRepository.findByProgramId(programId);
    }

    public Payment updatePaymentStatus(PaymentSatuts status, Long id) {
        Payment payment = paymentsRepository.findById(id).orElse(null);
        if (payment != null) {
            payment.setSatuts(status);
            return paymentsRepository.save(payment);
        }
        return null;
    }

    public Payment savePayment(MultipartFile file, LocalDate date, double amount, PaymentType type, String studentCode) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "payments");
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "payments", fileName + ".pdf");
        Files.copy(file.getInputStream(), filePath);

        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder()
                .date(date)
                .amount(amount)
                .type(type)
                .student(student)
                .satuts(PaymentSatuts.CREATED)
                .file(filePath.toUri().toString())
                .build();

        return paymentsRepository.save(payment);
    }

    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentsRepository.findById(paymentId).orElse(null);
        if (payment != null) {
            return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
        }
        return null;
    }
}
