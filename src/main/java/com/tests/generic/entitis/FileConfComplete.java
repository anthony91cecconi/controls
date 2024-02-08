package com.tests.generic.entitis;

import java.util.List;

public class FileConfComplete {
    private List<FileConfEntity> sensors;
    private String asset;

    public FileConfComplete(List<FileConfEntity> sensors, String asset) {
        this.sensors = sensors;
        this.asset = asset;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public List<FileConfEntity> getSensors() {
        return sensors;
    }

    public void setSensors(List<FileConfEntity> sensors) {
        this.sensors = sensors;
    }
}
