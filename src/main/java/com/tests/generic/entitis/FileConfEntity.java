package com.tests.generic.entitis;

public class FileConfEntity {
    private String nome;
    private String physical_id;
    private String numeroSeriale;
    private String numeroColonna;
    private String asset;
    // private String ucl;

    // i problemi
    // il physical_is è ripetuto
    private boolean physicalDuplicate;

    // il seriale è ripetuto
    private boolean serialDuplicate;

    // esiste nel csv
    private boolean isInCsv;

    // esiste nel DB
    private boolean isInDb;

    public FileConfEntity(String nome, String physical_id, String numeroSeriale, String numeroColonna, String ucl,
            String asset) {
        this.nome = nome;
        this.physical_id = physical_id;
        this.numeroSeriale = numeroSeriale;
        try {
            this.numeroColonna = numeroColonna;
        } catch (Exception e) {
            System.out.println("qui ci sono problemi");
        }
        this.asset = asset;
        // this.ucl = ucl;
        this.physicalDuplicate = false;
        this.serialDuplicate = false;
        this.isInCsv = false;
        this.isInDb = false;
    }

    @Override
    public String toString() {
        return nome + " | " + physical_id + " | " + numeroSeriale;
    }

    public String getPhysical_id() {
        return physical_id;
    }

    public void isClone() {
        this.physicalDuplicate = true;
    }

    public boolean getPhysicalDuplicate() {
        return this.physicalDuplicate;
    }

    public String getNumeroSeriale() {
        return this.numeroSeriale;
    }

    public boolean getSerialDuplicate() {
        return serialDuplicate;
    }

    public void serialDuplicate() {
        this.serialDuplicate = true;
    }

    public String getName() {
        return this.nome;
    }

    public String getNumeroColonna() {
        return numeroColonna;
    }

    public boolean getIsInCsv() {
        return this.isInCsv;
    }

    public void setIsInCsv() {
        this.isInCsv = true;
    }

    public void setIsInDb() {
        this.isInDb = true;
    }

    public boolean getIsInDb() {
        return this.isInDb;
    }

    public boolean iterable() {
        return (this.numeroColonna.equals("0") || this.numeroColonna.equals("0.0") || this.numeroColonna.equals("0,0"));
    }
}
