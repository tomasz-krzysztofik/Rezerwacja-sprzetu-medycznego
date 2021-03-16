package applicationPackage.businessLayer.service;

import applicationPackage.integrationLayer.entities.Doctor;
import applicationPackage.integrationLayer.entities.Employee;

public interface DoctorService {
     int saveDoctor (Employee employee, String pwzNumber);

    int editDoctor(Employee employee, String pwzNumber);

    void delete(Employee employee);

    Doctor findByPesel(String pesel);
}
