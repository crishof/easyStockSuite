package com.crishof.branchsv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponse {

    private UUID id;
    private String name;
    private List<LocationResponse> locations;
}
