package com.tests.fileconf.controllers;

import java.util.List;

import com.tests.generic.entitis.FileConfEntity;

public class ConfToConf {
    private List<FileConfEntity> conf;

    public ConfToConf(List<FileConfEntity> conf) {
        this.conf = conf;
    }

    // questo metodo verifica che nel file di configurazione non ci siano
    // physical_id doppioni
    public void controlDuplicatesPhysicalId() {
        for (int i = 0; i < conf.size(); i++) {
            FileConfEntity sensor = conf.get(i);
            if (!sensor.getPhysicalDuplicate() && i < conf.size()) {
                for (int j = i + 1; j < conf.size(); j++) {
                    FileConfEntity sensor2 = conf.get(j);
                    if (sensor.getPhysical_id().equals(sensor2.getPhysical_id())) {
                        sensor.isClone();
                        sensor2.isClone();
                    }
                }
            }
        }
    }

    // questo metodo verifica che non ci siano seriali doppioni
    public void controlDuplicatesSerial() {
        for (int i = 0; i < conf.size(); i++) {
            FileConfEntity sensor = conf.get(i);
            int countSerial = 0;
            for (int j = 0; j < conf.size(); j++) {
                FileConfEntity sensor2 = conf.get(j);
                if (sensor.getNumeroSeriale().equals(sensor2.getNumeroSeriale())) {
                    countSerial++;
                    if (countSerial > 4) {
                        sensor.serialDuplicate();
                        sensor2.getSerialDuplicate();
                    }
                }
            }
        }
    }
}
