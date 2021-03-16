package applicationPackage.businessLayer.serviceImpl;


import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.integrationLayer.DTO.ExaminationTypeDTO;
import applicationPackage.integrationLayer.entities.Examination;
import applicationPackage.integrationLayer.entities.ExaminationType;
import applicationPackage.integrationLayer.repository.ExaminationTypeRepository;
import applicationPackage.businessLayer.service.ExaminationTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationTypeServiceImpl implements ExaminationTypeService {

    @Autowired
    private ExaminationTypeRepository examinationTypeRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ExaminationTypeDTO> findAll() {
        List<ExaminationType> allTypes = examinationTypeRepository.findAll();
        return CommonMethods.getAllElements(allTypes, new ExaminationTypeDTO());

    }

    @Override
    public int saveType(ExaminationTypeDTO examinationTypeDTO, boolean createObject) {
        ExaminationType givenExaminationType = modelMapper.map(examinationTypeDTO, ExaminationType.class);
       ExaminationType examinationType = examinationTypeRepository.findByName(examinationTypeDTO.getName());
        if (examinationType == null && createObject) {
            examinationTypeRepository.save(givenExaminationType);
            return 1;
        } else if (examinationType != null && !createObject) {
            examinationType.setDescription(givenExaminationType.getDescription());
            examinationTypeRepository.save(examinationType);
            return 2;
        }
        return 0;
    }

    @Override
    public int deleteExaminationType(ExaminationTypeDTO examinationTypeDto) {
       ExaminationType examinationType = examinationTypeRepository.findByName(examinationTypeDto.getName());
        if (examinationType != null)
            for (Examination examination : examinationType.getExaminations())
            if (!examination.getPlannedExaminations().isEmpty()) {
                return 0;
            }
        examinationTypeRepository.delete(examinationType);
        return 6;

    }

    @Override
    public ExaminationTypeDTO getUpdateExaminationType(ExaminationTypeDTO examinationTypeDto) {
        return modelMapper.map(examinationTypeRepository.findByName(examinationTypeDto.getName()), ExaminationTypeDTO.class);
    }

    @Override
    public Examination saveExaminationType(Examination givenExamination) {
        givenExamination.setExaminationType(examinationTypeRepository.findByName(givenExamination.getExaminationType().getName()));
        return givenExamination;
    }

}

