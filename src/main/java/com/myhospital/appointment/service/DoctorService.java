package com.myhospital.appointment.service;

import com.myhospital.appointment.entity.Doctor;
import com.myhospital.appointment.model.bindModel.doctor.BaseDoctorModel;
import com.myhospital.appointment.model.bindModel.doctor.EditDoctorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    void save(BaseDoctorModel patient);
    Page<Doctor> findPaginated(Pageable pageable);
    EditDoctorModel findById(Integer id);
    Doctor findDoctorById(Integer id);
    Page<Doctor> findDoctorsByCode(Pageable pageable,String departmentCode);
    Page<Doctor> findByCriteria(String department,String name,String surname,Pageable pageable);
    Long countTotalDoctors();
}
