package applicationPackage.presentationLayer.controllers;

import applicationPackage.businessLayer.utilityClasses.CommonMethods;
import applicationPackage.integrationLayer.DTO.EmployeeDTO;
import applicationPackage.businessLayer.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/adminsPanel")
@SessionScope
public class AdminPanelController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/addEmployee")
    public ModelAndView showAddForm() {
        return CommonMethods.showView("administratorTemplates/administrator-panel-add-employee", "employeeDTO", new EmployeeDTO());
    }

    @GetMapping("/listOfEmployees")
    public ModelAndView showAllEmployees() {
        return CommonMethods.showView("administratorTemplates/administrator-panel-all-employees", "list", adminService.findAll());
    }

    @PostMapping("/saveEmployee")
    public ModelAndView saveEmployee(@ModelAttribute EmployeeDTO employeeDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = adminService.saveEmployee(employeeDTO, true);
        return CommonMethods.resultView("redirect:/adminsPanel/addEmployee", redirectAttributes, returnMessage);
    }

    @GetMapping("delete/{pesel}")
    public ModelAndView deleteEmployee(@PathVariable("pesel") String pesel) {
        adminService.deleteEmployee(pesel);
        return showAllEmployees();
    }

    @GetMapping("edit/{pesel}")
    public ModelAndView showUpdateForm(@PathVariable("pesel") String pesel) {
        return CommonMethods.showView("administratorTemplates/administrator-panel-edit-employee", "employeeDTO", adminService.getUpdateEmployee(pesel));
    }

    @PostMapping("update/{pesel}")
    public ModelAndView updateEmployee(@ModelAttribute EmployeeDTO employeeDTO, RedirectAttributes redirectAttributes) {
        int returnMessage = adminService.saveEmployee(employeeDTO, false);
        return CommonMethods.resultView("redirect:/adminsPanel/listOfEmployees", redirectAttributes, returnMessage);
    }

    @ResponseBody
    @GetMapping("searchPatient/{pesel}")
    public String searchEmployee(@PathVariable("pesel") String pesel) {
        return adminService.findPatient(pesel);
    }

    @GetMapping("changePanel")
    public String panelView(){
        return "administratorTemplates/administrator-panel-all-panels";
    }
}
