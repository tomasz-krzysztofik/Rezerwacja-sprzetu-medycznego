package applicationPackage.businessLayer.serviceImpl;

import applicationPackage.integrationLayer.DTO.DoctorDTO;
import applicationPackage.integrationLayer.entities.BuilderEntity;
import applicationPackage.integrationLayer.entities.Doctor;
import applicationPackage.integrationLayer.entities.Employee;
import applicationPackage.integrationLayer.repository.DoctorRepository;
import applicationPackage.integrationLayer.repository.EmployeeRepository;
import applicationPackage.businessLayer.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
private ModelMapper modelMapper = new ModelMapper();
@Autowired
    private DoctorRepository doctorRepository;
@Autowired
private EmployeeRepository employeeRepository;
private BuilderEntity builderEntity = new BuilderEntity();

    @Override
    public int saveDoctor(Employee employee, String pwzNumber) {
        Doctor givenDoctor = modelMapper.map(new DoctorDTO(pwzNumber), Doctor.class);
        Doctor doctor = doctorRepository.findByNumberPwz(pwzNumber);
        if (doctor==null){
            doctorRepository.save(builderEntity.createDoctor(givenDoctor, employee, pwzNumber));
            return 1;
        }        else return 4;
    }

    @Override
    public int editDoctor(Employee employee, String pwzNumber){
        Doctor doctor =  findByPesel(employee.getPesel());
        if (doctor==null){
            doctorRepository.save(builderEntity.createDoctor(new Doctor(), employee, pwzNumber));
            return 1;
        } else{
            Doctor doctorFromDB = doctorRepository.findByNumberPwz(pwzNumber);
            if (doctorFromDB == null || doctorFromDB.getEmployeeData().getPesel() == employee.getPesel()){
            doctorRepository.save(builderEntity.editDoctor(doctor, employee, pwzNumber));
                return 1;}
            else
                return 4;
        }
    }
    @Override
    public void delete(Employee employee) {

        for (Doctor doctor: doctorRepository.findAll()) {
            if (doctor.getEmployeeData().getPesel().equals(employee.getPesel()))
                doctorRepository.delete(doctor);
        }

    }

    @Override
    public Doctor findByPesel(String pesel) {
        List<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doctor: doctors) {
            if (doctor.getEmployeeData().getPesel().equals(pesel))
                return doctor;
        }
        return null;
    }


}
