package org.example.xpaymentadapterapp.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChargeRepository extends JpaRepository<com.iprody.xpaymentapi.Charge, UUID> {
    List<Charge> findByStatus(Charge.Status status);
}