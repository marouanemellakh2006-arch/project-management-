package com.teammatch.projectmanagement.mapper;

import com.teammatch.projectmanagement.dto.AvailabilityRequirementsDTO;
import com.teammatch.projectmanagement.dto.PersonalityRequirementsDTO;
import com.teammatch.projectmanagement.dto.ProjectRequestDTO;
import com.teammatch.projectmanagement.dto.ProjectResponseDTO;
import com.teammatch.projectmanagement.dto.ai.AiAvailabilityRequirementsDTO;
import com.teammatch.projectmanagement.dto.ai.AiPersonalityRequirementsDTO;
import com.teammatch.projectmanagement.dto.ai.AiProjectPayloadDTO;
import com.teammatch.projectmanagement.model.AvailabilityRequirements;
import com.teammatch.projectmanagement.model.PersonalityRequirements;
import com.teammatch.projectmanagement.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequestDTO request) {
        if (request == null) {
            return null;
        }

        return Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .requiredSkills(request.getRequiredSkills())
                .requiredExperience(request.getRequiredExperience())
                .requiredNumberOfTeamMembers(request.getRequiredNumberOfTeamMembers())
                .teamLeadRequirement(request.getTeamLeadRequirement())
                .status(request.getStatus())
                .personalityRequirements(toEntity(request.getPersonalityRequirements()))
                .availabilityRequirements(toEntity(request.getAvailabilityRequirements()))
                .build();
    }

    public ProjectResponseDTO toResponse(Project project) {
        if (project == null) {
            return null;
        }

        return ProjectResponseDTO.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .requiredSkills(project.getRequiredSkills())
                .requiredExperience(project.getRequiredExperience())
                .requiredNumberOfTeamMembers(project.getRequiredNumberOfTeamMembers())
                .teamLeadRequirement(project.getTeamLeadRequirement())
                .status(project.getStatus())
                .personalityRequirements(toDto(project.getPersonalityRequirements()))
                .availabilityRequirements(toDto(project.getAvailabilityRequirements()))
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }

    /**
     * Builds the dataset sent to the AI Recommendation microservice. Only the
     * data required by the matching algorithm is included; the algorithm
     * itself is implemented by that separate microservice.
     */
    public AiProjectPayloadDTO toAiPayload(Project project) {
        if (project == null) {
            return null;
        }

        return AiProjectPayloadDTO.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .requiredSkills(project.getRequiredSkills())
                .requiredExperience(project.getRequiredExperience())
                .requiredNumberOfTeamMembers(project.getRequiredNumberOfTeamMembers())
                .teamLeadRequirement(project.getTeamLeadRequirement())
                .personalityRequirements(toAiDto(project.getPersonalityRequirements()))
                .availabilityRequirements(toAiDto(project.getAvailabilityRequirements()))
                .build();
    }

    private PersonalityRequirements toEntity(PersonalityRequirementsDTO dto) {
        if (dto == null) {
            return null;
        }
        return PersonalityRequirements.builder()
                .extraversion(dto.getExtraversion())
                .agreeableness(dto.getAgreeableness())
                .conscientiousness(dto.getConscientiousness())
                .neuroticism(dto.getNeuroticism())
                .openness(dto.getOpenness())
                .build();
    }

    private AvailabilityRequirements toEntity(AvailabilityRequirementsDTO dto) {
        if (dto == null) {
            return null;
        }
        return AvailabilityRequirements.builder()
                .requiredFrom(dto.getRequiredFrom())
                .requiredUntil(dto.getRequiredUntil())
                .build();
    }

    private PersonalityRequirementsDTO toDto(PersonalityRequirements entity) {
        if (entity == null) {
            return null;
        }
        return PersonalityRequirementsDTO.builder()
                .extraversion(entity.getExtraversion())
                .agreeableness(entity.getAgreeableness())
                .conscientiousness(entity.getConscientiousness())
                .neuroticism(entity.getNeuroticism())
                .openness(entity.getOpenness())
                .build();
    }

    private AvailabilityRequirementsDTO toDto(AvailabilityRequirements entity) {
        if (entity == null) {
            return null;
        }
        return AvailabilityRequirementsDTO.builder()
                .requiredFrom(entity.getRequiredFrom())
                .requiredUntil(entity.getRequiredUntil())
                .build();
    }

    private AiPersonalityRequirementsDTO toAiDto(PersonalityRequirements entity) {
        if (entity == null) {
            return null;
        }
        return AiPersonalityRequirementsDTO.builder()
                .extraversion(entity.getExtraversion())
                .agreeableness(entity.getAgreeableness())
                .conscientiousness(entity.getConscientiousness())
                .neuroticism(entity.getNeuroticism())
                .openness(entity.getOpenness())
                .build();
    }

    private AiAvailabilityRequirementsDTO toAiDto(AvailabilityRequirements entity) {
        if (entity == null) {
            return null;
        }
        return AiAvailabilityRequirementsDTO.builder()
                .requiredFrom(entity.getRequiredFrom())
                .requiredUntil(entity.getRequiredUntil())
                .build();
    }
}
