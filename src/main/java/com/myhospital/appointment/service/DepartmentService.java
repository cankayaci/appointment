package com.myhospital.appointment.service;

import com.myhospital.appointment.entity.Department;

public interface DepartmentService {
    Department findByCode(String code);
}
