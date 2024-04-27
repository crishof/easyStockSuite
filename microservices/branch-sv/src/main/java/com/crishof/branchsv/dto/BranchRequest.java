package com.crishof.branchsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchRequest {

    private String name;
    private List<LocationRequest> locations;
}
