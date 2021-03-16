package applicationPackage.presentationLayer.controllers;

import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.integrationLayer.DTO.DeviceDTO;
import applicationPackage.businessLayer.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/techniciansPanel")
@SessionScope
public class TechnicianPanelController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/addDevice")
    public ModelAndView showAddForm() {
        return CommonMethods.showView("technicianTemplates/technician-panel-add-device", "deviceDTO", new DeviceDTO());
    }

    @GetMapping("/listOfDevices")
    public ModelAndView showListOfDevices() {
        return CommonMethods.showView("technicianTemplates/technician-panel-all-devices", "list", deviceService.findAll());
    }


    @PostMapping("/saveDevice")
    public ModelAndView saveDevice(@ModelAttribute DeviceDTO deviceDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = deviceService.saveDevice(deviceDTO, true);
        return CommonMethods.resultView("redirect:/techniciansPanel/addDevice", redirectAttributes, returnMessage);
    }

    @GetMapping("deleteDevice/{serialNumber}")
    public ModelAndView deleteDevice(@PathVariable("serialNumber") String serialNumber, RedirectAttributes redirectAttributes) {
        int returnMessage = deviceService.deleteDevice(new DeviceDTO(serialNumber));
        return CommonMethods.resultView("redirect:/techniciansPanel/listOfDevices", redirectAttributes, returnMessage);
    }

    @GetMapping("editDevice/{serialNumber}")
    public ModelAndView showUpdateForm(@PathVariable("serialNumber") String serialNumber) {
        return CommonMethods.showView("technicianTemplates/technician-panel-edit-device", "deviceDTO", deviceService.getUpdateDevice(new DeviceDTO(serialNumber)));
    }

    @PostMapping("updateDevice/{serialNumber}")
    public ModelAndView updateDevice(@ModelAttribute DeviceDTO deviceDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = deviceService.saveDevice(deviceDTO, false);
        return CommonMethods.resultView("redirect:/techniciansPanel/listOfDevices", redirectAttributes, returnMessage);
    }

    @GetMapping("changeStatus/{serialNumber}")
    public ModelAndView changeStatus(@PathVariable("serialNumber") String serialNumber, RedirectAttributes redirectAttributes) {
        int returnMessage = deviceService.changeStatus(serialNumber);
        return CommonMethods.resultView("redirect:/techniciansPanel/listOfDevices", redirectAttributes, returnMessage);

    }




}
