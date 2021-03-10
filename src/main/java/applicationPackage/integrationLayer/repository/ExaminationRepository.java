package applicationPackage.integrationLayer.repository;

import applicationPackage.integrationLayer.entities.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    Examination findByName(String name);

}
