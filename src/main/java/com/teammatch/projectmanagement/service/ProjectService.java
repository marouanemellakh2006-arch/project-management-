package com.teammatch.projectmanagement.service;

import com.teammatch.projectmanagement.dto.ProjectRequestDTO;
import com.teammatch.projectmanagement.dto.ProjectResponseDTO;
import com.teammatch.projectmanagement.dto.ai.AiProjectPayloadDTO;
import com.teammatch.projectmanagement.exception.DuplicateResourceException;
import com.teammatch.projectmanagement.exception.ResourceNotFoundException;
import com.teammatch.projectmanagement.mapper.ProjectMapper;
import com.teammatch.projectmanagement.model.AvailabilityRequirements;
import com.teammatch.projectmanagement.model.PersonalityRequirements;
import com.teammatch.projectmanagement.model.Project;
import com.teammatch.projectmanagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO request) {
        ensureProjectIsNotDuplicate(request);
        Project project = projectMapper.toEntity(request);
        Project saved = projectRepository.save(project);
        return projectMapper.toResponse(saved);
    }

    private void ensureProjectIsNotDuplicate(ProjectRequestDTO request) {
        projectRepository
                .findFirstByTitleAndDescriptionAndRequiredExperienceAndRequiredNumberOfTeamMembersAndTeamLeadRequirementAndStatus(
                        request.getTitle(),
                        request.getDescription(),
                        request.getRequiredExperience(),
                        request.getRequiredNumberOfTeamMembers(),
                        request.getTeamLeadRequirement(),
                        request.getStatus())
                .ifPresent(existing -> {
                    if (existing.getRequiredSkills().equals(request.getRequiredSkills())) {
                        throw new DuplicateResourceException("Un projet identique existe déjà.");
                    }
                });
    }

    private void ensureProjectIsNotDuplicateOnUpdate(UUID projectId, ProjectRequestDTO request) {
        projectRepository
                .findFirstByTitleAndDescriptionAndRequiredExperienceAndRequiredNumberOfTeamMembersAndTeamLeadRequirementAndStatus(
                        request.getTitle(),
                        request.getDescription(),
                        request.getRequiredExperience(),
                        request.getRequiredNumberOfTeamMembers(),
                        request.getTeamLeadRequirement(),
                        request.getStatus())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(projectId) && existing.getRequiredSkills().equals(request.getRequiredSkills())) {
                        throw new DuplicateResourceException("Un projet identique existe déjà.");
                    }
                });
    }

    public ProjectResponseDTO updateProject(UUID projectId, ProjectRequestDTO request) {
        Project existingProject = findProjectById(projectId);
        ensureProjectIsNotDuplicateOnUpdate(projectId, request);
        existingProject.setTitle(request.getTitle());
        existingProject.setDescription(request.getDescription());
        existingProject.setRequiredSkills(request.getRequiredSkills());
        existingProject.setRequiredExperience(request.getRequiredExperience());
        existingProject.setRequiredNumberOfTeamMembers(request.getRequiredNumberOfTeamMembers());
        existingProject.setTeamLeadRequirement(request.getTeamLeadRequirement());
        existingProject.setStatus(request.getStatus());
        existingProject.setPersonalityRequirements(PersonalityRequirements.builder()
                .extraversion(request.getPersonalityRequirements().getExtraversion())
                .agreeableness(request.getPersonalityRequirements().getAgreeableness())
                .conscientiousness(request.getPersonalityRequirements().getConscientiousness())
                .neuroticism(request.getPersonalityRequirements().getNeuroticism())
                .openness(request.getPersonalityRequirements().getOpenness())
                .build());
        existingProject.setAvailabilityRequirements(AvailabilityRequirements.builder()
                .requiredFrom(request.getAvailabilityRequirements().getRequiredFrom())
                .requiredUntil(request.getAvailabilityRequirements().getRequiredUntil())
                .build());

        Project updated = projectRepository.save(existingProject);
        return projectMapper.toResponse(updated);
    }

    @Transactional
    public void deleteProject(UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id: " + projectId);
        }
        projectRepository.deleteProjectByIdNative(projectId);
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(UUID projectId) {
        Project project = findProjectById(projectId);
        return projectMapper.toResponse(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> listProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Builds the dataset for a single project as consumed by the AI
     * Recommendation microservice (personality + availability requirements
     * included). The matching algorithm itself is out of scope.
     */
    @Transactional(readOnly = true)
    public AiProjectPayloadDTO getProjectAiPayload(UUID projectId) {
        Project project = findProjectById(projectId);
        return projectMapper.toAiPayload(project);
    }

    private Project findProjectById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
    }
}
