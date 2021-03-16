package applicationPackage.businessLayer.service;


import applicationPackage.integrationLayer.DTO.ExaminationTypeDTO;
import applicationPackage.integrationLayer.entities.Examination;

import java.util.List;

public interface ExaminationTypeService {

    List<ExaminationTypeDTO> findAll();
    int saveType(ExaminationTypeDTO examinationTypeDto, boolean createObject);
    int deleteExaminationType(ExaminationTypeDTO examinationTypeDto);
    ExaminationTypeDTO getUpdateExaminationType(ExaminationTypeDTO examinationTypeDto);

    Examination saveExaminationType(Examination givenExamination);
}
