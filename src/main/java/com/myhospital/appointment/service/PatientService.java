package com.myhospital.appointment.service;


import com.myhospital.appointment.entity.Patient;
import com.myhospital.appointment.model.bindModel.patient.BasePatientModel;
import com.myhospital.appointment.model.bindModel.patient.EditPatientModel;
import com.myhospital.appointment.model.bindModel.patient.PatientAddModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    Page<Patient> findPaginated(Pageable pageable);
    Patient findByIdentityNumber(String identityNumber);
    EditPatientModel findById(Integer id);
    void save(BasePatientModel editPatientModel);
    Patient findPatientById(Integer id);
    Page<Patient> findByCriteria(String identityNumber,String name,String surname,Pageable pageable);
    Long countTotalPatients();
}
