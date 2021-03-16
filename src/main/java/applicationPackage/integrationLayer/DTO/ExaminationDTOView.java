package applicationPackage.integrationLayer.DTO;

public class ExaminationDTOView {

    private String name;
    private String description;
    private String examinationTypeName;

    public ExaminationDTOView() {
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

    public String getExaminationTypeName() {
        return examinationTypeName;
    }

    public void setExaminationTypeName(String examinationTypeName) {
        this.examinationTypeName = examinationTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExaminationDTOView that = (ExaminationDTOView) o;

        return examinationTypeName.equals(that.examinationTypeName);
    }

    @Override
    public int hashCode() {
        return examinationTypeName.hashCode();
    }
}
