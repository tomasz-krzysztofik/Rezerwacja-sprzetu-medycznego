package applicationPackage.businessLayer.serviceImpl;


import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.integrationLayer.DTO.ExaminationDTO;
import applicationPackage.integrationLayer.DTO.ExaminationDTOView;
import applicationPackage.integrationLayer.entities.BuilderEntity;
import applicationPackage.integrationLayer.entities.Examination;
import applicationPackage.integrationLayer.repository.ExaminationRepository;
import applicationPackage.businessLayer.service.ExaminationService;
import applicationPackage.businessLayer.service.ExaminationTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExaminationServiceImpl implements ExaminationService {

    @Autowired
    private ExaminationRepository examinationRepository;
    @Autowired
    private ExaminationTypeService examinationTypeService;
    private ModelMapper modelMapper = new ModelMapper();
    private BuilderEntity builderEntity = new BuilderEntity();
    @Override
    public int saveExamination(ExaminationDTO examinationDTO, boolean createObject) {
        Examination givenExamination = modelMapper.map(examinationDTO, Examination.class);
       Examination examination = examinationRepository.findByName(examinationDTO.getName());
        if (examination == null && createObject) {
            givenExamination = examinationTypeService.saveExaminationType(givenExamination);
            examinationRepository.save(givenExamination);
            return 1;
        } else if (examination != null && !createObject) {
            givenExamination = examinationTypeService.saveExaminationType(givenExamination);
            examinationRepository.save(builderEntity.editExamination(examination, givenExamination));
            return 3;
        }
        return 0;
    }

    @Override
    public List<ExaminationDTO> findAll() {
        return CommonMethods.getAllElements(examinationRepository.findAll(), new ExaminationDTO());
    }

    @Override
    public List<ExaminationDTOView> findAllForTable() {
        return CommonMethods.getAllElements(examinationRepository.findAll(), new ExaminationDTOView());
    }

    @Override
    public int deleteExamination(ExaminationDTO examinationDTO){
        Examination examination = examinationRepository.findByName(examinationDTO.getName());
        if (examination!=null)
            if (examination.getPlannedExaminations().isEmpty()){
            examinationRepository.delete(examination);
            return 6;}

            return 0;

    }

    @Override
    public ExaminationDTO getUpdateExamination(ExaminationDTO examinationDTO) {
        return modelMapper.map(examinationRepository.findByName(examinationDTO.getName()),ExaminationDTO.class);
    }

    @Override
    public ArrayList<String> findAllForChosenType(String name) {
        ArrayList <String> matchedExaminations = new ArrayList<>();
        List <ExaminationDTOView> allExaminations = findAllForTable();
        for (ExaminationDTOView ex: allExaminations) {

            if (ex.getExaminationTypeName().equals(name))
                matchedExaminations.add(ex.getName());
        }

        return matchedExaminations;
    }



}
