package packetfilter.example.javapacketfiltering.packetfilteringapp;

public class Device {
    private String IPaddress;
    private Firewall firewall;

    public Device(String IP) {
        IPaddress = IP;
        firewall = new Firewall();
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public void setIPaddress(String iPaddress) {
        IPaddress = iPaddress;
    }

    public Firewall getFirewall() {
        return firewall;
    }

    public boolean sendTo(String destIP, String port) {
        boolean isAllow = firewall.isAllowPacket(IPaddress, destIP, port);
        return isAllow;
    }
}
