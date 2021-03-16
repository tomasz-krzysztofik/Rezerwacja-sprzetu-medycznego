package applicationPackage.integrationLayer.DTO;

public class PatientDTO {
    private String name;
    private String surname;
    private String pesel;
    private String sex;
    private String address;
    private String plenipotentiary;

    public PatientDTO() {

    }

    public PatientDTO(String name, String surname, String pesel, String sex, String address, String plenipotentiary) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.sex = sex;
        this.address = address;
        this.plenipotentiary = plenipotentiary;
    }

    public PatientDTO(String pesel){
        this.pesel = pesel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlenipotentiary() {
        return plenipotentiary;
    }

    public void setPlenipotentiary(String plenipotentiary) {
        this.plenipotentiary = plenipotentiary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "Znaleziony pacjent: " + name + " " + surname;
    }
}
