package com.myhospital.appointment.service.impl;

import com.myhospital.appointment.entity.Patient;
import com.myhospital.appointment.entity.Role;
import com.myhospital.appointment.entity.User;
import com.myhospital.appointment.model.bindModel.user.BaseUserModel;
import com.myhospital.appointment.model.bindModel.user.ChangePasswordModel;
import com.myhospital.appointment.model.bindModel.user.EditUserModel;
import com.myhospital.appointment.model.bindModel.user.UserAddModel;
import com.myhospital.appointment.model.viewModel.user.UserListModel;
import com.myhospital.appointment.repository.UserRepository;
import com.myhospital.appointment.service.RoleService;
import com.myhospital.appointment.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public void save(BaseUserModel baseUserModel) {
        User user = this.modelMapper.map(baseUserModel,User.class);
        Role role = roleService.findByName(baseUserModel.getRoleName());
        String pwd = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pwd);
        user.setRoles(Stream.of(role)
                .collect(Collectors.toCollection(HashSet::new)));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return  userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public EditUserModel findById(Integer id) {
        User user = userRepository.findById(id).get();
        return modelMapper.map(user,EditUserModel.class);
    }

    @Override
    public Page<User> findByCriteria(String username, String email, Pageable pageable) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> employeeExample = Example.of(user, matcher);
        return userRepository.findAll(employeeExample,pageable);
    }

    @Override
    public Long countTotalUsers() {
        return userRepository.count();
    }

    @Override
    public Boolean updateUserPassword(ChangePasswordModel editUserModel) {

        User user = this.userRepository.findByUsername(editUserModel.getUsername());
        String encryptedPassword = this.bCryptPasswordEncoder.encode(editUserModel.getPassword());
        if (!this.bCryptPasswordEncoder.matches(editUserModel.getOldPassword(),
                user.getPassword())) {
            return false;
        }
        user.setPassword(encryptedPassword);

        this.userRepository.save(user);

        return true;
    }
}
