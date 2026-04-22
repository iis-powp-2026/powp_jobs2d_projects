package edu.kis.powp.jobs2d.drivers.packet_composite;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.ArrayList;
import java.util.List;


public class CompositeDriver implements Job2dDriver {
    private List<Job2dDriver> drivers;
    private final String name;

    public CompositeDriver(String name) {
        this.name = name;
        this.drivers = new ArrayList<>();
    }

    public CompositeDriver(String name, List<Job2dDriver> drivers) {
        this.name = name;
        this.drivers = new ArrayList<>(drivers);
    }

    public CompositeDriver(){
        this("Composite Driver");
    }

    public void addDriver(Job2dDriver driver) {
        if(drivers == null) {
            throw new IllegalArgumentException("Drivers cannot be null");
        }
        else if(drivers == this){
            throw new IllegalArgumentException("Cannot add itself as a driver");
        }
        drivers.add(driver);
    }

    public boolean removeDriver(Job2dDriver driver) {
        if(drivers == null) {
            throw new IllegalArgumentException("Drivers cannot be null");
        }
        return drivers.remove(driver);
    }

    public List<Job2dDriver> getDrivers() {
        return drivers;
    }

    public int getDriverCount() {
        return drivers.size();
    }



    @Override
    public void setPosition(int x, int y) {
        for (Job2dDriver driver : drivers) {
            driver.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (Job2dDriver driver : drivers) {
            driver.operateTo(x, y);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}