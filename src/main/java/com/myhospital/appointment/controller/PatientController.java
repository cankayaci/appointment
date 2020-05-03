package com.myhospital.appointment.controller;


import com.myhospital.appointment.entity.Patient;
import com.myhospital.appointment.model.bindModel.patient.EditPatientModel;
import com.myhospital.appointment.model.bindModel.patient.PatientAddModel;
import com.myhospital.appointment.service.PatientService;
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
public class PatientController {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_SIZE = 5;

    @Autowired
    private PatientService patientService;

    @GetMapping(value = "/patient")
    public String patient(Model model, @ModelAttribute("result") Optional<String> result) {
        model.addAttribute("patientAddModel", new PatientAddModel());
        model.addAttribute("result",result.orElse(""));
        return "views/patient/patient-create";
    }

    @PostMapping(value = "/patient")
    public String patientSubmit(@Valid @ModelAttribute("patientAddModel")
                                        PatientAddModel patientAddModel, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("result", "error");
            return "views/patient/patient-create";
        }
        patientService.save(patientAddModel);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/patient";
    }

    @GetMapping(value = "/patient/search")

    public String searchPatients(Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size,
                                 @RequestParam("identityNumber") Optional<String> identityNumber,
                                 @RequestParam("name") Optional<String> name,
                                 @RequestParam("surname") Optional<String> surname,
                                 @ModelAttribute("result") Optional<String> result
    ) {

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get();
        final int pageSize = (size.orElse(0) < 1) ? INITIAL_SIZE : size.get();

        String identity = identityNumber.orElse("");
        String patientName = name.orElse("");
        String patientSurname = surname.orElse("");

        Page<Patient> patientPage = patientService.findByCriteria(identity, patientName, patientSurname, PageRequest.of(currentPage, pageSize));

        String url = String.format("/patient/search?identityNumber=%s&name=%s&surname=%s", identity, patientName, patientSurname);

        PageWrapper<Patient> patientPageWrapper = new PageWrapper<Patient>(patientPage, url);

        model.addAttribute("patientPage", patientPage);
        model.addAttribute("page", patientPageWrapper);
        model.addAttribute("name", patientName);
        model.addAttribute("surname", patientSurname);
        model.addAttribute("identityNumber", identity);
        model.addAttribute("result",result.orElse(""));
        return "views/patient/patient-search";
    }

    @GetMapping(value = "/patient/edit/{patientId}")
    public String getPatientForEdit(@PathVariable(name = "patientId") Integer patientId, Model model) {

        EditPatientModel editPatientModel = patientService.findById(patientId);
        model.addAttribute("editPatientModel", editPatientModel);
        return "views/patient/patient-edit";
    }

    @PostMapping(value = "/patient/edit/{patientId}")
    public String postPatientForEdit(@Valid @ModelAttribute EditPatientModel editPatientModel
            , BindingResult bindingResult, Model model, @PathVariable(name = "patientId") Integer patientId, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("result", "error");
            return "views/patient/patient-edit";
        }
        editPatientModel.setId(patientId);
        patientService.save(editPatientModel);
        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/patient/search";

    }

}
