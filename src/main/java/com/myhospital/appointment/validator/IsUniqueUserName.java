package com.myhospital.appointment.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsUserNameUniqueValidator.class)
public @interface IsUniqueUserName {
    String message() default "{lang.system.user.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
