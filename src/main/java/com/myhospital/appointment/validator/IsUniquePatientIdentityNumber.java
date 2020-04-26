package com.myhospital.appointment.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsUniquePatientIdentityNumberValidator.class)
public @interface IsUniquePatientIdentityNumber {
    String message() default "{lang.patient.identity.number.exists}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
