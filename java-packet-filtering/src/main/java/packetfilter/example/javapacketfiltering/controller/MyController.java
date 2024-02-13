package packetfilter.example.javapacketfiltering.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import packetfilter.example.javapacketfiltering.packetfilteringapp.Device;

@Controller
public class MyController {
    @Autowired
    private MyRestController myRestController;

    public MyController(MyRestController myRestController) {
        this.myRestController = myRestController;
    }

    @GetMapping("/testtemplate")
    public String hello(Model model) {

        model.addAttribute("message", "Hello, World!");
        return "test";
    }

    @GetMapping("/")
    public String index(Model model) {
        ArrayList<Device> devices = myRestController.getAllDevices().getBody();
        model.addAttribute("devices", devices);
        return "deviceManager";
    }

    @GetMapping("/createDevice")
    public String createDevice(Model model, @RequestParam(defaultValue = "") String ip) {
        myRestController.createDevice(ip);
        return "redirect:/";
    }

    @GetMapping("/device")
    public ModelAndView devicePage(Model model, @RequestParam(defaultValue = "") String ip) {
        ResponseEntity<Device> responseEntity = myRestController.getDevice(ip);
        HttpStatusCode status = responseEntity.getStatusCode();
        Device device = responseEntity.getBody();
        if (device == null) {
            model.addAttribute("error", 404);
            return new ModelAndView("error", HttpStatus.NOT_FOUND);
        }

        model.addAttribute("device", device);
        return new ModelAndView("device", status);
    }
}
