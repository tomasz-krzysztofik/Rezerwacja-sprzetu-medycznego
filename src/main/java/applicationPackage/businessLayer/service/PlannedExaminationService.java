package applicationPackage.businessLayer.service;

import applicationPackage.integrationLayer.DTO.PatientDTO;
import applicationPackage.integrationLayer.DTO.PlannedExaminationDTO;

public interface PlannedExaminationService {

    int addPlannedExamination(PatientDTO patientDTO, PlannedExaminationDTO plannedExaminationDTO);

    void delete(PatientDTO patientDTO, PlannedExaminationDTO chosenDTO);
}
