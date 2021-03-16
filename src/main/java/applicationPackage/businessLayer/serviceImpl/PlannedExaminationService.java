package applicationPackage.businessLayer.serviceImpl;


import applicationPackage.integrationLayer.DTO.PatientDTO;
import applicationPackage.integrationLayer.DTO.PlannedExaminationDTO;
import applicationPackage.integrationLayer.entities.BuilderEntity;
import applicationPackage.integrationLayer.entities.Device;
import applicationPackage.integrationLayer.entities.Examination;
import applicationPackage.integrationLayer.entities.Patient;
import applicationPackage.integrationLayer.entities.PlannedExamination;
import applicationPackage.integrationLayer.repository.DeviceRepository;
import applicationPackage.integrationLayer.repository.ExaminationRepository;
import applicationPackage.integrationLayer.repository.PatientRepository;
import applicationPackage.integrationLayer.repository.PlannedExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlannedExaminationService implements applicationPackage.businessLayer.service.PlannedExaminationService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ExaminationRepository examinationRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private PlannedExaminationRepository plannedExaminationRepository;
    private BuilderEntity builderEntity = new BuilderEntity();



    @Override
    public int addPlannedExamination(PatientDTO patientDTO, PlannedExaminationDTO plannedExaminationDTO) {
     Patient patient = patientRepository.findByPesel(patientDTO.getPesel());
     if (patient == null)
         return 0;
        // return "Nie znaleziono pacjenta";
     Examination examination = examinationRepository.findByName(plannedExaminationDTO.getExaminationDTO().getName());
        if (examination == null)
            return 1;
            //return "Nie znaleziono badania";
     Device device = deviceRepository.findBySerialNumber(plannedExaminationDTO.getDeviceDTO().getSerialNumber());
        if (device == null)
           return 2;
            // return "Nie znaleziono urządzenia";
     PlannedExamination plannedExamination = builderEntity.createPlannedExaminations(patient,examination,device,plannedExaminationDTO.getDateTimeStart());

     if (device.getPlannedExamination().contains(plannedExamination))
       return 3;
        //return "Termin badania został zarezerwowany przez innego użytkownika, należy wybrać inny termin";
     else if (plannedExaminationDTO.getDateTimeStart().isBefore(LocalDateTime.now()))
     return 4;
         //return "Wybrany termin już minął, należy wybrać inny termin";
     else
     {
         plannedExaminationRepository.save(plannedExamination);
        return 5;
         //return "Poprawnie zarezerwowano badanie";
     }

    }

    @Override
    public void delete(PatientDTO patientDTO, PlannedExaminationDTO chosenDTO) {
        Patient patient = patientRepository.findByPesel(patientDTO.getPesel());
        Device device = deviceRepository.findBySerialNumber(chosenDTO.getDeviceDTO().getSerialNumber());
        PlannedExamination plannedExamination = plannedExaminationRepository.findByPatientAndDateTimeStartAndDevice(patient, chosenDTO.getDateTimeStart(), device);
        plannedExaminationRepository.delete(plannedExamination);
    }
}
