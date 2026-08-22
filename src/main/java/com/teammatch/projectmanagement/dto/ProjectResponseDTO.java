package com.teammatch.projectmanagement.dto;

import com.teammatch.projectmanagement.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {

    private UUID id;
    private String title;
    private String description;
    private Set<String> requiredSkills;
    private String requiredExperience;
    private Integer requiredNumberOfTeamMembers;
    private Boolean teamLeadRequirement;
    private ProjectStatus status;
    private PersonalityRequirementsDTO personalityRequirements;
    private AvailabilityRequirementsDTO availabilityRequirements;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
