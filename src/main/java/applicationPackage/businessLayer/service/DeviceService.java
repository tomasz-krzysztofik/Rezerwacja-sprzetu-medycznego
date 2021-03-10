package applicationPackage.businessLayer.service;


import applicationPackage.integrationLayer.DTO.DeviceDTO;
import applicationPackage.integrationLayer.DTO.ExaminationDTO;
import applicationPackage.integrationLayer.DTO.PlannedExaminationDTO;

import java.util.List;

public interface DeviceService {

    List<DeviceDTO> findAll();

    int saveDevice(DeviceDTO deviceDTO, boolean create);

    int changeStatus(String serialNumber);

    int deleteDevice(DeviceDTO deviceDTO);

    DeviceDTO getUpdateDevice(DeviceDTO deviceDTO);

    List<ExaminationDTO> findAllExaminationsForDevice(DeviceDTO deviceDTO);

    int updateDeviceExaminationList(ExaminationDTO examinationDTO);

    int deleteExamination(DeviceDTO chosenDeviceDTO, String name);

    List<PlannedExaminationDTO> findPossibleDates(String examinationName, List<PlannedExaminationDTO> plannedExaminations);

    List<DeviceDTO> findAllForDoctor();
}
