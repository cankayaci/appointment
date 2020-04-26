package com.myhospital.appointment.service.impl;

import com.myhospital.appointment.entity.Doctor;
import com.myhospital.appointment.entity.Schedule;
import com.myhospital.appointment.model.bindModel.schedule.AddScheduleModel;
import com.myhospital.appointment.repository.ScheduleRepository;
import com.myhospital.appointment.service.DoctorService;
import com.myhospital.appointment.service.ScheduleService;
import com.myhospital.appointment.util.WorkingHourAdjuster;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.next;
import static java.time.DayOfWeek.SATURDAY;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    public static final int START_WORKING_HOUR = 9;
    public static final int START_LUNCH_HOUR = 12;
    public static final int END_WORKING_HOUR = 17;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Boolean save(AddScheduleModel addScheduleModel, int doctorId) {

        Doctor doctor = doctorService.findDoctorById(doctorId);

        List<Schedule> schedules = new ArrayList<>();
        final LocalDateTime startDateTime = LocalDateTime.ofInstant(addScheduleModel.getDate().toInstant(), ZoneId.systemDefault())
                .with(WorkingHourAdjuster.startHour(START_WORKING_HOUR));
        final LocalDateTime end = startDateTime.with(WorkingHourAdjuster.endHour(END_WORKING_HOUR));

        long count = getDoctorDailySchedule(startDateTime,startDateTime.plusDays(1),doctorId);
        if(count > 0 ) {
            return false;
        }
        for (LocalDateTime start = startDateTime; start.isBefore(end); ) {

            if (start.getHour() != START_LUNCH_HOUR) {
                LocalDateTime nextSlot = start.plusMinutes(addScheduleModel.getAppointmentMinutes());

                Schedule schedule = new Schedule();
                schedule.setDeleted(false);
                schedule.setEndTime(nextSlot);
                schedule.setStartTime(start);
                schedule.setDoctor(doctor);
                schedules.add(schedule);

                start = nextSlot;
            } else {
                start = start.plusHours(1);
            }

        }
        if (!schedules.isEmpty()) {
            scheduleRepository.saveAll(schedules);
        }
        return true;
    }

    @Override
    public Schedule findById(Integer id) {
        return scheduleRepository.findById(id).get();
    }

    @Override
    public Page<Schedule> findDoctorSchedule(LocalDateTime from, LocalDateTime to, Boolean deleted, Integer doctorId, Pageable pageable) {
        return scheduleRepository.findDoctorSchedule(from,to,deleted,doctorId,pageable);
    }

    @Override
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public long getDoctorDailySchedule(LocalDateTime from, LocalDateTime to, Integer doctorId) {
        return scheduleRepository.getDoctorDailySchedule(from,to,doctorId);
    }
}
