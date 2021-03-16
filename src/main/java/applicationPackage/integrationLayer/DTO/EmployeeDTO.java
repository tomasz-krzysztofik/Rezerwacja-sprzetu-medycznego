package applicationPackage.integrationLayer.DTO;

public class EmployeeDTO {

    private String name;
    private String surname;
    private String pesel;
    private String workplace;
    private String login;
    private String password;
    private String pwzNumber;
    public EmployeeDTO() {
    }


    public EmployeeDTO(String pesel) {
        this.pesel = pesel;
    }

    public EmployeeDTO(String name, String surname, String pesel, String workplace, String login, String password, String pwzNumber) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.workplace = workplace;
        this.login = login;
        this.password = password;
        this.pwzNumber = pwzNumber;
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

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwzNumber() {
        return pwzNumber;
    }

    public void setPwzNumber(String pwzNumber) {
        this.pwzNumber = pwzNumber;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", pesel='" + pesel + '\'' +
                ", workplace='" + workplace + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", pwzNumber='" + pwzNumber + '\'' +
                '}';
    }
}
