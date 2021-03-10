package applicationPackage.integrationLayer.DTO;
import java.util.ArrayList;
import java.util.List;

public class ExaminationTypeDTO {

    private String name;
    private String description;
    private List<ExaminationDTO> listOfExaminations;
    public ExaminationTypeDTO() {
    }

    public ExaminationTypeDTO(String name, String description, List<ExaminationDTO> listOfExaminations) {
        this.name = name;
        this.description = description;
        this.listOfExaminations = listOfExaminations;
    }

    public ExaminationTypeDTO(String name){
        this.name = name;
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

    public List<ExaminationDTO> getListOfExaminations() {
        if(listOfExaminations == null){
            listOfExaminations = new ArrayList<>();
        }
        return listOfExaminations;
    }

    public void setListOfExaminations(List<ExaminationDTO> listOfExaminations) {
        this.listOfExaminations = listOfExaminations;
    }

    @Override
    public String toString() {
        return "ExaminationTypeDTO" +
                "name='" + name + "\n" +
                ", description='" + description + "\n";
//                ", listOfExaminations=" + listOfExaminations +"\n";
    }
}
