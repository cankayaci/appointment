package com.myhospital.appointment.service.impl;

import com.myhospital.appointment.entity.Appointment;
import com.myhospital.appointment.entity.Patient;
import com.myhospital.appointment.entity.Schedule;
import com.myhospital.appointment.repository.AppointmentRepository;
import com.myhospital.appointment.service.AppointmentService;
import com.myhospital.appointment.service.PatientService;
import com.myhospital.appointment.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ScheduleService scheduleService;

    @Override
    public void save(int patientId, int scheduleId) {

        Schedule schedule = scheduleService.findById(scheduleId);
        Patient patient = patientService.findPatientById(patientId);

        Appointment appointment = new Appointment();
        appointment.setSchedule(schedule);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);

        schedule.setDeleted(true);
        scheduleService.save(schedule);
    }

    @Override
    public Appointment getAppointmentWithId(int scheduleId, int patientId) {
        return appointmentRepository.findAppointmentByIds(scheduleId,patientId);
    }

    @Override
    public Page<Appointment> findPatientAppointments(String identityNumber, String name, String surname, Pageable pageable) {
        Appointment appointment = new Appointment();

        Patient patient = new Patient();
        patient.setIdentityNumber(identityNumber);
        patient.setName(name);
        patient.setSurname(surname);
        appointment.setPatient(patient);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Appointment> employeeExample = Example.of(appointment, matcher);
        return appointmentRepository.findAll(employeeExample,pageable);
    }

    @Override
    public Long countTotalAppointments() {
        return appointmentRepository.count();
    }
}
