package com.teammatch.projectmanagement.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_required_skills", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "skill", nullable = false)
    @Builder.Default
    private Set<String> requiredSkills = new LinkedHashSet<>();

    @Column(nullable = false)
    private String requiredExperience;

    @Column(name = "required_team_members", nullable = false)
    private Integer requiredNumberOfTeamMembers;

    @Column(name = "team_lead_requirement", nullable = false)
    private Boolean teamLeadRequirement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    /**
     * Big Five personality trait scores (0-100) expected from a matched candidate.
     * Consumed by the AI Recommendation microservice.
     */
    @Embedded
    private PersonalityRequirements personalityRequirements;

    /**
     * Date range during which the project needs its team members to be available.
     * Consumed by the AI Recommendation microservice.
     */
    @Embedded
    private AvailabilityRequirements availabilityRequirements;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

