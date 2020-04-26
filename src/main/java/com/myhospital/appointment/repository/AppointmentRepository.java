package com.myhospital.appointment.repository;

import com.myhospital.appointment.entity.Appointment;
import com.myhospital.appointment.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.time.LocalDateTime;

public interface AppointmentRepository extends PagingAndSortingRepository<Appointment,Integer>, QueryByExampleExecutor<Appointment> {
    @Query("select o from Appointment o where o.schedule.id = :scheduleId AND o.patient.id = :patientId")
    Appointment findAppointmentByIds(Integer scheduleId,Integer patientId);
}
