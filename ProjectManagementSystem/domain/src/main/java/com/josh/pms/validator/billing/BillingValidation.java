package com.josh.pms.validator.billing;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BillingValidator.class)
public @interface BillingValidation {

    String message() default "Invalid Billing Id: must be 1, 2 or 3";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
