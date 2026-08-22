package com.teammatch.projectmanagement.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * Project dataset sent from the backend to the AI Recommendation microservice.
 * This DTO only carries the data the matching algorithm needs as input; the
 * matching/recommendation algorithm itself is implemented by that separate
 * microservice and is out of scope here.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiProjectPayloadDTO {

    private UUID projectId;
    private String title;
    private Set<String> requiredSkills;
    private String requiredExperience;
    private Integer requiredNumberOfTeamMembers;
    private Boolean teamLeadRequirement;
    private AiPersonalityRequirementsDTO personalityRequirements;
    private AiAvailabilityRequirementsDTO availabilityRequirements;
}
