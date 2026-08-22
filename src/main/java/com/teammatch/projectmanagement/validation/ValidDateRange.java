package com.teammatch.projectmanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level constraint validating that a "requiredUntil" date is not before
 * its corresponding "requiredFrom" date. Applied on {@code AvailabilityRequirementsDTO}.
 */
@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {

    String message() default "requiredUntil must not be before requiredFrom";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
