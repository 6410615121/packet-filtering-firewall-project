package packetfilter.example.javapacketfiltering.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import packetfilter.example.javapacketfiltering.packetfilteringapp.Device;
import packetfilter.example.javapacketfiltering.packetfilteringapp.Firewall;
import packetfilter.example.javapacketfiltering.packetfilteringapp.RuleManager;
import packetfilter.example.javapacketfiltering.packetfilteringapp.Rule;

@Controller
public class MyController {

    // using MyRestController to get api endpoint
    @Autowired
    private MyRestController myRestController;

    public MyController(MyRestController myRestController) {
        this.myRestController = myRestController;
    }

    // method for referencing purpose.
    @GetMapping("/testtemplate")
    public String hello(Model model) {
        model.addAttribute("message", "Hello, World!"); // add attribute in to template
        return "test"; // render template 'test.html'
    }
    //

    // index, DeviceManager page
    // show all devices that was created.
    @GetMapping("/")
    public String index(Model model) {
        // using myRestController and getAllDevices() to get devices and getBody() to-
        // get body of the ResponseEntity
        ArrayList<Device> devices = myRestController.getAllDevices().getBody();
        model.addAttribute("devices", devices);
        return "deviceManager";
    }

    // method to create device using ip user provided
    @GetMapping("/createDevice")
    public String createDevice(@RequestParam(defaultValue = "") String ip) {
        // call method is MyRestController to create Device
        myRestController.createDevice(ip);

        // redirect to /
        return "redirect:/";
    }

    // individual device page
    @GetMapping("/device")
    public ModelAndView devicePage(Model model, @RequestParam(defaultValue = "") String ip) {
        // get device using ip from deviceManger page
        ResponseEntity<Device> responseEntity = myRestController.getDevice(ip);

        // get status code from responseEntity. might not be necessary.
        HttpStatusCode status = responseEntity.getStatusCode();

        // get Device from responseEntity api endpoint provided
        Device device = responseEntity.getBody();

        // if not found then render 'error' template with 404 http status code
        if (device == null) {
            model.addAttribute("error", 404);
            return new ModelAndView("error", HttpStatus.NOT_FOUND);
        }

        // add device attribute to template
        model.addAttribute("device", device);

        // using ModelAndView to render template with status code
        return new ModelAndView("device", status);
    }

    @PostMapping("/device/togglefirewall/")
    public String toggleFirewall(@RequestParam String ip) {
        // get device from api Endpoint
        Device device = myRestController.getDevice(ip).getBody();

        // get firewall from get method in Device Class
        Firewall firewall = device.getFirewall();

        // toggle
        if (firewall.isActive()) {
            firewall.disable();
        } else {
            firewall.enable();
        }

        // when toggled just redirect to device page (/device)
        return "redirect:/device?ip=" + ip;
    }

    // Post method to sendpacket it require many arguments including sourceIP destIP
    // and port
    @PostMapping("/device/sendpacket")
    public String sendPacket(RedirectAttributes redirectAttributes, @RequestParam String sourceIP,
            @RequestParam String destIP,
            @RequestParam String port) {

        //for testing
        System.out.println(sourceIP);
        System.out.println(sourceIP.toString() == "192.168.1.1");
        System.out.println("===");

        // get Device using information from form user provided
        Device sourceDevice = myRestController.getDevice(sourceIP).getBody();
        Device destDevice = myRestController.getDevice(destIP).getBody();

        // get Firewall from devices, null for DestDeviceFirewall first because we -
        // don't know that Destination device is exist or not
        Firewall sourceDeviceFirewall = sourceDevice.getFirewall();
        Firewall DestDeviceFirewall = null;

        // like model.addAttribute("device", device) but use it when redirect to keep
        // attribute from missing.
        // attribute 'sent' is set to true to indicate template that user have sent
        // packet and needed to show the sending result furthur
        redirectAttributes.addFlashAttribute("sent", true);

        // using isAllowPacket() to get the result from firewall that will be indicated
        // that packet is blocked or not
        boolean sendResultSource = sourceDeviceFirewall.isAllowPacket(sourceIP, destIP, port);

        // check if source firewall is active or not, if active just use the result
        // above, if not then packet will be allowed for every packet.
        if (sourceDeviceFirewall.isActive()) {
            redirectAttributes.addFlashAttribute("sendResultSource", sendResultSource);
        } else {
            redirectAttributes.addFlashAttribute("sendResultSource", true);
        }

        // check if destDevice is not null, if null then sending result will be false.
        // if not then checking furthur using firewall isAllowPacket() and isActive()
        if (destDevice == null) {
            redirectAttributes.addFlashAttribute("sendResultDest", false);
        } else {
            DestDeviceFirewall = destDevice.getFirewall();

            if (DestDeviceFirewall.isActive()) {
                boolean sendResultDest = sourceDeviceFirewall.isAllowPacket(sourceIP, destIP, port);
                redirectAttributes.addFlashAttribute("sendResultDest", sendResultDest);
            } else {
                redirectAttributes.addFlashAttribute("sendResultDest", true);
            }

        }

        return "redirect:/device?ip=" + sourceIP;
    }

    // Post method to createrule, it require sourceIP, destIP, port, and isAllow
    @PostMapping("/device/createrule")
    public String createRule(RedirectAttributes redirectAttributes, @RequestParam String sourceIP,
            @RequestParam String ruleSourceIP,
            @RequestParam String destIP,
            @RequestParam String port,
            @RequestParam Boolean isAllow) {
                // get Device using information from the form that user provided
                Device sourceDevice = myRestController.getDevice(sourceIP).getBody();

                // get Firewall from device
                Firewall sourceDeviceFirewall = sourceDevice.getFirewall();
                
                // get Rule manager from firewall
                RuleManager sourceDeviceRuleManager = sourceDeviceFirewall.getRuleManager();
                
                // create new rule using information from the form that user provided
                Rule newRule = new Rule(ruleSourceIP, destIP, port, isAllow);
                
                // add the created rule to the rule manager 
                sourceDeviceRuleManager.addRule(newRule);

                // attribute 'created' is set to true to indicate to template that a rule has been created
                redirectAttributes.addFlashAttribute("created", true);

                //just for testing
                redirectAttributes.addFlashAttribute("allow", isAllow);
                return "redirect:/device?ip=" + sourceIP;
            }
}
