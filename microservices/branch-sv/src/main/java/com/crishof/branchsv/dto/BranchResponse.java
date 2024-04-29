package com.crishof.branchsv.dto;

import com.crishof.branchsv.model.StockLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponse {

    private UUID id;
    private String name;
    private List<LocationResponse> locations;
}
