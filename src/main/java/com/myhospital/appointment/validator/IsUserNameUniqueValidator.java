package com.myhospital.appointment.validator;

import com.myhospital.appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsUserNameUniqueValidator implements ConstraintValidator<IsUniqueUserName, String> {
    @Autowired
    private UserRepository userRepository;


    @Override
    public void initialize(IsUniqueUserName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return username !=null && userRepository.findByUsername(username) ==null;
    }
}
