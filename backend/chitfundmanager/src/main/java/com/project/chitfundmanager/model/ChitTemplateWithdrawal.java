package com.project.chitfundmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "chit_template_withdrawal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChitTemplateWithdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to template
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    private ChitTemplate template;

    @Column(nullable = false)
    private Integer monthNumber;  // e.g., 1, 2, 3...

    @Column(nullable = false)
    private Double withdrawalAmount;  // amount receivable in this month

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
