package me.aziah.midstforth;

public enum Sounds {
    ALARM("station_alarm"),
    SIGNAL_DETECTED("signal_detected"),
    BACKUP_POWER_ON("backup_power"),
    ACCELERATOR_START("accelerator_charge"),
    ACCELERATOR_ON("accelerator_enabled"),
    LAB_EVENT1("lab1_event1");


    private final String name;
    Sounds(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
