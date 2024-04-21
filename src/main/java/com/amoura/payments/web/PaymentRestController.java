package com.amoura.payments.web;

import com.amoura.payments.entities.Payment;
import com.amoura.payments.entities.PaymentSatuts;
import com.amoura.payments.entities.PaymentType;
import com.amoura.payments.entities.Student;
import com.amoura.payments.service.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PaymentRestController {

    private final PaymentService paymentService;

    public PaymentRestController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping(path = "/student/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code) {
        return paymentService.getPaymentsByStudent(code);
    }

    @GetMapping(path = "/payments/byStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentSatuts status) {
        return paymentService.getPaymentsByStatus(status);
    }

    @GetMapping(path = "/payments/byType")
    public List<Payment> paymentsByType(@RequestParam PaymentType type) {
        return paymentService.getPaymentsByType(type);
    }

    @GetMapping(path = "/students")
    public List<Student> allStudents() {
        return paymentService.getAllStudents();
    }

    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return paymentService.getStudentByCode(code);
    }

    @GetMapping(path = "/studentsByProgramId/")
    public List<Student> getStudentsByProgramId(@RequestParam String programId) {
        return paymentService.getStudentsByProgramId(programId);
    }

    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentSatuts status, @PathVariable Long id) {
        return paymentService.updatePaymentStatus(status, id);
    }

    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date, double amount, PaymentType type, String studentCode) throws IOException {
        return paymentService.savePayment(file, date, amount, type, studentCode);
    }

    @GetMapping(path = "paymentFile/{paymentId}", produces = {MediaType.APPLICATION_PDF_VALUE})
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        return paymentService.getPaymentFile(paymentId);
    }
}
