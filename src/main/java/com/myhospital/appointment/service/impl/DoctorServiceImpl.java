package com.myhospital.appointment.service.impl;

import com.myhospital.appointment.entity.Department;
import com.myhospital.appointment.entity.Doctor;
import com.myhospital.appointment.entity.Patient;
import com.myhospital.appointment.model.bindModel.doctor.AddDoctorModel;
import com.myhospital.appointment.model.bindModel.doctor.BaseDoctorModel;
import com.myhospital.appointment.model.bindModel.doctor.EditDoctorModel;
import com.myhospital.appointment.repository.DoctorRepository;
import com.myhospital.appointment.service.DepartmentService;
import com.myhospital.appointment.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.print.Doc;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DepartmentService departmentService;

    @Override
    public void save(BaseDoctorModel baseDoctorModel) {

        Department department = departmentService.findByCode(baseDoctorModel.getDepartment());
        Doctor doctor = this.modelMapper.map(baseDoctorModel, Doctor.class);
        doctor.setDepartment(department);
        doctorRepository.save(doctor);
    }

    @Override
    public Page<Doctor> findPaginated(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }

    @Override
    public EditDoctorModel findById(Integer id) {
        Doctor doctor = doctorRepository.findById(id).get();
        return modelMapper.map(doctor, EditDoctorModel.class);
    }

    @Override
    public Doctor findDoctorById(Integer id) {
        return doctorRepository.findById(id).get();
    }

    @Override
    public Page<Doctor> findDoctorsByCode(Pageable pageable, String departmentCode) {
        Department department = departmentService.findByCode(departmentCode);
        Doctor doctor = new Doctor();
        doctor.setDepartment(department);
        
        Example<Doctor> doctorExample = Example.of(doctor, ExampleMatcher.matchingAll());
        return doctorRepository.findAll(doctorExample, pageable);

    }

    @Override
    public Page<Doctor> findByCriteria(String department, String name, String surname, Pageable pageable) {
        Department dept = departmentService.findByCode(department);
        Doctor doctor = new Doctor();
        doctor.setDepartment(dept);
        doctor.setName(name);
        doctor.setSurname(surname);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Doctor> employeeExample = Example.of(doctor, matcher);
        return doctorRepository.findAll(employeeExample,pageable);
    }

    @Override
    public Long countTotalDoctors() {
        return doctorRepository.count();
    }
}
