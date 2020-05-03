package com.myhospital.appointment.service;

import com.myhospital.appointment.entity.User;
import com.myhospital.appointment.model.bindModel.user.BaseUserModel;
import com.myhospital.appointment.model.bindModel.user.ChangePasswordModel;
import com.myhospital.appointment.model.bindModel.user.EditUserModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService{
    void save(BaseUserModel user);
    User findByUsername(String username);
    Page<User> findPaginated(Pageable pageable);
    EditUserModel findById(Integer id);
    Page<User> findByCriteria(String username, String email,Pageable pageable);
    Long countTotalUsers();
    Boolean updateUserPassword(ChangePasswordModel changePasswordModel);
}
