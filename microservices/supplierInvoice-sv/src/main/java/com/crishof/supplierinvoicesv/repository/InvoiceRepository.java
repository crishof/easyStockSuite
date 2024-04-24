package com.crishof.supplierinvoicesv.repository;

import com.crishof.supplierinvoicesv.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
