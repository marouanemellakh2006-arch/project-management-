package com.teammatch.projectmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teammatch.projectmanagement.validation.ValidDateRange;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidDateRange
public class AvailabilityRequirementsDTO {

    @NotNull(message = "requiredFrom is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate requiredFrom;

    @NotNull(message = "requiredUntil is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate requiredUntil;
}
