package applicationPackage.integrationLayer.DTO;

public class DoctorDTO {

    private String pwz;
    private EmployeeDTO employeeData;
    public DoctorDTO() {
    }

    public DoctorDTO(String pwz, EmployeeDTO employeeData) {
        this.pwz = pwz;
    }

    public DoctorDTO(String pwz) {
        this.pwz = pwz;
    }

    public String getPwz() {
        return pwz;
    }

    public void setPwz(String pwz) {
        this.pwz = pwz;
    }

    public EmployeeDTO getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(EmployeeDTO employeeData) {
        this.employeeData = employeeData;
    }

}
