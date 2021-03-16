package applicationPackage.businessLayer.serviceImpl;


import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.integrationLayer.DTO.*;
import applicationPackage.integrationLayer.entities.BuilderEntity;
import applicationPackage.integrationLayer.entities.*;
import applicationPackage.integrationLayer.repository.DeviceRepository;
import applicationPackage.integrationLayer.repository.EmployeeRepository;
import applicationPackage.integrationLayer.repository.PatientRepository;
import applicationPackage.businessLayer.service.ReceptionistService;
import applicationPackage.businessLayer.utilityClasses.SortByDate;
import applicationPackage.businessLayer.utilityClasses.SortBySurname;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceptionistServiceImpl implements ReceptionistService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    private BuilderEntity builderEntity = new BuilderEntity();
    private ModelMapper modelMapper = new ModelMapper();



    public List<PatientDTO> findAll() {
        List<Patient> allPatients = patientRepository.findAll();
        allPatients.sort(new SortBySurname());
        return CommonMethods.getAllElements(allPatients, new PatientDTO());
    }

    @Override
    public int savePatient(PatientDTO patientDTO, boolean create) {
        if(checkPesel(patientDTO.getPesel()) == false)
            return 3;
        Employee employeeFromDB = employeeRepository.findByPesel(patientDTO.getPesel());
        Patient patient = patientRepository.findByPesel(patientDTO.getPesel());
        if (patient == null && create) {
            Patient givenPatient = modelMapper.map(patientDTO, Patient.class);
            if (employeeFromDB != null) {
                patientRepository.save(builderEntity.createPatientEmployee(givenPatient, employeeFromDB));
                return 9;
            }
            patientRepository.save(givenPatient);
            return 1;
        } else if (patient != null && !create) {
            if (employeeFromDB == null) {
                patientRepository.save(builderEntity.editPatient(patient, patientDTO));
                return 2;
            } else
                return 8;
        }
        return 0;

    }

    private boolean checkPesel(String pesel) {
            int a = Integer.parseInt(String.valueOf(pesel.charAt(0)));
            int b = Integer.parseInt(String.valueOf(pesel.charAt(1)));
            int c = Integer.parseInt(String.valueOf(pesel.charAt(2)));
            int d = Integer.parseInt(String.valueOf(pesel.charAt(3)));
            int e = Integer.parseInt(String.valueOf(pesel.charAt(4)));
            int f = Integer.parseInt(String.valueOf(pesel.charAt(5)));
            int g = Integer.parseInt(String.valueOf(pesel.charAt(6)));
            int h = Integer.parseInt(String.valueOf(pesel.charAt(7)));
            int i = Integer.parseInt(String.valueOf(pesel.charAt(8)));
            int j = Integer.parseInt(String.valueOf(pesel.charAt(9)));
            int control = Integer.parseInt(String.valueOf(pesel.charAt(10)));

            int sum = 9 * a + 7 * b + 3 * c + 1 * d + 9 * e + 7 * f + 3 * g + 1 * h + 9 * i + 7 * j;
            if (sum%10 == control)
                return true;
            else
                return false;

    }

    @Override
    public String findEmployee(String pesel) {
        if (employeeRepository.findByPesel(pesel) == null)
            return "brak";
        else
            return "istnieje";
    }

    @Override
    public int deletePatient(String pesel) {
        Patient patient = patientRepository.findByPesel(pesel);
        try{
            patientRepository.delete(patient);
            return 6;
        } catch (DataIntegrityViolationException e){
            return 0;
        }
    }

    @Override
    public PatientDTO getUpdateEmployee(String pesel) {
        Employee employee = employeeRepository.findByPesel(pesel);
        if (employee != null)
            return null;

        return modelMapper.map(patientRepository.findByPesel(pesel), PatientDTO.class);
    }

    @Override
    public PatientDTO findByPesel(String pesel) {
        Patient patient = patientRepository.findByPesel(pesel);
        if (patient != null)
            return modelMapper.map(patient, PatientDTO.class);
        return null;
    }

    @Override
    public List<PlannedExaminationDTO> plannedExaminations(String pesel) {
        Patient patient = patientRepository.findByPesel(pesel);
        List<PlannedExamination> plannedExaminations = patient.getPlannedExaminations();
        List<PlannedExaminationDTO> plannedExaminationDTO = new ArrayList<>();

        if (plannedExaminations != null) {
            for (PlannedExamination examination : plannedExaminations) {
                DeviceDTO deviceDto = modelMapper.map(examination.getDevice(), DeviceDTO.class);
                ExaminationDTO examinationDTO = modelMapper.map(examination.getExamination(), ExaminationDTO.class);
                PatientDTO patientDTO2 = modelMapper.map(examination.getPatient(), PatientDTO.class);
                plannedExaminationDTO.add(new PlannedExaminationDTO(examination.getDateTimeStart(), patientDTO2, examinationDTO, deviceDto));
            }
            plannedExaminationDTO.sort(new SortByDate());
            return plannedExaminationDTO;
        }
        return null;
    }

    @Override
    public List<ExaminationDTO> findAllExaminationsForActiveDevices() {
        List<Device> devices = deviceRepository.findAllByDeviceActivated((byte) 1);
        List<ExaminationDTO> activeExaminations = new ArrayList<>();
        for (Device device : devices) {
            List<Examination> examinations = device.getExaminationsList();
            for (Examination examination : examinations) {
                ExaminationDTO ex = modelMapper.map(examination, ExaminationDTO.class);
                if (!activeExaminations.contains(ex)) {
                    ex.setExaminationTypeDto(modelMapper.map(examination.getExaminationType(), ExaminationTypeDTO.class));
                    activeExaminations.add(ex);
                }
            }
        }
        return activeExaminations;
    }

    //ta metoda zwraca tylko typy
    @Override
    public List<String> findAllTypesForPossibleExaminations(List<ExaminationDTO> examinationsDTO) {
        List<String> strings = new ArrayList<>();
        if (!examinationsDTO.isEmpty()){
            for (ExaminationDTO ex : examinationsDTO)
                if (!strings.contains(ex.getExaminationTypeDto().getName()))
                    strings.add(ex.getExaminationTypeDto().getName());
    }
        else
            strings.add("Brak aktywnych urządzeń, powiadom technika");
        return strings;
    }



}
