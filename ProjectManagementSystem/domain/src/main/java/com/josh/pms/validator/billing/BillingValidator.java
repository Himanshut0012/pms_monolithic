package com.josh.pms.validator.billing;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

public class BillingValidator implements ConstraintValidator<BillingValidation, Integer> {

    @Override
    public boolean isValid(Integer billingId, ConstraintValidatorContext context) {
        Predicate<Integer> billingIdPresent = (id) -> ( id > 0 && id < 4);
        return billingIdPresent.test(billingId);
    }
}
