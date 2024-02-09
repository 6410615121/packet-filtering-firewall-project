package packetfilter.example.javapacketfiltering.packetfilteringapp;

public class Device {
    private String IPaddress;

    public Device(String IP) {
        IPaddress = IP;
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public void setIPaddress(String iPaddress) {
        IPaddress = iPaddress;
    }
}
