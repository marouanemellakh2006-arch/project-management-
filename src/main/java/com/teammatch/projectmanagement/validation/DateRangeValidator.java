package com.teammatch.projectmanagement.validation;

import com.teammatch.projectmanagement.dto.AvailabilityRequirementsDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, AvailabilityRequirementsDTO> {

    @Override
    public boolean isValid(AvailabilityRequirementsDTO value, ConstraintValidatorContext context) {
        if (value == null || value.getRequiredFrom() == null || value.getRequiredUntil() == null) {
            // Let @NotNull on individual fields report those errors separately.
            return true;
        }
        return !value.getRequiredUntil().isBefore(value.getRequiredFrom());
    }
}
