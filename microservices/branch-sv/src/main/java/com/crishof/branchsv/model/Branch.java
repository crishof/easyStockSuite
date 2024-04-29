package com.crishof.branchsv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_branch")
@Builder
public class Branch {

    @Id
    @GeneratedValue
    @Column(name = "branch_id")
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<StockLocation> locations;
    private boolean active;

}
