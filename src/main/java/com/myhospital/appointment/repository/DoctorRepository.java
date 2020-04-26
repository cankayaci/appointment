package com.myhospital.appointment.repository;

import com.myhospital.appointment.entity.Doctor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface DoctorRepository extends PagingAndSortingRepository<Doctor,Integer>, QueryByExampleExecutor<Doctor> {
}
