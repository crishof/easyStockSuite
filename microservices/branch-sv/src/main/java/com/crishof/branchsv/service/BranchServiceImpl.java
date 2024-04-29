package com.crishof.branchsv.service;

import com.crishof.branchsv.dto.BranchRequest;
import com.crishof.branchsv.dto.BranchResponse;
import com.crishof.branchsv.dto.LocationResponse;
import com.crishof.branchsv.model.Branch;
import com.crishof.branchsv.model.StockLocation;
import com.crishof.branchsv.repository.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private static final String DEFAULT_LOCATION = "Main Showroom";
    private final BranchRepository branchRepository;

    @Override
    public BranchResponse createBranch(BranchRequest branchRequest) {

        Branch branch = new Branch();
        branch.setName(branchRequest.getName());

        List<StockLocation> stockLocations = new ArrayList<>();
        StockLocation stockLocation = new StockLocation();
        stockLocation.setBranch(branch);
        stockLocation.setName(DEFAULT_LOCATION);
        stockLocations.add(stockLocation);
        branch.setLocations(stockLocations);

        return this.toBranchResponse(branchRepository.save(branch));
    }

    @Override
    public BranchResponse updateBranchName(UUID branchId, BranchRequest branchRequest) {

        Branch branch = branchRepository.getReferenceById(branchId);
        branch.setName(branchRequest.getName());

        return this.toBranchResponse(branchRepository.save(branch));
    }

    @Override
    public BranchResponse createLocation(UUID branchId, String name) {

        Branch branch = branchRepository.getReferenceById(branchId);
        StockLocation stockLocation = new StockLocation();
        stockLocation.setName(name);

        List<StockLocation> locations = branch.getLocations();
        locations.add(stockLocation);
        branch.setLocations(locations);
        return this.toBranchResponse(branchRepository.save(branch));
    }

    @Override
    public BranchResponse updateLocation(UUID branchId, UUID locationId, String name) {

        Optional<Branch> optionalBranch = branchRepository.findById(branchId);

        if (optionalBranch.isEmpty()) {
            throw new EntityNotFoundException("Branch with ID " + branchId + " not found");
        }

        Branch branch = optionalBranch.get();

        Optional<StockLocation> optionalLocation = branch.getLocations().stream()
                .filter(location -> location.getId().equals(locationId))
                .findAny();

        if (optionalLocation.isEmpty()) {
            throw new EntityNotFoundException("Location with ID " + locationId + " not found in branch " + branchId);
        }

        StockLocation location = optionalLocation.get();
        location.setName(name);

        return this.toBranchResponse(branchRepository.save(branch));
    }

    @Override
    public BranchResponse deleteLocation(UUID branchId, UUID locationId) {

        Optional<Branch> optionalBranch = branchRepository.findById(branchId);

        if (optionalBranch.isEmpty()) {
            throw new EntityNotFoundException("Branch with ID " + branchId + " not found");
        }

        Branch branch = optionalBranch.get();

        Optional<StockLocation> optionalLocation = branch.getLocations().stream()
                .filter(stockLocation -> stockLocation.getId().equals(locationId))
                .findAny();

        if (optionalLocation.isEmpty()) {
            throw new EntityNotFoundException("Location with ID " + locationId + " not found in branch " + branchId);
        }

        StockLocation location = optionalLocation.get();
        branch.getLocations().remove(location);

        return this.toBranchResponse(branchRepository.save(branch));
    }

    @Override
    public List<BranchResponse> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        return branches.stream()
                .map(this::toBranchResponse).toList();
    }

    private BranchResponse toBranchResponse(Branch branch) {
        BranchResponse branchResponse = new BranchResponse();
        branchResponse.setId(branch.getId());
        branchResponse.setName(branch.getName());
        branchResponse.setLocations(branch.getLocations().stream().map(this::toLocationResponse).toList());

        return branchResponse;
    }

    private LocationResponse toLocationResponse(StockLocation stockLocation) {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setId(stockLocation.getId());
        locationResponse.setName(stockLocation.getName());
        return locationResponse;
    }
}

