package com.myhospital.appointment.controller;

import com.myhospital.appointment.entity.Appointment;
import com.myhospital.appointment.entity.Doctor;
import com.myhospital.appointment.entity.Patient;
import com.myhospital.appointment.entity.Schedule;
import com.myhospital.appointment.service.AppointmentService;
import com.myhospital.appointment.service.DoctorService;
import com.myhospital.appointment.service.PatientService;
import com.myhospital.appointment.service.ScheduleService;
import com.myhospital.appointment.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.extras.java8time.expression.Temporals;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Optional;

@Controller
public class AppointmentController {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_SIZE = 5;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping(value = "/appointment")
    public String getDoctors(Model model,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam("department") Optional<String> department,
                             @ModelAttribute("result") Optional<String> result) {

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get();
        final int pageSize = (size.orElse(0) < 1) ? INITIAL_SIZE : size.get();

        String departmentCode = department.isPresent() ? department.get() : "";

        Page<Doctor> doctorPage = doctorService.findDoctorsByCode(PageRequest.of(currentPage, pageSize), departmentCode);

        String url = "/appointment?department=" + department.orElse("");
        PageWrapper<Doctor> doctorPageWrapper = new PageWrapper<>(doctorPage, url);
        model.addAttribute("doctorPage", doctorPage);
        model.addAttribute("page", doctorPageWrapper);
        model.addAttribute("result",result.orElse(""));

        return "views/appointment/doctor-list";
    }

    @GetMapping(value = "/appointment/{doctorId}/schedule")
    public String getDoctorSchedules(@PathVariable(name = "doctorId") Integer doctorId,
                                     @RequestParam("date") Optional<LocalDateTime> date,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     Model model) {

        LocalDateTime requested = date.isPresent() ? date.get() : LocalDate.now().atStartOfDay();
        String time = requested.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get();
        final int pageSize = (size.orElse(0) < 1) ? INITIAL_SIZE : size.get();

        Page<Schedule> schedulePage = scheduleService.findDoctorSchedule(requested, requested.plusDays(1), false, doctorId, PageRequest.of(currentPage, pageSize));

        String url = String.format("/appointment/%s/schedule?date=%s", doctorId, time);
        PageWrapper<Schedule> schedulePageWrapper = new PageWrapper<>(schedulePage, url);

        model.addAttribute("schedulePage", schedulePage);
        model.addAttribute("page", schedulePageWrapper);
        model.addAttribute("date", time);

        return "views/appointment/appointment-schedule";
    }

    @GetMapping(value = "/appointment/schedule/{scheduleId}/patient")
    public String getPatientForAppointment(@RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size,
                                          @RequestParam("name") Optional<String> name,
                                          @RequestParam("surname") Optional<String> surname,
                                          @RequestParam("identityNumber") Optional<String> identityNumber,
                                           @PathVariable("scheduleId") Optional<Integer> scheduleId,
                                          Model model) {

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get();
        final int pageSize = (size.orElse(0) < 1) ? INITIAL_SIZE : size.get();

        String identity = identityNumber.orElse("");
        String patientName = name.orElse("");
        String patientSurname = surname.orElse("");

        Page<Patient> patientPage = patientService.findByCriteria(identity, patientName, patientSurname, PageRequest.of(currentPage, pageSize));
        String url = String.format("/appointment/schedule/%s/patient?identityNumber=%s&name=%s&surname=%s",scheduleId.get(),identity,patientName,patientSurname);

        PageWrapper<Patient> patientPageWrapper = new PageWrapper<>(patientPage, url);

        model.addAttribute("patientPage", patientPage);
        model.addAttribute("page", patientPageWrapper);
        model.addAttribute("name",patientName);
        model.addAttribute("surname",patientSurname);
        model.addAttribute("identityNumber",identity);

        return "views/appointment/patient-select";
    }
    @GetMapping(value = "/appointment/schedule/{scheduleId}/patient/{patientId}")
    public String createAppointment(@PathVariable("scheduleId") Optional<Integer> scheduleId,
                                    @PathVariable("patientId") Optional<Integer> patientId,
                                    Model model, RedirectAttributes redirectAttributes){

        Appointment appointment = appointmentService.getAppointmentWithId(scheduleId.get(),patientId.get());

        if(!scheduleId.isPresent() || !patientId.isPresent() || appointment !=null){
            redirectAttributes.addFlashAttribute("result","error");
            return "redirect:/appointment";
        }
        appointmentService.save(patientId.get(),scheduleId.get());
        redirectAttributes.addFlashAttribute("result","success");
        return "redirect:/appointment";
    }

    @GetMapping(value = "/appointment/patients")
    public String getPatientAppointments(@RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size,
                                         @RequestParam("name") Optional<String> name,
                                         @RequestParam("surname") Optional<String> surname,
                                         @RequestParam("identityNumber") Optional<String> identityNumber,
                                         @RequestParam("scheduleId") Optional<Integer> scheduleId,
                                         Model model){

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get();
        final int pageSize = (size.orElse(0) < 1) ? INITIAL_SIZE : size.get();

        String identity = identityNumber.orElse("");
        String patientName = name.orElse("");
        String patientSurname = surname.orElse("");

        Page<Appointment> appointmentPage = appointmentService.findPatientAppointments(identity, patientName, patientSurname, PageRequest.of(currentPage, pageSize));
        String url = String.format("/appointment/patients?identityNumber=%s&name=%s&surname=%s",identity,patientName,patientSurname);

        PageWrapper<Appointment> appointmentPageWrapper = new PageWrapper<>(appointmentPage, url);

        model.addAttribute("appointmentPage", appointmentPage);
        model.addAttribute("page", appointmentPageWrapper);
        model.addAttribute("name",patientName);
        model.addAttribute("surname",patientSurname);
        model.addAttribute("identityNumber",identity);

        return "views/appointment/appointment-list";

    }
}
