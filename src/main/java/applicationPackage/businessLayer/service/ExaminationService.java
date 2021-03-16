package applicationPackage.businessLayer.service;


import applicationPackage.integrationLayer.DTO.ExaminationDTO;
import applicationPackage.integrationLayer.DTO.ExaminationDTOView;

import java.util.ArrayList;
import java.util.List;

public interface ExaminationService {

    int saveExamination(ExaminationDTO examinationDTO, boolean b);

    List<ExaminationDTO> findAll();

    List<ExaminationDTOView> findAllForTable();

    int deleteExamination(ExaminationDTO examinationDTO);

    ExaminationDTO getUpdateExamination(ExaminationDTO examinationDTO);

    ArrayList<String> findAllForChosenType(String name);

}
