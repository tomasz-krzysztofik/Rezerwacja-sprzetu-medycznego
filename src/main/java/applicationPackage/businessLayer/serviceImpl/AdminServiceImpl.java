package applicationPackage.businessLayer.serviceImpl;


import applicationPackage.businessLayer.service.AdminService;
import applicationPackage.businessLayer.service.DoctorService;
import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.integrationLayer.DTO.EmployeeDTO;
import applicationPackage.integrationLayer.entities.BuilderEntity;
import applicationPackage.integrationLayer.entities.Doctor;
import applicationPackage.integrationLayer.entities.Employee;
import applicationPackage.integrationLayer.entities.Patient;
import applicationPackage.integrationLayer.repository.DoctorRepository;
import applicationPackage.integrationLayer.repository.EmployeeRepository;
import applicationPackage.integrationLayer.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorService doctorService;
    private ModelMapper modelMapper = new ModelMapper();
    private BuilderEntity builderEntity = new BuilderEntity();


    @Override
    public List<EmployeeDTO> findAll() {
        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> allEmployeesWd = new ArrayList<>();
        for (int i = 1; i < allEmployees.size(); i++) {
            Employee e = allEmployees.get(i);


            if (checkPesel(e.getPesel()))
            if (!e.getWorkplace().equals("Lekarz"))
                allEmployeesWd.add(e);
        }
        List<Doctor> doctors = doctorRepository.findAll();
        List<EmployeeDTO> allDTOs2 = CommonMethods.getAllElements(allEmployeesWd, new EmployeeDTO());
        for (Doctor d : doctors) {
            EmployeeDTO edto = modelMapper.map(d.getEmployeeData(), EmployeeDTO.class);
            edto.setPwzNumber(d.getNumberPwz());
            allDTOs2.add(edto);
        }

        return allDTOs2;
    }


    @Override
    public int saveEmployee(EmployeeDTO employeeDTO, boolean createEmployee) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Employee givenEmployee = modelMapper.map(employeeDTO, Employee.class);
        Employee employee = employeeRepository.findByPesel(employeeDTO.getPesel());
        Patient patientFromDB = patientRepository.findByPesel(employeeDTO.getPesel());


        if (!checkPassword(givenEmployee.getPassword()) || givenEmployee.getPassword().length() < 8)
            return 8;
        if(!checkPesel(givenEmployee.getPesel()))
            return 9;

        givenEmployee.setPassword(bCryptPasswordEncoder.encode(givenEmployee.getPassword()));
        givenEmployee = builderEntity.setRole(givenEmployee);
        if (employee == null && checkLoginFree(employeeDTO) && createEmployee) {
            if (patientFromDB != null) {
                givenEmployee = builderEntity.createEmployeePatient(givenEmployee, patientFromDB);
                String pwzNumber = employeeDTO.getPwzNumber();
                if (pwzNumber != null) {
                    return doctorService.saveDoctor(givenEmployee, pwzNumber);
                }
                employeeRepository.save(givenEmployee);
                return 5;
            }
            String pwzNumber = employeeDTO.getPwzNumber();
            if (pwzNumber != null) {
                return doctorService.saveDoctor(givenEmployee, pwzNumber);
            }

            employeeRepository.save(givenEmployee);
            return 1;
        } else if (employee != null && !createEmployee) {
            employee = builderEntity.editEmployee(employee, employeeDTO);
            if (!givenEmployee.getWorkplace().equals("Lekarz")) {
                if (doctorService.findByPesel(employee.getPesel()) != null)
                    doctorService.delete(employee);
                employeeRepository.save(employee);
                return 1;
            } else {
                return doctorService.editDoctor(employee, employeeDTO.getPwzNumber());
            }


        }
        return 0;
    }

    private boolean checkPesel(String pesel) {
        char[] chars = pesel.toCharArray();
        int sum = 1 * Character.getNumericValue(chars[0]) +
                  3 * Character.getNumericValue(chars[1]) +
                  7 * Character.getNumericValue(chars[2]) +
                  9 * Character.getNumericValue(chars[3]) +
                  1 * Character.getNumericValue(chars[4]) +
                  3 * Character.getNumericValue(chars[5]) +
                  7 * Character.getNumericValue(chars[6]) +
                  9 * Character.getNumericValue(chars[7]) +
                  1 * Character.getNumericValue(chars[8]) +
                  3 * Character.getNumericValue(chars[9]);


        System.out.println(sum);
        sum%= 10;
        sum = 10 - sum;
        sum %= 10;

        if (sum == Character.getNumericValue(chars[10]))
            return true;
        else
            return false;
    }

    private boolean checkPassword(String password) {
        String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
        char currentCharacter;
        boolean numberPresent = false;
        boolean upperCasePresent = false;
        boolean lowerCasePresent = false;
        boolean specialCharacterPresent = false;

        for (int i = 0; i < password.length(); i++) {
            currentCharacter = password.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                numberPresent = true;
            } else if (Character.isUpperCase(currentCharacter)) {
                upperCasePresent = true;
            } else if (Character.isLowerCase(currentCharacter)) {
                lowerCasePresent = true;
            } else if (specialChars.contains(String.valueOf(currentCharacter))) {
                specialCharacterPresent = true;
            }
        }

        return numberPresent && upperCasePresent && lowerCasePresent && specialCharacterPresent;
    }

    @Override
    public void deleteEmployee(String pesel) {
        Employee employee = employeeRepository.findByPesel(pesel);
        if (employee != null) {
            doctorService.delete(employee);
            employeeRepository.delete(employee);
        }
    }

    @Override
    public EmployeeDTO getUpdateEmployee(String pesel) {

        Employee employee = employeeRepository.findByPesel(pesel);
        employee.setPassword("");
        System.out.println(employee.getRole());
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public String findPatient(String pesel) {
        Patient patient = patientRepository.findByPesel(pesel);

        if (patient == null)
            return "lack";
        else
            return "exists";
    }


    private boolean checkLoginFree(EmployeeDTO employeeDTO) {
        Employee foundWithGivenLogin = employeeRepository.findByLogin(employeeDTO.getLogin());
        return foundWithGivenLogin == null;

    }

}
