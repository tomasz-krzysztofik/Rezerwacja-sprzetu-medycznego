package applicationPackage.businessLayer.service;


import applicationPackage.integrationLayer.DTO.EmployeeDTO;

import java.util.List;

public interface AdminService {
    List<EmployeeDTO> findAll();

    int saveEmployee(EmployeeDTO employeeDTO, boolean b);

    void deleteEmployee(String pesel);

    EmployeeDTO getUpdateEmployee(String pesel);

    String findPatient(String pesel);
}
