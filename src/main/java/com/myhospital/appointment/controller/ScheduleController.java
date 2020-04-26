package com.myhospital.appointment.controller;

import com.myhospital.appointment.entity.Doctor;
import com.myhospital.appointment.entity.Schedule;
import com.myhospital.appointment.model.bindModel.patient.EditPatientModel;
import com.myhospital.appointment.model.bindModel.schedule.AddScheduleModel;
import com.myhospital.appointment.model.bindModel.schedule.BaseScheduleModel;
import com.myhospital.appointment.service.DoctorService;
import com.myhospital.appointment.service.ScheduleService;
import com.myhospital.appointment.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Controller
public class ScheduleController {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_SIZE = 5;

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private DoctorService doctorService;


    @GetMapping(value="/doctor/schedule/{doctorId}")
    public String getDoctorSchedule(@PathVariable(name = "doctorId") Integer doctorId,Model model){

        Doctor doctor = doctorService.findDoctorById(doctorId);
        AddScheduleModel addScheduleModel = new AddScheduleModel();
        addScheduleModel.setDoctor(doctor);

        model.addAttribute("addScheduleModel",addScheduleModel);
        return "views/schedule/schedule-create";
    }

    @PostMapping(value="/doctor/schedule/{doctorId}")
    public String postDoctorSchedule(@Valid @ModelAttribute("addScheduleModel") AddScheduleModel addScheduleModel
            , BindingResult bindingResult, Model model, @PathVariable(name = "doctorId") Integer doctorId, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors() ){
            model.addAttribute("result","error");
            model.addAttribute("addScheduleModel",addScheduleModel);
            return "views/schedule/schedule-create";
        }

        Doctor doctor = doctorService.findDoctorById(doctorId);
        addScheduleModel.setDoctor(doctor);
        Boolean save = scheduleService.save(addScheduleModel,doctorId);
        if(save){
            redirectAttributes.addFlashAttribute("result", "success");
        }
        else{
            model.addAttribute("result","error");
            model.addAttribute("addScheduleModel",addScheduleModel);
            return "views/schedule/schedule-create";
        }

        return "redirect:/doctor/search";
    }

}
