package com.teammatch.projectmanagement.dto;

import com.teammatch.projectmanagement.model.ProjectStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDTO {

    @NotBlank(message = "Project title is required")
    private String title;

    @NotBlank(message = "Project description is required")
    private String description;

    @NotEmpty(message = "At least one required skill is required")
    @Builder.Default
    private Set<@NotBlank(message = "Skill value must not be blank") String> requiredSkills = new LinkedHashSet<>();

    @NotBlank(message = "Required experience is required")
    private String requiredExperience;

    @NotNull(message = "Required number of team members is required")
    @Min(value = 1, message = "Required number of team members must be at least 1")
    private Integer requiredNumberOfTeamMembers;

    @NotNull(message = "Team lead requirement must be specified")
    private Boolean teamLeadRequirement;

    @NotNull(message = "Project status is required")
    private ProjectStatus status;

    @NotNull(message = "Personality requirements are required")
    @Valid
    private PersonalityRequirementsDTO personalityRequirements;

    @NotNull(message = "Availability requirements are required")
    @Valid
    private AvailabilityRequirementsDTO availabilityRequirements;
}
