package com.myhospital.appointment.service.impl;

import com.myhospital.appointment.entity.Department;
import com.myhospital.appointment.repository.DepartmentRepository;
import com.myhospital.appointment.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department findByCode(String code) {
       return departmentRepository.findByCode(code);
    }
}
