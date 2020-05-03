package com.myhospital.appointment.service.impl;

import com.myhospital.appointment.entity.Patient;
import com.myhospital.appointment.model.bindModel.patient.BasePatientModel;
import com.myhospital.appointment.model.bindModel.patient.EditPatientModel;
import com.myhospital.appointment.repository.PatientRepository;
import com.myhospital.appointment.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<Patient> findPaginated(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    @Override
    public Patient findByIdentityNumber(String identityNumber) {
        return patientRepository.findByIdentityNumber(identityNumber);
    }

    @Override
    public EditPatientModel findById(Integer id) {
        Patient patient = patientRepository.findById(id).get();
        return modelMapper.map(patient, EditPatientModel.class);
    }

    @Override
    public void save(BasePatientModel basePatientModel) {
      Patient patient = modelMapper.map(basePatientModel, Patient.class);
      patientRepository.save(patient);
    }

    @Override
    public Patient findPatientById(Integer id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public Page<Patient> findByCriteria(String identityNumber, String name, String surname,Pageable pageable) {
         Patient patient = new Patient();
         patient.setIdentityNumber(identityNumber);
         patient.setName(name);
         patient.setSurname(surname);
         ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
         Example<Patient> employeeExample = Example.of(patient, matcher);
         return patientRepository.findAll(employeeExample,pageable);
    }

    @Override
    public Long countTotalPatients() {
        return patientRepository.count();
    }
}
