package com.myhospital.appointment.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class IsValidDateValidator implements ConstraintValidator<IsValidDate, Date> {
    @Override
    public void initialize(IsValidDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {

        if (!(date instanceof Date)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate( "{my.custom.template}" )
                    .addPropertyNode( "date" ).addConstraintViolation();
            return false;
        }
        return true;
    }
}
