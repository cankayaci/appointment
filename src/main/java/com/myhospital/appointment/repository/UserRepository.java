package com.myhospital.appointment.repository;

import com.myhospital.appointment.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User,Integer>, QueryByExampleExecutor<User> {
    User findByUsername(String username);
}
