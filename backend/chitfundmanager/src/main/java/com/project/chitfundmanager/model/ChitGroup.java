package com.project.chitfundmanager.model;

import com.project.chitfundmanager.model.enums.ChitGroupStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "chit_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChitGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to template (fund structure + withdrawal schedule)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    private ChitTemplate template;

    // Which manager created/owns this group
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @Column(nullable = false, length = 150)
    private String name; // Example: "Jan 2025 – 1L Group A"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ChitGroupStatus status; // NEW, ACTIVE, CLOSED

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private Integer durationMonths; // copies from template

    @Column(nullable = false)
    private Double monthlyDue; // copies from template

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}