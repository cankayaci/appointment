package com.myhospital.appointment.model.bindModel.schedule;



import com.myhospital.appointment.validator.IsValidDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

public class BaseScheduleModel {
    @NotNull
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    public Date date;

    @NotNull
    @Min(15)
    public Long appointmentMinutes;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAppointmentMinutes() {
        return appointmentMinutes;
    }

    public void setAppointmentMinutes(Long appointmentMinutes) {
        this.appointmentMinutes = appointmentMinutes;
    }




}
