package com.teammatch.projectmanagement.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Personality requirements formatted for the AI Recommendation microservice.
 * The trait keys are capitalized (e.g. "Extraversion") to match the exact
 * JSON contract expected by the AI service, independent of the internal
 * camelCase Java naming convention used elsewhere in the REST API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiPersonalityRequirementsDTO {

    @JsonProperty("Extraversion")
    private Integer extraversion;

    @JsonProperty("Agreeableness")
    private Integer agreeableness;

    @JsonProperty("Conscientiousness")
    private Integer conscientiousness;

    @JsonProperty("Neuroticism")
    private Integer neuroticism;

    @JsonProperty("Openness")
    private Integer openness;
}
