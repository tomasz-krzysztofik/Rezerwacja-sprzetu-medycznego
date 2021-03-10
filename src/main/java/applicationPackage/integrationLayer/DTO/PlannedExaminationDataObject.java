package applicationPackage.integrationLayer.DTO;

public class PlannedExaminationDataObject {
    private String pesel;
    private String examinationName;


    public PlannedExaminationDataObject(String pesel, String examinationName) {
        this.pesel = pesel;
        this.examinationName = examinationName;
    }

    public PlannedExaminationDataObject() {
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }
}
