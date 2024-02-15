package packetfilter.example.javapacketfiltering.packetfilteringapp;

import java.util.ArrayList;

public class Firewall {
    private RuleManager ruleManager;

    private boolean isActive;

    public Firewall() {
        isActive = true;
        ruleManager = new RuleManager();
    }

    public void enable() {
        isActive = true;
    }

    public void disable() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public RuleManager getRuleManager() {
        return ruleManager;
    }

    public boolean isAllowPacket(String sourceIP, String destIP, String port) {
        ArrayList<Rule> rules = ruleManager.getRules();

        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);

            String ruleSourceIP = rule.getSourceIP();
            String ruleDestIP = rule.getDestIP();
            String rulePort = rule.getPort();

            // cannot compare the value of two string with ==
            // // equalling -1 means any
            // boolean cond1 = (sourceIP == ruleSourceIP) || (ruleSourceIP.equals("-1")); // sourceIP
            // boolean cond2 = (destIP == rule.getDestIP()) || (ruleDestIP.equals("-1")); // destIP
            // boolean cond3 = (port == rulePort) || (rulePort.equals("-1")); // port

            // use .equals() instead of ==
            // equalling -1 means any
            boolean cond1 = (sourceIP.equals(ruleSourceIP)) || (ruleSourceIP.equals("-1")); // sourceIP
            boolean cond2 = (destIP.equals(rule.getDestIP())) || (ruleDestIP.equals("-1")); // destIP
            boolean cond3 = (port.equals(rulePort)) || (rulePort.equals("-1")); // port

            if (cond1 && cond2 && cond3) {
                return rule.isAllow();
            }

        }
        return true;
    }

}
