package com.myhospital.appointment.service;

import com.myhospital.appointment.entity.Appointment;
import com.myhospital.appointment.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    void save(int patientId,int scheduleId);
    Appointment getAppointmentWithId(int scheduleId, int patientId);
    Page<Appointment> findPatientAppointments(String identityNumber, String name, String surname, Pageable pageable);
    Long countTotalAppointments();
}
