package com.teammatch.projectmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammatch.projectmanagement.dto.AvailabilityRequirementsDTO;
import com.teammatch.projectmanagement.dto.PersonalityRequirementsDTO;
import com.teammatch.projectmanagement.dto.ProjectRequestDTO;
import com.teammatch.projectmanagement.model.ProjectStatus;
import com.teammatch.projectmanagement.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        projectRepository.deleteAll();
    }

    private static PersonalityRequirementsDTO defaultPersonality() {
        return PersonalityRequirementsDTO.builder()
                .extraversion(80)
                .agreeableness(70)
                .conscientiousness(90)
                .neuroticism(40)
                .openness(80)
                .build();
    }

    private static AvailabilityRequirementsDTO defaultAvailability() {
        return AvailabilityRequirementsDTO.builder()
                .requiredFrom(LocalDate.of(2026, 9, 1))
                .requiredUntil(LocalDate.of(2026, 12, 31))
                .build();
    }

    @Test
    void shouldCreateProjectSuccessfully() throws Exception {
        ProjectRequestDTO request = ProjectRequestDTO.builder()
                .title("Nouveau Projet Test")
                .description("Description du projet de test d'intégration")
                .requiredSkills(new LinkedHashSet<>(Set.of("Java", "Spring Boot")))
                .requiredExperience("Senior")
                .requiredNumberOfTeamMembers(3)
                .teamLeadRequirement(true)
                .status(ProjectStatus.DRAFT)
                .personalityRequirements(defaultPersonality())
                .availabilityRequirements(defaultAvailability())
                .build();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title", is("Nouveau Projet Test")))
                .andExpect(jsonPath("$.status", is("DRAFT")))
                .andExpect(jsonPath("$.personalityRequirements.extraversion", is(80)))
                .andExpect(jsonPath("$.availabilityRequirements.requiredFrom", is("2026-09-01")))
                .andExpect(jsonPath("$.availabilityRequirements.requiredUntil", is("2026-12-31")));
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsBlank() throws Exception {
        ProjectRequestDTO request = ProjectRequestDTO.builder()
                .title("")
                .description("Description valid")
                .requiredSkills(new LinkedHashSet<>(Set.of("Java")))
                .requiredExperience("Mid")
                .requiredNumberOfTeamMembers(2)
                .teamLeadRequirement(false)
                .status(ProjectStatus.ACTIVE)
                .personalityRequirements(defaultPersonality())
                .availabilityRequirements(defaultAvailability())
                .build();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").exists());
    }

    @Test
    void shouldReturnBadRequestWhenPersonalityScoreOutOfRange() throws Exception {
        ProjectRequestDTO request = ProjectRequestDTO.builder()
                .title("Projet score invalide")
                .description("Description valide")
                .requiredSkills(new LinkedHashSet<>(Set.of("Java")))
                .requiredExperience("Mid")
                .requiredNumberOfTeamMembers(2)
                .teamLeadRequirement(false)
                .status(ProjectStatus.ACTIVE)
                .personalityRequirements(PersonalityRequirementsDTO.builder()
                        .extraversion(150)
                        .agreeableness(70)
                        .conscientiousness(90)
                        .neuroticism(40)
                        .openness(80)
                        .build())
                .availabilityRequirements(defaultAvailability())
                .build();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenRequiredUntilBeforeRequiredFrom() throws Exception {
        ProjectRequestDTO request = ProjectRequestDTO.builder()
                .title("Projet dates invalides")
                .description("Description valide")
                .requiredSkills(new LinkedHashSet<>(Set.of("Java")))
                .requiredExperience("Mid")
                .requiredNumberOfTeamMembers(2)
                .teamLeadRequirement(false)
                .status(ProjectStatus.ACTIVE)
                .personalityRequirements(defaultPersonality())
                .availabilityRequirements(AvailabilityRequirementsDTO.builder()
                        .requiredFrom(LocalDate.of(2026, 12, 31))
                        .requiredUntil(LocalDate.of(2026, 9, 1))
                        .build())
                .build();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldExposeAiPayloadWithCapitalizedPersonalityKeys() throws Exception {
        ProjectRequestDTO request = ProjectRequestDTO.builder()
                .title("Projet IA")
                .description("Description valide")
                .requiredSkills(new LinkedHashSet<>(Set.of("Java")))
                .requiredExperience("Mid")
                .requiredNumberOfTeamMembers(2)
                .teamLeadRequirement(false)
                .status(ProjectStatus.ACTIVE)
                .personalityRequirements(defaultPersonality())
                .availabilityRequirements(defaultAvailability())
                .build();

        String responseContent = mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String projectId = objectMapper.readTree(responseContent).get("id").asText();

        mockMvc.perform(get("/api/projects/" + projectId + "/ai-payload"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalityRequirements.Extraversion", is(80)))
                .andExpect(jsonPath("$.personalityRequirements.Agreeableness", is(70)))
                .andExpect(jsonPath("$.availabilityRequirements.requiredFrom", is("2026-09-01")));
    }

    @Test
    void shouldListProjects() throws Exception {
        ProjectRequestDTO request = ProjectRequestDTO.builder()
                .title("Projet 1")
                .description("Description 1")
                .requiredSkills(new LinkedHashSet<>(Set.of("React")))
                .requiredExperience("Junior")
                .requiredNumberOfTeamMembers(1)
                .teamLeadRequirement(false)
                .status(ProjectStatus.ACTIVE)
                .personalityRequirements(defaultPersonality())
                .availabilityRequirements(defaultAvailability())
                .build();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Projet 1")));
    }

    @Test
    void shouldDeleteProjectSuccessfully() throws Exception {
        ProjectRequestDTO request = ProjectRequestDTO.builder()
                .title("Projet à supprimer")
                .description("Description projet à supprimer")
                .requiredSkills(new LinkedHashSet<>(Set.of("Java", "PostgreSQL")))
                .requiredExperience("Senior")
                .requiredNumberOfTeamMembers(2)
                .teamLeadRequirement(true)
                .status(ProjectStatus.ACTIVE)
                .personalityRequirements(defaultPersonality())
                .availabilityRequirements(defaultAvailability())
                .build();

        String responseContent = mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String projectId = objectMapper.readTree(responseContent).get("id").asText();

        mockMvc.perform(delete("/api/projects/" + projectId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/projects/" + projectId))
                .andExpect(status().isNotFound());
    }
}
