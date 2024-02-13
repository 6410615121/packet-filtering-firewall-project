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

    // api for create device using ip as primary key
    @GetMapping("/api/createDevice")
    public ResponseEntity<String> createDevice(@RequestParam(defaultValue = "") String ip) {
        if (!ip.equals("")) {
            // check that ip user provided is unique.
            for (int i = 0; i < devices.size(); i++) {
                Device device = devices.get(i);

                // found ip on some device
                if (device.getIPaddress().equals(ip)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This IP already on some Device.");
                }
            }
        } else {
            // if user doesnt provide any ip, just gen it based on devices array list size
            ip = "192.168.1." + (devices.size() + 1);
        }

        // create device and add into arraylist
        Device device = new Device(ip);
        devices.add(device);

        // create text that will be showed on api endpoint
        String responseBody = "created a device using IP: " + device.getIPaddress() + "!";
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // api for getting all devices
    @GetMapping("/api/getAllDevices")
    public ResponseEntity<ArrayList<Device>> getAllDevices() {
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }

    // api for getting a device using ip
    @GetMapping("/api/getDevice")
    public ResponseEntity<Device> getDevice(@RequestParam String ip) {

        // iterate into device arraylist
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);

            // found it
            if (device.getIPaddress().equals(ip)) {
                return ResponseEntity.status(HttpStatus.OK).body(device);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
