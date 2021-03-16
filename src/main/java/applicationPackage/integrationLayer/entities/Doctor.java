package applicationPackage.integrationLayer.entities;
import javax.persistence.*;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doctor_id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employeeData;

    private String numberPwz;

    public Doctor() {
    }

    public Doctor(long id, Employee employeeData, String pwz) {
        this.id = id;
        this.employeeData = employeeData;
        this.numberPwz = pwz;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(Employee employeeData) {
        this.employeeData = employeeData;
    }

    public String getNumberPwz() {
        return numberPwz;
    }

    public void setNumberPwz(String numberPwz) {
        this.numberPwz = numberPwz;
    }


}
