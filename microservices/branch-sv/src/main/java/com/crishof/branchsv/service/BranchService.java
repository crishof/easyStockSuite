package com.crishof.branchsv.service;

import com.crishof.branchsv.dto.BranchRequest;
import com.crishof.branchsv.dto.BranchResponse;

import java.util.UUID;

public interface BranchService {

    BranchResponse createBranch(BranchRequest branchRequest);

    BranchResponse updateBranchName(UUID branchId, BranchRequest branchRequest);

    BranchResponse createLocation(UUID branchId, String name);

    BranchResponse updateLocation(UUID branchId, UUID locationId, String name);

    BranchResponse deleteLocation(UUID branchId, UUID locationId);
}
