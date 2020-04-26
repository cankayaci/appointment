package com.myhospital.appointment.model.bindModel.schedule;

import com.myhospital.appointment.entity.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddScheduleModel extends BaseScheduleModel{

    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

}
