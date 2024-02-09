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

    public String getSourceIP() {
        return sourceIP;
    }

    public String getDestIP() {
        return destIP;
    }

    public String getPort() {
        return port;
    }

    public boolean isAllow() {
        return isAllow;
    }

}
