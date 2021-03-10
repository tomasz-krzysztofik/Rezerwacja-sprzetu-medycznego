package applicationPackage.integrationLayer.repository;


import applicationPackage.integrationLayer.entities.ExaminationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationTypeRepository extends JpaRepository<ExaminationType, Long> {
    ExaminationType findByName(String name);
}