package com.josh.pms.validator.status;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileNumberValidator.class)
public @interface Mobile {
	String message() default "Enter  valid Mobile Number";
	
	Class<?>[] groups() default{};
	public abstract Class<? extends Payload>[] payload() default {};

} 	
