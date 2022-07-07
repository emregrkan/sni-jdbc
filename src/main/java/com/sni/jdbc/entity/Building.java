package com.sni.jdbc.entity;

public class Building {

    private Integer id;
    private String address;
    private Integer workingEmployees;

    public Building() {
    }

    public Building(Integer id, String address, Integer workingEmployees) {
        this.id = id;
        this.address = address;
        this.workingEmployees = workingEmployees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getWorkingEmployees() {
        return workingEmployees;
    }

    public void setWorkingEmployees(Integer workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    @Override
    public String toString() {
        return "Building{" + "id=" + id + ", address='" + address + '\'' + ", workingEmployees=" + workingEmployees + '}';
    }
}
