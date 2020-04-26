package com.myhospital.appointment.repository;

import com.myhospital.appointment.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    Department findByCode(String code);
}
