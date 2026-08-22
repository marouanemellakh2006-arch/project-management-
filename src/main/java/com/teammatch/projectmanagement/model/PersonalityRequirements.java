package com.teammatch.projectmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Big Five personality trait scores (0-100) expected from a matched candidate.
 * Embedded directly into {@link Project} and consumed by the AI Recommendation
 * microservice to score candidate/project compatibility.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalityRequirements {

    @Column(name = "personality_extraversion", nullable = false)
    private Integer extraversion;

    @Column(name = "personality_agreeableness", nullable = false)
    private Integer agreeableness;

    @Column(name = "personality_conscientiousness", nullable = false)
    private Integer conscientiousness;

    @Column(name = "personality_neuroticism", nullable = false)
    private Integer neuroticism;

    @Column(name = "personality_openness", nullable = false)
    private Integer openness;
}
