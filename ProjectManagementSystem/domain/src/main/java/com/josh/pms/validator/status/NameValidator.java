package com.josh.pms.validator.status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		char[] chars = name.toCharArray();
		for (char c : chars) {
			if (Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

}
