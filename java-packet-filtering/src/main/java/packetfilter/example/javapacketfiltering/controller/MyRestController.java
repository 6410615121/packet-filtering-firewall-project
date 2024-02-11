package packetfilter.example.javapacketfiltering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import packetfilter.example.javapacketfiltering.packetfilteringapp.Device;

@RestController
public class MyRestController {
    private ArrayList<Device> devices = new ArrayList<Device>();

    // template for copy-paste
    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/example")
    public ResponseEntity<String> example() {
        String responseBody = "{\"message\": \"Example response\"}";
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    //

    @GetMapping("/api/createDevice")
    public ResponseEntity<String> createDevice(@RequestParam(defaultValue = "") String ip) {
        if (!ip.equals("")) {
            for (int i = 0; i < devices.size(); i++) {
                Device device = devices.get(i);
                if (device.getIPaddress().equals(ip)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This IP already on some Device.");
                }
            }
        } else {
            ip = "192.168.1." + (devices.size() + 1);
        }

        Device device = new Device(ip);
        devices.add(device);

        String responseBody = "created a device using IP: " + device.getIPaddress() + "!";
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/api/getAllDevices")
    public ResponseEntity<ArrayList<Device>> getAllDevices() {
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }

    @GetMapping("/api/getDevice")
    public ResponseEntity<Device> getAllDevices(@RequestParam String ip) {
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            if (device.getIPaddress().equals(ip)) {
                return ResponseEntity.status(HttpStatus.OK).body(device);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
