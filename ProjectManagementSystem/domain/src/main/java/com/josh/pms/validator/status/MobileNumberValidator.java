package com.josh.pms.validator.status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;

public class MobileNumberValidator implements ConstraintValidator<Mobile, String> {

	@Override
	public boolean isValid(String mobileNo, ConstraintValidatorContext context) {
		if (mobileNo!=null &&!mobileNo.isEmpty() && mobileNo.length() == 10) {
			char[] chars = mobileNo.toCharArray();
			for (char c : chars) {
				if (Character.isAlphabetic(c)) {
					return false;
				}
			}
			return true;
		} else {
			return false;

		}
	}
}
