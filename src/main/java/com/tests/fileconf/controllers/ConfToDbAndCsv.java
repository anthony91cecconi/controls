package com.tests.fileconf.controllers;

import java.util.List;

import com.tests.generic.entitis.FileConfEntity;
import com.tests.generic.entitis.PysicalDbEntity;

public class ConfToDbAndCsv {
    private List<FileConfEntity> fc;
    private List<PysicalDbEntity> pd;
    private List<String> csv;

    public ConfToDbAndCsv(List<FileConfEntity> fc, List<PysicalDbEntity> pd, List<String> csv) {
        this.fc = fc;
        this.pd = pd;
        this.csv = csv;
    }

    /*
     * questo metodo confronta i physical del db, id con quelli del file di
     * connfigurazione
     */
    public void confVsDb() {
        for (FileConfEntity entityFc : fc) {
            for (PysicalDbEntity entityPd : pd) {
                if (entityFc.getPhysical_id().toUpperCase().equals(entityPd.getPhysical().toUpperCase())) {
                    entityFc.setIsInDb();
                    break;
                }
            }
        }

    }

    /*
     * cerifica che i csv del file di configurazione siano nel csv
     */
    public void confVsCsv() {
        for (FileConfEntity entityFc : fc) {
            for (String phys : csv) {
                if (entityFc.getPhysical_id().toUpperCase().equals(phys.toUpperCase())) {
                    entityFc.setIsInCsv();
                    break;
                }
            }
        }
    }

    /*
     * cerifica che i physical_id del csv siano nel file di configurazione
     * 
     * TODO: IMPLEMENTARE CONTROLLO SE IL CSV CONTIENE PHYSICAL_ID INESISTENTI O DI
     * ALTRI PONTI.
     * 
     */

}
