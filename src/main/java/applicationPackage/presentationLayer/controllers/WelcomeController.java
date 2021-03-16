package applicationPackage.presentationLayer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@RequestMapping("/")
@SessionScope

public class WelcomeController {

    @GetMapping("adminsPanel")
    public String adminsPanel() {
        return "/administratorTemplates/administrator-panel-start";
    }

    @GetMapping("techniciansPanel")
    public String techniciansPanel() {
        return "technicianTemplates/technician-panel-start";
    }

    @GetMapping("doctorsPanel")
    public String doctorPanel() {
        return "doctorTemplates/doctor-panel-start";
    }

    @GetMapping("receptionistsPanel")
    public String receptionistsPanel() {
        return "receptionistTemplates/receptionist-panel-start";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }


}
