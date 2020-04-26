package com.myhospital.appointment.service;

import com.myhospital.appointment.entity.Schedule;
import com.myhospital.appointment.model.bindModel.schedule.AddScheduleModel;
import com.myhospital.appointment.model.bindModel.schedule.BaseScheduleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ScheduleService {
    Boolean save(AddScheduleModel addScheduleModel,int doctorId);
    Schedule findById(Integer id);
    Page<Schedule> findDoctorSchedule(LocalDateTime from, LocalDateTime to, Boolean deleted, Integer doctorId, Pageable pageable);
    void save(Schedule schedule);
    long getDoctorDailySchedule(LocalDateTime from,LocalDateTime to,Integer doctorId);
}
