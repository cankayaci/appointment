package com.myhospital.appointment.repository;

import com.myhospital.appointment.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule,Integer> {
    @Query("select o from Schedule o where o.startTime between :from AND :to AND o.deleted = :deleted AND o.doctor.id = :doctorId")
    Page<Schedule> findDoctorSchedule(LocalDateTime from,LocalDateTime to, Boolean deleted, Integer doctorId, Pageable pageable);
    @Query("select count(*) from Schedule o where o.startTime between :from AND :to AND o.doctor.id = :doctorId")
    long getDoctorDailySchedule(LocalDateTime from,LocalDateTime to,Integer doctorId);

}
