package applicationPackage.integrationLayer.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id", nullable = false)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String pesel;
    @NotNull
    private String workplace;
    @NotNull
    private String login;
    @NotNull
    private String password;

    private String role;

    public Employee() {
    }

    public Employee(long id, String name, String surname, String pesel, String workplace, String login, String password, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.workplace = workplace;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
