package com.crishof.paymentsv.repository;

import com.crishof.paymentsv.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllBySupplierId(UUID supplierId);
}
