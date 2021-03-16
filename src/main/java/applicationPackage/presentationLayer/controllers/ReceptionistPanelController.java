package applicationPackage.presentationLayer.controllers;

import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.integrationLayer.DTO.ExaminationDTO;
import applicationPackage.integrationLayer.DTO.PatientDTO;
import applicationPackage.integrationLayer.DTO.PlannedExaminationDTO;
import applicationPackage.integrationLayer.DTO.PlannedExaminationDataObject;
import applicationPackage.businessLayer.service.DeviceService;
import applicationPackage.businessLayer.service.PlannedExaminationService;
import applicationPackage.businessLayer.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/receptionistsPanel")
@SessionScope
public class ReceptionistPanelController {

    @Autowired
    private ReceptionistService receptionistService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PlannedExaminationService plannedExaminationService;
    private List<PlannedExaminationDTO> freeDates;
    private List<PlannedExaminationDTO> plannedExaminations;
    private List<ExaminationDTO> examinationsDTO;
    private PatientDTO patientDTO;

    @GetMapping("/listOfPatients")
    public ModelAndView showAllPatients() {
        return CommonMethods.showView("receptionistTemplates/receptionist-panel-all-patients", "list", receptionistService.findAll());
    }


    @GetMapping("/addPatient")
    public ModelAndView showAddForm() {
        return CommonMethods.showView("receptionistTemplates/receptionist-panel-add-patient", "patientDTO", new PatientDTO());
    }

    @PostMapping("/savePatient")
    public ModelAndView savePatient(@ModelAttribute PatientDTO patientDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = receptionistService.savePatient(patientDTO, true);
        return CommonMethods.resultView("redirect:/receptionistsPanel/addPatient", redirectAttributes, returnMessage);
    }

    @GetMapping("/delete/{pesel}")
    public ModelAndView deleteEmployee(@PathVariable("pesel") String pesel, RedirectAttributes redirectAttributes) {
        int returnMessage = receptionistService.deletePatient(pesel);
        return CommonMethods.resultView("redirect:/receptionistsPanel/listOfPatients", redirectAttributes, returnMessage);
    }

    @GetMapping("/edit/{pesel}")
    public ModelAndView showUpdateForm(@PathVariable("pesel") String pesel, RedirectAttributes redirectAttributes) {
        PatientDTO patientDTO = receptionistService.getUpdateEmployee(pesel);
        if (patientDTO == null){
            int returnMessage = 8;
            return CommonMethods.resultView("redirect:/receptionistsPanel/listOfPatients", redirectAttributes, returnMessage);
        }
        return CommonMethods.showView("receptionistTemplates/receptionist-panel-edit-patient", "patientDTO", receptionistService.getUpdateEmployee(pesel));
    }

    @PostMapping("/update/{pesel}")
    public ModelAndView updateEmployee(@ModelAttribute PatientDTO patientDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = receptionistService.savePatient(patientDTO, false);
        return CommonMethods.resultView("redirect:/receptionistsPanel/listOfPatients", redirectAttributes, returnMessage);
    }

    @GetMapping("/plannedExaminationsList/{pesel}")
    public ModelAndView getAllPlannedExaminations(@PathVariable("pesel") String pesel) {
        patientDTO = new PatientDTO(pesel);
        plannedExaminations = receptionistService.plannedExaminations(pesel);
        return CommonMethods.showView("receptionistTemplates/receptionist-panel-patient-examinations", "list", plannedExaminations);
    }

    @GetMapping("/bookExamination")
    public ModelAndView plannedExaminationForm() {
        examinationsDTO = receptionistService.findAllExaminationsForActiveDevices();
        ModelAndView modelAndView = CommonMethods.showView("receptionistTemplates/receptionist-panel-book-an-examination", "list", receptionistService.findAllTypesForPossibleExaminations(examinationsDTO));
        modelAndView.addObject("plannedExaminationDataObject", new PlannedExaminationDataObject());
        return modelAndView;
    }

    //ta metoda zwraca nazwy badan dla wybranego typu
    @ResponseBody
    @GetMapping("/findExaminationsForActiveDevices/{type}")
    public ArrayList<String> getExaminationsForActiveDevices(@PathVariable("type") String type) {
        ArrayList<String> examinationNames = new ArrayList<>();
        for (ExaminationDTO ex : examinationsDTO) {
            if (ex.getExaminationTypeDto().getName().equals(type) && !examinationNames.contains(ex.getName()))
                examinationNames.add(ex.getName());
        }
        return examinationNames;
    }


    @ResponseBody
    @GetMapping("/searchPatient/{pesel}")
    public String searchPatient(@PathVariable String pesel) {
        patientDTO = receptionistService.findByPesel(pesel);
        if (patientDTO != null) {
            return patientDTO.toString();
        } else
            return "null";
    }

    @ResponseBody
    @GetMapping("/searchEmployee/{pesel}")
    public String searchEmployee(@PathVariable("pesel") String pesel) {
        return receptionistService.findEmployee(pesel);
    }

    @GetMapping("/showFreeDates")
    public ModelAndView showFreeDates(@ModelAttribute PlannedExaminationDataObject plannedExaminationDataObject) {
        freeDates = deviceService.findPossibleDates(plannedExaminationDataObject.getExaminationName(), receptionistService.plannedExaminations(patientDTO.getPesel()));
        return CommonMethods.showView("receptionistTemplates/receptionist-panel-choose-device", "list", freeDates);
    }

    @PostMapping("/saveExaminationToPatientAndDevice/{chosenIndex}")
    @ResponseBody
    public ModelAndView addPlannedExaminationToPatientAndDevice(@PathVariable("chosenIndex") int chosenIndex, RedirectAttributes redirectAttributes) {
        PlannedExaminationDTO chosenDTO = freeDates.get(chosenIndex);
        int response = plannedExaminationService.addPlannedExamination(patientDTO, chosenDTO);
        return CommonMethods.resultView("redirect:/receptionistsPanel/showResult", redirectAttributes, response );

    }

    @GetMapping("/showResult")
    public ModelAndView showResult(){
       ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("receptionistTemplates/receptionist-panel-show-result");
        return modelAndView;
    }

    @GetMapping("/deleteExamination/{chosenIndex}")
    public ModelAndView deleteExaminationFromPatientAndDevice(@PathVariable("chosenIndex") int chosenIndex) {
        PlannedExaminationDTO chosenDTO = plannedExaminations.get(chosenIndex);
        plannedExaminationService.delete(patientDTO, chosenDTO);
        return getAllPlannedExaminations(patientDTO.getPesel());

    }



}
