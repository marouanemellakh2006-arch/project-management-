package com.teammatch.projectmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Date range during which the project needs its team members to be available.
 * Embedded directly into {@link Project} and consumed by the AI Recommendation
 * microservice to filter candidates by availability.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityRequirements {

    @Column(name = "required_from", nullable = false)
    private LocalDate requiredFrom;

    @Column(name = "required_until", nullable = false)
    private LocalDate requiredUntil;
}
