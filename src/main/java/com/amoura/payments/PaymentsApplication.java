package com.amoura.payments;

import com.amoura.payments.entities.Payment;
import com.amoura.payments.entities.PaymentStatus;
import com.amoura.payments.entities.PaymentType;
import com.amoura.payments.entities.Student;
import com.amoura.payments.repository.PaymentsRepository;
import com.amoura.payments.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class PaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsApplication.class, args);
	}
    @Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentsRepository paymentsRepository){
		return args -> {
			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("karim")
					.code("1122")
					.programId("SDIA")
					.build());
			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("samir")
					.code("2233")
					.programId("GLSID")
					.build());
			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("mohamde")
					.code("4455")
					.programId("BDCC")
					.build());
			studentRepository.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("najat")
					.code("6677")
					.programId("BDCC")
					.build());

		PaymentType[] paymentTypes = PaymentType.values();
		PaymentStatus[] paymentStatuses = PaymentStatus.values();
		Random random = new Random();
		int minDay = (int) LocalDate.of(2020, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(2025, 12, 31).toEpochDay();
		long randomDay = minDay + random.nextInt(maxDay - minDay);
			studentRepository.findAll().forEach(s -> {
				for (int i =0; i<10; i++) {
				int index = random.nextInt(paymentTypes.length);
				int index2 = random.nextInt(paymentStatuses.length);
					LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
					Payment payment = Payment.builder()
							.amount(1000+(int)(Math.random()*20000))
						    .type(paymentTypes[index])
							.status(paymentStatuses[index2])
							.student(s)
							.date(randomDate)
							.build();
					paymentsRepository.save(payment);
				}
			});



		};



	}

}
