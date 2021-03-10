package applicationPackage.businessLayer.serviceImpl;

import applicationPackage.integrationLayer.DTO.DeviceDTO;
import applicationPackage.integrationLayer.DTO.ExaminationDTO;
import applicationPackage.integrationLayer.DTO.PlannedExaminationDTO;
import applicationPackage.integrationLayer.entities.BuilderEntity;
import applicationPackage.integrationLayer.entities.Device;
import applicationPackage.integrationLayer.entities.Examination;
import applicationPackage.integrationLayer.entities.PlannedExamination;
import applicationPackage.integrationLayer.repository.DeviceRepository;
import applicationPackage.integrationLayer.repository.ExaminationRepository;
import applicationPackage.businessLayer.service.DeviceService;
import applicationPackage.businessLayer.utilityClasses.AvailableDates;
import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.businessLayer.utilityClasses.SortByDate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private BuilderEntity builderEntity = new BuilderEntity();
    @Autowired
    private ExaminationRepository examinationRepository;

    @Override
    public List<DeviceDTO> findAllForDoctor() {
        List<DeviceDTO> list = CommonMethods.getAllElements(deviceRepository.findAll(), new DeviceDTO());
        List<DeviceDTO> devicesData = new ArrayList<>();
        for (DeviceDTO dto : list) {
            devicesData.add(new DeviceDTO(dto.getSerialNumber(), dto.toString()));
        }
        return devicesData;
    }

    @Override
    public List<DeviceDTO> findAll() {
        return CommonMethods.getAllElements(deviceRepository.findAll(), new DeviceDTO());
    }
    @Override
    public int saveDevice(DeviceDTO deviceDTO, boolean create) {
        Device givenDevice = modelMapper.map(deviceDTO, Device.class);
        Device device = deviceRepository.findBySerialNumber(deviceDTO.getSerialNumber());
        if (device == null && create) {
            deviceRepository.save(givenDevice);
            return 1;
        } else if (device != null && !create) {
            deviceRepository.save(builderEntity.editDevice(device, deviceDTO));
            return 2;
        }
        return 0;
    }

    @Override
    public int changeStatus(String serialNumber) {
        Device device = deviceRepository.findBySerialNumber(serialNumber);
        byte status = device.getDeviceActivated();
        if (status == 0) {
            device.setDeviceActivated((byte) 1);
            deviceRepository.save(device);
            return 7;
        } else if (status == 1) {
            if (device.getPlannedExamination().isEmpty()) {
                device.setDeviceActivated((byte) 0);
                deviceRepository.save(device);
                return 8;
            }
        }
        return 0;
    }

    @Override
    public int deleteDevice(DeviceDTO deviceDTO) {
        Device device = deviceRepository.findBySerialNumber(deviceDTO.getSerialNumber());
        if (device != null)
            if (device.getPlannedExamination().isEmpty()) {
                deviceRepository.delete(device);
                return 6;
            }
        return 0;
    }

    @Override
    public DeviceDTO getUpdateDevice(DeviceDTO deviceDTO) {
        Device device = deviceRepository.findBySerialNumber(deviceDTO.getSerialNumber());
        return modelMapper.map(device, DeviceDTO.class);
    }

    @Override
    public List<ExaminationDTO> findAllExaminationsForDevice(DeviceDTO deviceDTO) {
        List<ExaminationDTO> assignedExaminationsDTO = new ArrayList<>();
        Device device = deviceRepository.findBySerialNumber(deviceDTO.getSerialNumber());
        List<Examination> assignedExaminations = device.getExaminationsList();
        if (assignedExaminations != null) {
            for (Examination examination : assignedExaminations) {
                assignedExaminationsDTO.add(modelMapper.map(examination, ExaminationDTO.class));
            }
        }
        return assignedExaminationsDTO;
    }

    @Override
    public int updateDeviceExaminationList(ExaminationDTO examinationDTO) {
        Device device = deviceRepository.findBySerialNumber(examinationDTO.getChosenDevice());
        Examination examination = examinationRepository.findByName(examinationDTO.getName());

        if (device.getExaminationsList().contains(examination))
            return 0;
        device.getExaminationsList().add(examination);
        deviceRepository.save(device);
        return 1;
    }

    @Override
    public int deleteExamination(DeviceDTO deviceDTO, String name) {
        Device device = deviceRepository.findBySerialNumber(deviceDTO.getSerialNumber());
        if (device == null)
            return 1;
        Examination examination = examinationRepository.findByName(name);
        if (examination.getPlannedExaminations().isEmpty()){
        device.getExaminationsList().remove(examination);
        deviceRepository.save(device);
        return 2;
        }
        return 0;
    }


    public List<PlannedExaminationDTO> findPossibleDates(String examinationName, List<PlannedExaminationDTO> plannedExaminations){
        List<Device> devices = deviceRepository.findAllByDeviceActivated((byte) 1);
        List<PlannedExaminationDTO> possibleDates = new ArrayList<>();
        if(devices != null) {
            for (Device device : devices){

                if (device.getExaminationsList().contains(new Examination(examinationName))){
                    possibleDates.addAll(fillCalendar(modelMapper.map(device, DeviceDTO.class), plannedExaminations, examinationName));
                }
            }
            Collections.sort(possibleDates, new SortByDate());
        }
        return possibleDates;
    }

    private List<PlannedExaminationDTO> fillCalendar(DeviceDTO deviceDTO, List<PlannedExaminationDTO> patientsExaminations, String examinationName) {
        AvailableDates availableDates = new AvailableDates();
        LocalDateTime time = availableDates.createDateForExamination();
        List<PlannedExaminationDTO> plannedExaminationDTO = plannedExaminationsDevice(deviceDTO);
        List<PlannedExaminationDTO> calendar = new ArrayList<>();


        for (int i = 0; i < 5; i++) {
            LocalDate date = time.toLocalDate();

            if (!date.isEqual(deviceDTO.getMaintenanceDate())) {
                while (availableDates.checkIfDateHasWorkTime(time)) {
                    PlannedExaminationDTO ex = new PlannedExaminationDTO(time, deviceDTO, new ExaminationDTO(examinationName));
                    calendar.add(ex);
                    time = time.plusMinutes(30);
                }
                time = availableDates.changeDay(time);
            } else {
                time = availableDates.changeDayWhenDateIsMaintenanceDay(time);
                i--;
            }
        }
        //usuwanie z kalendarza badan pacjenta
        if (patientsExaminations != null) {
            for (PlannedExaminationDTO ex : patientsExaminations) {
                calendar.remove(ex);
            }
        }
        //usuwanie z kalendarza innych badan przypisanych do urzadzenia
        if (plannedExaminationDTO != null) {
            for (PlannedExaminationDTO ex : plannedExaminationDTO) {
                calendar.remove(ex);
            }
        }
        return calendar;
    }

    private List<PlannedExaminationDTO> plannedExaminationsDevice(DeviceDTO deviceDTO) {
        Device device = deviceRepository.findBySerialNumber(deviceDTO.getSerialNumber());
        List<PlannedExamination> plannedExaminations = device.getPlannedExamination();
        if (plannedExaminations != null) {
            return CommonMethods.getAllElements(plannedExaminations, new PlannedExaminationDTO());
        }
        return null;
    }

}
