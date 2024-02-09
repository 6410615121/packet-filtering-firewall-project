package packetfilter.example.javapacketfiltering.packetfilteringapp;

public class Firewall {
    private RuleManager ruleManager;
    private boolean isActive;

    public void enable() {
        isActive = true;
    }

    public void disable() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isAllowPacket(String sourceIP, String destIP, String port) {

        // implement later
        return true;
    }

}
