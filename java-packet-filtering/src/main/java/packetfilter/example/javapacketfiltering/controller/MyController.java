package packetfilter.example.javapacketfiltering.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
