package com.iprody.payment.service.persistence;

import com.iprody.payment.service.persistence.entity.Payment;
import com.iprody.payment.service.persistence.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.lang.ScopedValue;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID>,
    JpaSpecificationExecutor<Payment> {
    List<Payment> findByStatus(PaymentStatus paymentStatus);

    <T> ScopedValue<T> findByGuid(UUID paymentGuid);
}
