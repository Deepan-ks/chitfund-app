package com.project.chitfundmanager.model;

import com.project.chitfundmanager.model.enums.ContributionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contribution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to chit group member
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chit_group_member_id", nullable = false)
    private ChitGroupMember chitGroupMember;

    @Column(nullable = false)
    private Integer monthNumber;  // Which month’s due this payment belongs to

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private Double amountPaid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContributionStatus status; // PAID, PENDING, LATE

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}