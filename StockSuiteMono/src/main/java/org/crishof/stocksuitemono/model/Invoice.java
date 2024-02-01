package org.crishof.stocksuitemono.model;

import java.time.LocalDate;
import java.util.List;

public class Invoice {

    private Long id;
    private Long invoiceNumber;
    private LocalDate issueDate;
    private LocalDate receptionDate;
    private Long SupplierId;
    private List<Long> itemsIdList;

}
