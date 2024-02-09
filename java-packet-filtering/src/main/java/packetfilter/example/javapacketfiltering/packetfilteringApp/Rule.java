package packetfilter.example.javapacketfiltering.packetfilteringapp;

public class Rule {
    private String sourceIP;
    private String destIP;
    private String port;
    private boolean isAllow;

    public Rule(String sourceIP, String destIP, String port, boolean isAllow) {
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.port = port;
        this.isAllow = isAllow;
    }
}
