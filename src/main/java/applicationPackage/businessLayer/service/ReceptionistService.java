package applicationPackage.businessLayer.service;


import applicationPackage.integrationLayer.DTO.ExaminationDTO;
import applicationPackage.integrationLayer.DTO.PatientDTO;
import applicationPackage.integrationLayer.DTO.PlannedExaminationDTO;

import java.util.List;

public interface ReceptionistService {

    List<PatientDTO> findAll();

    int savePatient(PatientDTO patientDTO, boolean create);

   int deletePatient(String pesel);

    PatientDTO getUpdateEmployee(String pesel);

    PatientDTO findByPesel(String pesel);

    List<PlannedExaminationDTO> plannedExaminations(String pesel);

    List<ExaminationDTO> findAllExaminationsForActiveDevices();

    List<String> findAllTypesForPossibleExaminations(List<ExaminationDTO> examinationsDTO);

    String findEmployee(String pesel);
}