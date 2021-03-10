package applicationPackage.integrationLayer.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class PlannedExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="plannedExamination_id")
    private long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="examination_id", nullable = false)
    private Examination examination;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="device_id", nullable = false)
    private Device device;

    private LocalDateTime dateTimeStart;
    public PlannedExamination() {
    }

    public PlannedExamination(long id, Patient patient, Examination examination, Device device, LocalDateTime dateTime) {
        this.id = id;
        this.patient = patient;
        this.examination = examination;
        this.device = device;
        this.dateTimeStart = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlannedExamination that = (PlannedExamination) o;

        if (!Objects.equals(patient, that.patient)) return false;
        if (!Objects.equals(examination, that.examination)) return false;
        if (!Objects.equals(device, that.device)) return false;
        return Objects.equals(dateTimeStart, that.dateTimeStart);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (patient != null ? patient.hashCode() : 0);
        result = 31 * result + (examination != null ? examination.hashCode() : 0);
        result = 31 * result + (device != null ? device.hashCode() : 0);
        result = 31 * result + (dateTimeStart != null ? dateTimeStart.hashCode() : 0);
        return result;
    }
}
