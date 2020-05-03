package com.myhospital.appointment.controller;

import com.myhospital.appointment.entity.Doctor;
import com.myhospital.appointment.entity.Patient;
import com.myhospital.appointment.model.bindModel.doctor.AddDoctorModel;
import com.myhospital.appointment.model.bindModel.doctor.EditDoctorModel;
import com.myhospital.appointment.model.bindModel.patient.EditPatientModel;
import com.myhospital.appointment.service.DepartmentService;
import com.myhospital.appointment.service.DoctorService;
import com.myhospital.appointment.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class DoctorController {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_SIZE = 5;

    @Autowired
    private DoctorService doctorService;

    @GetMapping(value="/doctor")
    public String doctor(Model model, @ModelAttribute("result") Optional<String> result){
        model.addAttribute("addDoctorModel",new AddDoctorModel());
        model.addAttribute("result",result.orElse(""));
        return "views/doctor/doctor-create";
    }

    @PostMapping(value="/doctor")
    public String doctorSubmit(
            @Valid @ModelAttribute("addDoctorModel") AddDoctorModel addDoctorModel,
            BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            model.addAttribute("result","error");
            return "views/doctor/doctor-create";
        }
        doctorService.save(addDoctorModel);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/doctor";
    }

    @GetMapping(value="/doctor/search")
    public String doctorSearch(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("department") Optional<String> department,
                               @RequestParam("name") Optional<String> name,
                               @RequestParam("surname") Optional<String> surname,
                               @RequestParam("identityNumber") Optional<String> identityNumber,
                               @ModelAttribute("result") Optional<String> result){

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() ;
        final int pageSize =  (size.orElse(0) < 1) ? INITIAL_SIZE : size.get();

        String dept = department.orElse("");
        String doctorName = name.orElse("");
        String doctorSurname = surname.orElse("");

        Page<Doctor> doctorPage = doctorService.findByCriteria(dept, doctorName, doctorSurname, PageRequest.of(currentPage, pageSize));

        String url = String.format("/doctor/search?department=%s&name=%s&surname=%s",dept,doctorName,doctorSurname);

        PageWrapper<Doctor> doctorPageWrapper = new PageWrapper<>(doctorPage,url);
        model.addAttribute("doctorPage", doctorPage);
        model.addAttribute("page", doctorPageWrapper);
        model.addAttribute("name",doctorName);
        model.addAttribute("surname",doctorSurname);
        model.addAttribute("department",dept);
        model.addAttribute("result",result.orElse(""));

        return "views/doctor/doctor-search";
    }

    @GetMapping(value="/doctor/edit/{doctorId}")
    public String getPatientForEdit(@PathVariable(name = "doctorId") Integer doctorId,Model model){

        EditDoctorModel editDoctorModel =  doctorService.findById(doctorId);
        model.addAttribute("editDoctorModel",editDoctorModel);
        return "views/doctor/doctor-edit";
    }
    @PostMapping(value="/doctor/edit/{doctorId}")
    public String postPatientForEdit(@Valid @ModelAttribute EditDoctorModel editDoctorModel
            , BindingResult bindingResult, Model model, @PathVariable(name = "doctorId") Integer doctorId, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors() ){
            model.addAttribute("result","error");
            return "views/doctor/doctor-edit";
        }

        editDoctorModel.setId(doctorId);
        doctorService.save(editDoctorModel);
        redirectAttributes.addFlashAttribute("result","success");
        return "redirect:/doctor/search";

    }
}

