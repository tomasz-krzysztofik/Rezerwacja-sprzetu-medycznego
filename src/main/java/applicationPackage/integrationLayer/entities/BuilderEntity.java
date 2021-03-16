package applicationPackage.integrationLayer.entities;

import applicationPackage.integrationLayer.DTO.DeviceDTO;
import applicationPackage.integrationLayer.DTO.EmployeeDTO;
import applicationPackage.integrationLayer.DTO.PatientDTO;
import applicationPackage.integrationLayer.entities.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class BuilderEntity {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public BuilderEntity() {

    }

    public Patient createPatientEmployee(Patient patient, Employee employee){
        patient.setName(employee.getName());
        patient.setSurname(employee.getSurname());
        patient.setPesel(employee.getPesel());
        return patient;
    }

    public Patient editPatient(Patient patient, PatientDTO givenPatient){
        patient.setName(givenPatient.getName());
        patient.setSurname(givenPatient.getSurname());
        patient.setSex(givenPatient.getSex());
        patient.setAddress(givenPatient.getAddress());
        patient.setPlenipotentiary(givenPatient.getPlenipotentiary());
        return patient;
    }

    public PlannedExamination createPlannedExaminations(Patient patient, Examination examination, Device device, LocalDateTime dateTime){
        PlannedExamination plannedExamination = new PlannedExamination();
        plannedExamination.setPatient(patient);
        plannedExamination.setExamination(examination);
        plannedExamination.setDevice(device);
        plannedExamination.setDateTimeStart(dateTime);
        return plannedExamination;
    }

    public Examination editExamination(Examination examination, Examination givenExamination){
        examination.setDescription(givenExamination.getDescription());
        examination.setExaminationType(givenExamination.getExaminationType());
        return examination;
    }

    public Device editDevice(Device device, DeviceDTO deviceDTO){
        device.setName(deviceDTO.getName());
        device.setModel(deviceDTO.getModel());
        device.setProductionYear(deviceDTO.getProductionYear());
        device.setProducer(deviceDTO.getProducer());
        device.setMainService(deviceDTO.getMainService());
        device.setInstallationDate(deviceDTO.getInstallationDate());
        device.setMaintenanceDate(deviceDTO.getMaintenanceDate());
        device.setRoom(deviceDTO.getRoom());
        return device;
    }

    public Employee createEmployeePatient(Employee givenEmployee, Patient patient){
        givenEmployee.setName(patient.getName());
        givenEmployee.setSurname(patient.getSurname());
        return givenEmployee;
    }

    public Employee editEmployee(Employee employee, EmployeeDTO givenEmployee){
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        employee.setName(givenEmployee.getName());
        employee.setSurname(givenEmployee.getSurname());
        employee.setWorkplace(givenEmployee.getWorkplace());
        employee.setPassword(bCryptPasswordEncoder.encode(givenEmployee.getPassword()));
        employee = setRole(employee);

        return employee;
    }

    public Doctor createDoctor(Doctor doctor, Employee employee, String pwzNumber){
        doctor.setEmployeeData(employee);
        doctor.setNumberPwz(pwzNumber);
        return doctor;
    }
    public Doctor editDoctor(Doctor doctor, Employee employee, String pwzNumber){
        doctor.setEmployeeData(employee);
        doctor.setNumberPwz(pwzNumber);
        return doctor;
    }

    public Employee setRole(Employee givenEmployee) {

        switch (givenEmployee.getWorkplace()){
            case "Lekarz":
                givenEmployee.setRole("DOCTOR");
                break;
            case "Rejestrator":
                givenEmployee.setRole("RECEPTIONIST");
                break;
            case "Technik":
                givenEmployee.setRole("TECHNICIAN");
                break;
            case "Administrator":
                givenEmployee.setRole("ADMIN");
                break;
    }
    return givenEmployee;
}
}
