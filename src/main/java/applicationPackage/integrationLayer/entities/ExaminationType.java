package applicationPackage.integrationLayer.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="examinationTypes")
public class ExaminationType {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="type_id")
    private long id;
    @NotNull
    private String name;
    private String description;
   @OneToMany(mappedBy = "examinationType", cascade = CascadeType.ALL)
    private List<Examination> examinations;
    public ExaminationType() {
    }

    public ExaminationType(long id, String name, String description, List<Examination> examinations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.examinations = examinations;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Examination> getExaminations() {
        return examinations;
    }

    public void setExaminations(List<Examination> examinations) {
        this.examinations = examinations;
    }

    @Override
    public String toString() {
        return "ExaminationType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", examinations=" + examinations +
                '}';
    }
}
