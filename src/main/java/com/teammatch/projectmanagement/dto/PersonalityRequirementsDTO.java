package com.teammatch.projectmanagement.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalityRequirementsDTO {

    @NotNull(message = "Extraversion score is required")
    @Min(value = 0, message = "Extraversion score must be between 0 and 100")
    @Max(value = 100, message = "Extraversion score must be between 0 and 100")
    private Integer extraversion;

    @NotNull(message = "Agreeableness score is required")
    @Min(value = 0, message = "Agreeableness score must be between 0 and 100")
    @Max(value = 100, message = "Agreeableness score must be between 0 and 100")
    private Integer agreeableness;

    @NotNull(message = "Conscientiousness score is required")
    @Min(value = 0, message = "Conscientiousness score must be between 0 and 100")
    @Max(value = 100, message = "Conscientiousness score must be between 0 and 100")
    private Integer conscientiousness;

    @NotNull(message = "Neuroticism score is required")
    @Min(value = 0, message = "Neuroticism score must be between 0 and 100")
    @Max(value = 100, message = "Neuroticism score must be between 0 and 100")
    private Integer neuroticism;

    @NotNull(message = "Openness score is required")
    @Min(value = 0, message = "Openness score must be between 0 and 100")
    @Max(value = 100, message = "Openness score must be between 0 and 100")
    private Integer openness;
}
