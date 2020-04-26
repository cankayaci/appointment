package com.myhospital.appointment.validator;

import com.myhospital.appointment.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsUniquePatientIdentityNumberValidator implements ConstraintValidator<IsUniquePatientIdentityNumber, String> {
    @Autowired
    private PatientService patientService;
    @Override
    public void initialize(IsUniquePatientIdentityNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String identityNumber, ConstraintValidatorContext constraintValidatorContext) {
        return identityNumber !=null && patientService.findByIdentityNumber(identityNumber) ==null;
    }
}
