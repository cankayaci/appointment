package com.myhospital.appointment.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {

        ModelAndView model = new ModelAndView();
        //model.addObject("errMsg", "This is a 'Exception.class' message.");
        model.setViewName("views/error/error");
        return model;

    }
}
