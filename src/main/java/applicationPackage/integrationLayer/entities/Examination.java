package applicationPackage.integrationLayer.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="examinations")
public class Examination {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="examination_id")
    private long id;

    @NotNull
    private String name;

    private String description;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="type_id", nullable = false)
    private ExaminationType examinationType;

    @ManyToMany (cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST})
    @JoinTable(name="examinations_devices",
            inverseJoinColumns = @JoinColumn(name="examination_id"),
            joinColumns = @JoinColumn(name="device_id"))
    private List<Device> deviceList;

    @OneToMany(mappedBy = "examination", cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST})
    private List<PlannedExamination> plannedExaminations;



    public Examination() {
    }


    public Examination(long id, String name, String description, ExaminationType examinationType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.examinationType=examinationType;
    }

    public Examination(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExaminationType getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(ExaminationType examinationType) {
        this.examinationType = examinationType;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public List<PlannedExamination> getPlannedExaminations() {
        return plannedExaminations;
    }

    public void setPlannedExaminations(List<PlannedExamination> plannedExaminations) {
        this.plannedExaminations = plannedExaminations;
    }

    @Override
    public boolean equals(Object o) {
        return (getName().equals(((Examination) o).getName()));
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }


}
