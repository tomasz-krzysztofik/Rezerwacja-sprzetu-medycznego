package applicationPackage.integrationLayer.DTO;

public class ExaminationDTO {

    private String name;
    private String description;
    private ExaminationTypeDTO examinationTypeDto;
    private String chosenDevice;


    public ExaminationDTO() {
    }

    public ExaminationDTO(String name) {
        this.name = name;
    }

    public ExaminationDTO(String name, String description, ExaminationTypeDTO examinationTypeDto, String chosenDevice) {
        this.name = name;
        this.description = description;
        this.examinationTypeDto = examinationTypeDto;
        this.chosenDevice = chosenDevice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExaminationTypeDTO getExaminationTypeDto() {
        return examinationTypeDto;
    }

    public void setExaminationTypeDto(ExaminationTypeDTO examinationTypeDto) {
        this.examinationTypeDto = examinationTypeDto;
    }

    public String getChosenDevice() {
        return chosenDevice;
    }

    public void setChosenDevice(String chosenDevice) {
        this.chosenDevice = chosenDevice;
    }
}
