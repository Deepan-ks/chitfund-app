package com.project.chitfundmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chit_group_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChitGroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to chit group
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chit_group_id", nullable = false)
    private ChitGroup chitGroup;

    // Link to member
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean hasTakenChit = false; // default: not taken

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
