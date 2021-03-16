package applicationPackage.presentationLayer.controllers;

import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.integrationLayer.DTO.DeviceDTO;
import applicationPackage.integrationLayer.DTO.ExaminationDTO;
import applicationPackage.integrationLayer.DTO.ExaminationTypeDTO;
import applicationPackage.businessLayer.service.DeviceService;
import applicationPackage.businessLayer.service.ExaminationService;
import applicationPackage.businessLayer.service.ExaminationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doctorsPanel")
@SessionScope
public class DoctorPanelController {
    @Autowired
    private ExaminationTypeService examinationTypeService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private DeviceService deviceService;

    private DeviceDTO chosenDeviceDTO;

    @GetMapping("/addType")
    public ModelAndView showAddTypeForm() {
        return CommonMethods.showView("doctorTemplates/doctor-panel-add-type", "typeDTO", new ExaminationTypeDTO());
    }

    @PostMapping("/saveType")
    public ModelAndView saveType(@ModelAttribute ExaminationTypeDTO typeDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = examinationTypeService.saveType(typeDTO, true);
        return CommonMethods.resultView("redirect:/doctorsPanel/addType", redirectAttributes, returnMessage);
    }

    @GetMapping("/deleteType/{name}")
    public ModelAndView deleteType(@PathVariable("name") String name, RedirectAttributes redirectAttributes) {
        int returnMessage = examinationTypeService.deleteExaminationType(new ExaminationTypeDTO(name));
        return CommonMethods.resultView("redirect:/doctorsPanel/listOfTypes", redirectAttributes, returnMessage);
    }

    @GetMapping("/edit/{name}")
    public ModelAndView showUpdateForm(@PathVariable("name") String name) {
        return CommonMethods.showView("doctorTemplates/doctor-panel-edit-type", "examinationTypeDTO", examinationTypeService.getUpdateExaminationType(new ExaminationTypeDTO(name)));
    }

    @PostMapping("/update/{name}")
    public ModelAndView updateType(@ModelAttribute ExaminationTypeDTO typeDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = examinationTypeService.saveType(typeDTO, false);
        return CommonMethods.resultView("redirect:/doctorsPanel/listOfTypes", redirectAttributes, returnMessage);
    }

    @GetMapping("/listOfTypes")
    public ModelAndView showListOfTypes() {
        return CommonMethods.showView("doctorTemplates/doctor-panel-all-types", "list", examinationTypeService.findAll());
    }

    @GetMapping("/addExamination")
    public ModelAndView showAddExaminationForm() {
        ModelAndView modelAndView = CommonMethods.showView("doctorTemplates/doctor-panel-add-examination", "examinationDTO", new ExaminationDTO());
        List<ExaminationTypeDTO> typesDTO = examinationTypeService.findAll();
        modelAndView.addObject("typesDTO", typesDTO);
        return modelAndView;
    }

    @PostMapping("/saveExamination")
    public ModelAndView saveExamination(@ModelAttribute ExaminationDTO examinationDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = examinationService.saveExamination(examinationDTO, true);
        return CommonMethods.resultView("redirect:/doctorsPanel/addExamination", redirectAttributes, returnMessage);
    }

    @GetMapping("/listOfExaminations")
    public ModelAndView showListOfExaminations() {
        return CommonMethods.showView("doctorTemplates/doctor-panel-all-examinations", "list", examinationService.findAllForTable());
    }

    @GetMapping("/deleteExamination/{name}")
    public ModelAndView deleteExamination(@PathVariable("name") String name, RedirectAttributes redirectAttributes) {
        int returnMessage = examinationService.deleteExamination(new ExaminationDTO(name));
        return CommonMethods.resultView("redirect:/doctorsPanel/listOfExaminations", redirectAttributes, returnMessage);
    }

    @GetMapping("/editExamination/{name}")
    public ModelAndView showExaminationUpdateForm(@PathVariable("name") String name) {
        ModelAndView modelAndView = CommonMethods.showView("doctorTemplates/doctor-panel-edit-examination", "examinationDTO", examinationService.getUpdateExamination(new ExaminationDTO(name)));
        List<ExaminationTypeDTO> typesDTO = examinationTypeService.findAll();
        modelAndView.addObject("typesDTO", typesDTO);
        return modelAndView;
    }

    @PostMapping("/updateExamination/{name}")
    public ModelAndView updateExamination(@ModelAttribute ExaminationDTO examinationDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = examinationService.saveExamination(examinationDTO, false);
        return CommonMethods.resultView("redirect:/doctorsPanel/listOfExaminations", redirectAttributes, returnMessage);
    }

    @GetMapping("/listOfDevices")
    public ModelAndView showListOfDevices() {
        return CommonMethods.showView("doctorTemplates/doctor-panel-show-devices", "list", deviceService.findAll());
    }

    @GetMapping("/examinations/{serialNumber}")
    public ModelAndView showExaminationsAssignedToDevice(@PathVariable("serialNumber") String serialNumber) {
        chosenDeviceDTO = new DeviceDTO(serialNumber);
        return CommonMethods.showView("doctorTemplates/doctor-panel-device-examinations-list", "list", deviceService.findAllExaminationsForDevice(chosenDeviceDTO));
    }

    @GetMapping("/addExaminationToDevice")
    public ModelAndView showTypesForExaminationAndDeviceAssignment() {
        ModelAndView modelAndView = CommonMethods.showView("doctorTemplates/doctor-panel-add-examination-to-device", "list", examinationTypeService.findAll());
        modelAndView.addObject("examinationDTO", new ExaminationDTO());
        modelAndView.addObject("deviceList", deviceService.findAllForDoctor());
        return modelAndView;
    }

    @GetMapping("/showExaminationsForType/{name}")
    @ResponseBody
    public ArrayList<String> showExaminationsForChosenType(@PathVariable("name") String name) {
        return examinationService.findAllForChosenType(name);
    }

    @PostMapping("/postExaminationToDevice")
    public ModelAndView addExaminationToDevice(@ModelAttribute ExaminationDTO examinationDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = deviceService.updateDeviceExaminationList(examinationDTO);
        return CommonMethods.resultView("redirect:/doctorsPanel/addExaminationToDevice", redirectAttributes, returnMessage);

    }

    @GetMapping("deleteExaminationFromDevice/{name}")
    public ModelAndView delete(@PathVariable("name") String name, RedirectAttributes redirectAttributes) {
        int returnMessage = deviceService.deleteExamination(chosenDeviceDTO, name);
        return CommonMethods.resultView("redirect:/doctorsPanel/examinations/" + chosenDeviceDTO.getSerialNumber(), redirectAttributes, returnMessage);
    }


}

