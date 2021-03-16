package applicationPackage.businessLayer.security;

import applicationPackage.integrationLayer.entities.Employee;
import applicationPackage.integrationLayer.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


   private EmployeeRepository employeeRepository;

    public UserPrincipalDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Employee employee = employeeRepository.findByLogin(username);
      UserPrincipal userPrincipal = new UserPrincipal(employee);
        if (employee == null)
            throw new UsernameNotFoundException("User 404");
        return userPrincipal;

    }
}
