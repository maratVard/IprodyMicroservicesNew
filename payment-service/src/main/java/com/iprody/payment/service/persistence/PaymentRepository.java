package com.iprody.payment.service.persistence;

import com.iprody.payment.service.persistence.entity.Payment;
import com.iprody.payment.service.persistence.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByStatus(PaymentStatus status);
}
