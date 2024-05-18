package com.crishof.supplierinvoicesv.repository;

import com.crishof.supplierinvoicesv.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    List<Invoice> findAllBySupplierId(UUID supplierId);
}
