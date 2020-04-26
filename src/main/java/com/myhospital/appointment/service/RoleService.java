package com.myhospital.appointment.service;

import com.myhospital.appointment.entity.Role;

public interface RoleService {
    Role findByName(String name);
}
