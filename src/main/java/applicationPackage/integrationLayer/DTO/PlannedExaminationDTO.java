package applicationPackage.integrationLayer.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class PlannedExaminationDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTimeStart;
    private PatientDTO patientDTO;
    private ExaminationDTO examinationDTO;
    private DeviceDTO deviceDTO;


    public PlannedExaminationDTO(PatientDTO patientDTO) {
        this.patientDTO = patientDTO;
    }

    public PlannedExaminationDTO() {

    }

    public PlannedExaminationDTO(LocalDateTime dateTimeStart, DeviceDTO deviceDTO, ExaminationDTO examinationDTO) {
        this.dateTimeStart = dateTimeStart;
        this.deviceDTO = deviceDTO;
        this.examinationDTO = examinationDTO;
    }

    public PlannedExaminationDTO(LocalDateTime dateTimeStart, PatientDTO patientDTO, ExaminationDTO examinationDTO, DeviceDTO deviceDTO) {
        this.dateTimeStart = dateTimeStart;
        this.patientDTO = patientDTO;
        this.examinationDTO = examinationDTO;
        this.deviceDTO = deviceDTO;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }


    public PatientDTO getPatientDTO() {
        return patientDTO;
    }

    public void setPatientDTO(PatientDTO patientDTO) {
        this.patientDTO = patientDTO;
    }

    public ExaminationDTO getExaminationDTO() {
        return examinationDTO;
    }

    public void setExaminationDTO(ExaminationDTO examinationDTO) {
        this.examinationDTO = examinationDTO;
    }

    public DeviceDTO getDeviceDTO() {
        return deviceDTO;
    }

    public void setDeviceDTO(DeviceDTO deviceDTO) {
        this.deviceDTO = deviceDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlannedExaminationDTO that = (PlannedExaminationDTO) o;

        return dateTimeStart.equals(that.dateTimeStart);
    }

    @Override
    public int hashCode() {
        return dateTimeStart.hashCode();
    }

    @Override
    public String toString() {
        return "PlannedExaminationDTO{" +
                "dateTimeStart=" + dateTimeStart +
                ", examinationDTO=" + examinationDTO.getName() +
                ", deviceDTO=" + deviceDTO.getRoom() +
                '}';
    }
}
