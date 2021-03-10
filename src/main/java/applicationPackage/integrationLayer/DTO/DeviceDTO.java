package applicationPackage.integrationLayer.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class DeviceDTO {

    private String name;
    private String model;
    private int productionYear;
    private String producer;
    private String serialNumber;
    private String mainService;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate installationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate maintenanceDate;
    private List<ExaminationDTO> examinationList;
    private String room;
    private String data;
    private byte deviceActivated;

    public DeviceDTO() {
    }

    public DeviceDTO(String serialNumber, String data) {
        this.serialNumber = serialNumber;
        this.data = data;
    }

    public DeviceDTO(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public List<ExaminationDTO> getExaminationList() {
        return examinationList;
    }

    public void setExaminationList(List<ExaminationDTO> examinationList) {
        this.examinationList = examinationList;
    }

    public byte getDeviceActivated() {
        return deviceActivated;
    }

    public void setDeviceActivated(byte deviceActivated) {
        this.deviceActivated = deviceActivated;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Nazwa: " + name + ", Model: " + model  + ", Rok Produkcji: " + productionYear + ", Producent: " + producer  + ", Sala: " + room;
    }
}
