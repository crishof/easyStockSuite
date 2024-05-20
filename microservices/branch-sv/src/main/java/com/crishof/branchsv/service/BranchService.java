package com.crishof.branchsv.service;

import com.crishof.branchsv.dto.BranchRequest;
import com.crishof.branchsv.dto.BranchResponse;
import com.crishof.branchsv.exception.BranchNotFoundException;
import com.crishof.branchsv.model.Branch;

import java.util.List;
import java.util.UUID;

public interface BranchService {

    BranchResponse createBranch(BranchRequest branchRequest);

    BranchResponse updateBranchName(UUID branchId, BranchRequest branchRequest) throws BranchNotFoundException;

    BranchResponse createLocation(UUID branchId, String name);

    BranchResponse updateLocation(UUID branchId, UUID locationId, String name) throws BranchNotFoundException;

    BranchResponse deleteLocation(UUID branchId, UUID locationId) throws BranchNotFoundException;

    List<BranchResponse> getAllBranches();

    Branch getBranchById(UUID branchId) throws BranchNotFoundException;
}
