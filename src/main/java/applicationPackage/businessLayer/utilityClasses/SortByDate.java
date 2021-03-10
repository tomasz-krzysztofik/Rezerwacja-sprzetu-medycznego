package applicationPackage.businessLayer.utilityClasses;

import applicationPackage.integrationLayer.DTO.PlannedExaminationDTO;

import java.util.Comparator;

public class SortByDate implements Comparator<PlannedExaminationDTO> {
    @Override
    public int compare(PlannedExaminationDTO o1, PlannedExaminationDTO o2) {
        return o1.getDateTimeStart().compareTo(o2.getDateTimeStart());
    }
}
