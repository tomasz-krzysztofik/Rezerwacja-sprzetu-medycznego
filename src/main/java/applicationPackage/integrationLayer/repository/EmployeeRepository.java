package applicationPackage.integrationLayer.repository;

import applicationPackage.integrationLayer.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByPesel(String pesel);
    Employee findByLogin(String login);
}
