package packetfilter.example.javapacketfiltering.packetfilteringapp;

import java.util.ArrayList;

public class RuleManager {
    private ArrayList<Rule> rules;

    public RuleManager() {
        rules = new ArrayList<Rule>();
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public boolean addRule(Rule rule) {
        return rules.add(rule);
    }

    public boolean removeRule(Rule rule) {
        return rules.remove(rule);
    }
}
