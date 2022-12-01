package com.josh.pms.validator.status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

public class StatusValidator implements ConstraintValidator<StatusValidation, Integer> {
    @Override
    public boolean isValid(Integer statusId, ConstraintValidatorContext context) {
        Predicate<Integer> statusIdIsValid = (id) -> ( id > 0 && id < 5);
        return statusIdIsValid.test(statusId);
    }
}
