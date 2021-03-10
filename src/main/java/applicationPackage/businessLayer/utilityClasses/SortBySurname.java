package applicationPackage.businessLayer.utilityClasses;

import applicationPackage.integrationLayer.entities.Patient;

import java.util.Comparator;

public class SortBySurname implements Comparator<Patient> {
    @Override
    public int compare(Patient o1, Patient o2) {
        return o1.getSurname().compareTo(o2.getSurname());
    }
}
