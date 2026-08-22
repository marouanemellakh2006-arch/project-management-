package com.teammatch.projectmanagement.controller;

import com.teammatch.projectmanagement.dto.ProjectRequestDTO;
import com.teammatch.projectmanagement.dto.ProjectResponseDTO;
import com.teammatch.projectmanagement.dto.ai.AiProjectPayloadDTO;
import com.teammatch.projectmanagement.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO request) {
        ProjectResponseDTO response = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable("projectId") UUID projectId,
            @Valid @RequestBody ProjectRequestDTO request) {
        return ResponseEntity.ok(projectService.updateProject(projectId, request));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable("projectId") UUID projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable("projectId") UUID projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> listProjects() {
        return ResponseEntity.ok(projectService.listProjects());
    }

    /**
     * Returns the project dataset formatted for the AI Recommendation
     * microservice (personality + availability requirements included, with
     * the exact JSON shape expected by that service). The recommendation
     * algorithm itself is implemented by the AI microservice, not here.
     */
    @GetMapping("/{projectId}/ai-payload")
    public ResponseEntity<AiProjectPayloadDTO> getProjectAiPayload(@PathVariable("projectId") UUID projectId) {
        return ResponseEntity.ok(projectService.getProjectAiPayload(projectId));
    }
}
