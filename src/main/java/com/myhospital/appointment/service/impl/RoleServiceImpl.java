package com.myhospital.appointment.service.impl;

import com.myhospital.appointment.entity.Role;
import com.myhospital.appointment.repository.RoleRepository;
import com.myhospital.appointment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
