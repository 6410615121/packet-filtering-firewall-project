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

    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/example")
    public ResponseEntity<String> example() {
        String responseBody = "{\"message\": \"Example response\"}";
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/api/createDevice")
    public String createDevice(@RequestParam(defaultValue = "127.0.0.1") String IP) {
        Device device = new Device(IP);
        devices.add(device);
        return "created a device using IP: " + device.getIPaddress() + "!";
    }

    @GetMapping("/api/getAllDevices")
    public ResponseEntity<ArrayList<Device>> getAllDevices() {
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }

}
