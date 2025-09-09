package com.project.chitfundmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chit_receivable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChitReceivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which chit group this receivable belongs to
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chit_group_id", nullable = false)
    private ChitGroup chitGroup;

    @Column(nullable = false)
    private Integer monthNumber;  // Example: 1, 2, 3, ...

    @Column(nullable = false)
    private Double receivableAmount;

    @Column(nullable = false)
    private Boolean isCompanyShare = false; // true only for month 1

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
