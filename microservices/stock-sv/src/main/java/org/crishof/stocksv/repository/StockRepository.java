package org.crishof.stocksv.repository;

import org.crishof.stocksv.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    Stock findByProductIdAndBranchIdAndLocationId(UUID productId, UUID branchId, UUID locationId);
}
