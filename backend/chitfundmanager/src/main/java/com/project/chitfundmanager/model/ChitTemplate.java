package com.project.chitfundmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chit_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChitTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double fundValue;   // e.g., 25000, 50000, 100000

    @Column(nullable = false)
    private Integer durationMonths; // total months

    @Column(nullable = false)
    private Double monthlyDue;  // due per member

    // Who created this template
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private Manager createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
