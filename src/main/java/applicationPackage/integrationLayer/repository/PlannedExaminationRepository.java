package applicationPackage.integrationLayer.repository;

import applicationPackage.integrationLayer.entities.Device;
import applicationPackage.integrationLayer.entities.Patient;
import applicationPackage.integrationLayer.entities.PlannedExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PlannedExaminationRepository extends JpaRepository<PlannedExamination, Long> {
    PlannedExamination findByPatientAndDateTimeStartAndDevice(Patient patient, LocalDateTime localDateTime, Device device);

}
