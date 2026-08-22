package com.teammatch.projectmanagement.dto.ai;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Availability requirements formatted for the AI Recommendation microservice.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiAvailabilityRequirementsDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate requiredFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate requiredUntil;
}
