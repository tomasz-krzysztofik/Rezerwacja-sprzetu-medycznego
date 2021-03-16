package applicationPackage.integrationLayer.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name="patients")
public class Patient {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="patient_id")
	private long id;
	
	@NotNull
	private String name;
	@NotNull
	private String surname;

	@NotNull
	private String sex;

	@NotNull
	private String address;

	private String plenipotentiary;

	@Column(name="PESEL")
	@NotNull
	private String pesel;
	@OneToMany(mappedBy = "patient", cascade = {
			CascadeType.REFRESH,
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST})
	private List<PlannedExamination> plannedExaminations;
	public Patient() {
		super();
	}
	
	public Patient(long id, String name, String surname, String pesel, String sex, String address, String plenipotentiary) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
		this.sex = sex;
		this.address = address;
		this.plenipotentiary = plenipotentiary;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPesel() {
		return pesel;
	}
	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlenipotentiary() {
		return plenipotentiary;
	}

	public void setPlenipotentiary(String plenipotentiary) {
		this.plenipotentiary = plenipotentiary;
	}

	public List<PlannedExamination> getPlannedExaminations() {
		return plannedExaminations;
	}

	public void setPlannedExaminations(List<PlannedExamination> plannedExaminations) {
		this.plannedExaminations = plannedExaminations;
	}
}
