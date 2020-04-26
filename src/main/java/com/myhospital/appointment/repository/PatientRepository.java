package com.myhospital.appointment.repository;

import com.myhospital.appointment.entity.Patient;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface PatientRepository extends PagingAndSortingRepository<Patient,Integer>, QueryByExampleExecutor<Patient> {
    Patient findByIdentityNumber(String identityNumber);
}
