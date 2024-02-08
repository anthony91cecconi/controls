package com.tests.generic.entitis;

public class PysicalDbEntity {
    private String physical_id;

    public PysicalDbEntity(String py) {
        this.physical_id = py;
    }

    public String getPhysical() {
        return this.physical_id;
    }
}
