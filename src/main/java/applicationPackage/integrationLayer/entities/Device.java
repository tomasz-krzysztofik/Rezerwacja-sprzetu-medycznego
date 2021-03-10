package applicationPackage.integrationLayer.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "device_id")
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String model;
    @NotNull
    private int productionYear;
    @NotNull
    private String producer;
    @NotNull
    private String serialNumber;
    @NotNull
    private String mainService;
    @NotNull
    private LocalDate installationDate;
    private LocalDate maintenanceDate;
    private String room;
    @NotNull
    private byte deviceActivated;

    @OneToMany(mappedBy = "device", cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST})
    private List<PlannedExamination> plannedExamination;


    @ManyToMany(cascade = {
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST}, targetEntity = Examination.class)
    @JoinTable(name="examinations_devices",
    joinColumns = @JoinColumn(name="examination_id"),
    inverseJoinColumns = @JoinColumn(name="device_id"))
    private List<Examination> examinationsList;

    public Device() {
    }
    public Device(long id, String name, String model, int productionYear, String producer, String serialNumber, String mainService, LocalDate maintenanceDate, LocalDate installationDate) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.productionYear = productionYear;
        this.producer = producer;
        this.serialNumber = serialNumber;
        this.mainService = mainService;
        this.maintenanceDate = maintenanceDate;
        this.installationDate = installationDate;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMainService() {
        return mainService;
    }

    public void setMainService(String mainService) {
        this.mainService = mainService;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }


    public byte getDeviceActivated() {
        return deviceActivated;
    }

    public void setDeviceActivated(byte deviceActivated) {
        this.deviceActivated = deviceActivated;
    }

    public List<Examination> getExaminationsList() {
        return examinationsList;
    }

    public void setExaminationsList(List<Examination> examinationsList) {
        this.examinationsList = examinationsList;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<PlannedExamination> getPlannedExamination() {
        return plannedExamination;
    }

    public void setPlannedExamination(List<PlannedExamination> plannedExamination) {
        this.plannedExamination = plannedExamination;
    }
}
