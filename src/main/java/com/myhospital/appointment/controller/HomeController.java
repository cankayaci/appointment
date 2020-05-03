package com.myhospital.appointment.controller;

import com.myhospital.appointment.service.AppointmentService;
import com.myhospital.appointment.service.DoctorService;
import com.myhospital.appointment.service.PatientService;
import com.myhospital.appointment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){

        model.addAttribute("totalAppointmentCount",appointmentService.countTotalAppointments());
        model.addAttribute("totalPatientCount",patientService.countTotalPatients());
        model.addAttribute("totalDoctorCount",doctorService.countTotalDoctors());
        model.addAttribute("totalUserCount",userService.countTotalUsers());

        return "views/dashboard/dashboard";
    }
}
