package applicationPackage.integrationLayer.repository;

import applicationPackage.integrationLayer.entities.Doctor;
import applicationPackage.integrationLayer.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByNumberPwz(String numberPwz);
    Doctor findByEmployeeData(Employee employee);

}
