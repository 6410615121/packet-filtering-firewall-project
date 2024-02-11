package packetfilter.example.javapacketfiltering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/testtemplate")
    public String hello(Model model) {
        model.addAttribute("message", "Hello, World!");
        return "test";
    }

    @GetMapping("/")
    public String index(Model model) {
        return "deviceManager";
    }
}
