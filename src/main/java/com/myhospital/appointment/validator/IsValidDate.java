package com.myhospital.appointment.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsValidDateValidator.class)
public @interface IsValidDate {

    String message() default "{lang.not.valid.date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
