package packetfilter.example.javapacketfiltering.packetfilteringapp;

public class test {
    public static void main(String[] args) {
        Device device1 = new Device("1.1.1.1");

        device1.getFirewall().getRuleManager().addRule(new Rule("-1", "2.2.2.2", "-1", false));
        System.out.println(device1.sendTo("2.2.2.2", "25565"));
    }
}
