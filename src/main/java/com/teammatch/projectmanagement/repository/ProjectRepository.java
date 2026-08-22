package com.teammatch.projectmanagement.repository;

import com.teammatch.projectmanagement.model.Project;
import com.teammatch.projectmanagement.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Optional<Project> findFirstByTitleAndDescriptionAndRequiredExperienceAndRequiredNumberOfTeamMembersAndTeamLeadRequirementAndStatus(
            String title,
            String description,
            String requiredExperience,
            Integer requiredNumberOfTeamMembers,
            Boolean teamLeadRequirement,
            ProjectStatus status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM projects WHERE id = :id", nativeQuery = true)
    void deleteProjectByIdNative(@Param("id") UUID id);
}
