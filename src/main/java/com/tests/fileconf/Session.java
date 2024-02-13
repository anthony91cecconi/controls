package com.tests.fileconf;

import java.io.FileWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tests.Main;
import com.tests.fileconf.controllers.ConfToConf;
import com.tests.fileconf.controllers.ConfToDbAndCsv;
import com.tests.generic.entitis.FileConfComplete;
import com.tests.generic.entitis.FileConfEntity;
import com.tests.generic.entitis.PysicalDbEntity;
import com.tests.generic.repository.ConnectorDbMysql;
import com.tests.generic.repository.FileExcelsEstractor;
import com.tests.utility.Utility;
import java.io.IOException;
//per i csv
import java.io.File;

public class Session {
    private FileConfComplete conflocked;
    private List<PysicalDbEntity> physicalsDb;
    private List<String> physicalsCsv;
    private ConfToDbAndCsv controllerConfDb;
    private ConnectorDbMysql conDb;

    public void menu() throws SQLException, IOException {
        while (true) {
            System.out.println("-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<");
            System.out.println("-<-<-<-<-<-<-<-servizio<-<-<-<-<-<-<-<-<");
            System.out.println("-<-<-<-<-<-<di controllo dei-<-<-<-<-<-<");
            System.out.println("-<-<-<-<-<-<-<-seriali-<-<-<-<-<-<-<-<-<");
            System.out.println("-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<\n\n");

            System.out
                    .println("1.controllare un ponte\n2.controllare più ponti (solo db e file di configurazione)");
            switch (Utility.getNumber(1, 2)) {
                case 1:
                    session();
                    menu();
                    break;
                case 2:
                    sessionMax(false);
                    menu();
                    break;
                default:
                    break;
            }
        }
    }

    public void sessionMax(boolean onlyOne) throws SQLException, IOException {
        int count = 0;
        while (true) {
            if (count > 0 && !Utility.yesOrNo("ancora ? si o no?") || !onlyOne) {
                break;
            }
            // connessione con file di configurazione e db
            this.conflocked = newFConf();

            conDb = new ConnectorDbMysql();

            // recupero dati e confronti
            physicalsDb = conDb.getAllPhysical_id(this.conflocked.getAsset());
            controllerConfDb = new ConfToDbAndCsv(conflocked.getSensors(), physicalsDb, physicalsCsv);
            controllerConfDb.confVsDb();
            controllerConfDb.confVsCsv();

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
            String ora = sdf.format(date);

            File file = new File("Disallineamenti(db_fconf)-" + this.conflocked.getAsset() + "_" + ora + ".txt");

            FileWriter writer = new FileWriter(file);

            // -------------------------------------------------------------------------------------------------------
            writer.write(duplicatePhysical() + "\n\n\n");
            // -------------------------------------------------------------------------------------------------------
            writer.write(duplicateSerial() + "\n\n\n");
            // -------------------------------------------------------------------------------------------------------
            writer.write(DBmissing() + "\n\n\n");
            // -------------------------------------------------------------------------------------------------------

            writer.close();
            Utility.logInfo("writer chiuso");
            Main.store.addFilesConfs(new FileConfComplete(conflocked.getSensors(), this.conflocked.getAsset()));
            count++;
        }

    }

    public void session() throws SQLException, IOException {

        FileExcelsEstractor fc = new FileExcelsEstractor();
        physicalsCsv = fc.getPhysical_idCsv();

        this.conflocked = newFConf();

        conDb = new ConnectorDbMysql();

        // recupero dati e confronti
        physicalsDb = conDb.getAllPhysical_id(this.conflocked.getAsset());
        controllerConfDb = new ConfToDbAndCsv(conflocked.getSensors(), physicalsDb, physicalsCsv);
        controllerConfDb.confVsDb();
        controllerConfDb.confVsCsv();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
        String ora = sdf.format(date);

        File file = new File("Disallineamenti-" + conflocked.getAsset() + "_" + ora + ".txt");

        FileWriter writer = new FileWriter(file);

        // -------------------------------------------------------------------------------------------------------
        writer.write(duplicatePhysical() + "\n\n\n");
        // -------------------------------------------------------------------------------------------------------
        writer.write(duplicateSerial() + "\n\n\n");
        // -------------------------------------------------------------------------------------------------------
        writer.write(csvMissing() + "\n\n\n");
        // -------------------------------------------------------------------------------------------------------
        writer.write(csvError() + "\n\n\n");
        // -------------------------------------------------------------------------------------------------------
        writer.write(DBmissing() + "\n\n\n");
        // -------------------------------------------------------------------------------------------------------

        writer.close();
        Utility.logInfo("writer chiuso");
        Main.store.addFilesConfs(new FileConfComplete(conflocked.getSensors(), conflocked.getAsset()));
    }

    public String duplicatePhysical() {
        String aggDuplicatePhysical = "";
        Utility.logInfo("File di configurazione : Physical_id duplicati - calcolati");
        for (int i = 0; i < conflocked.getSensors().size(); i++) {
            if (conflocked.getSensors().get(i).getPhysicalDuplicate()) {
                aggDuplicatePhysical += conflocked.getSensors().get(i).getPhysical_id() + " ; ";
            }
        }
        if (aggDuplicatePhysical.length() == 0) {
            aggDuplicatePhysical = "NESSUN PHYSICAL_ID DUPLICATO";
        }
        return "File di configurazione : Physical_id duplicati \n" + aggDuplicatePhysical;

    }

    public String duplicateSerial() {
        String aggDuplicateSerial = "";
        Utility.logInfo(
                "File di configurazione : Seriali duplicati (esclusi i Sensori di Temperatura con lo stesso Seriale)");
        for (int i = 0; i < conflocked.getSensors().size(); i++) {
            if (conflocked.getSensors().get(i).getSerialDuplicate()) {
                aggDuplicateSerial += conflocked.getSensors().get(i).getNumeroSeriale() + " ; ";
            }
        }
        if (aggDuplicateSerial.length() == 0) {
            aggDuplicateSerial = "NESSUN SERIALE DUPLICATO";
        }
        return "File di configurazione : Seriali duplicati (esclusi i Sensori di Temperatura con lo stesso Seriale)\n"
                + aggDuplicateSerial;
    }

    public String csvMissing() {
        String aggCsvMissing = "";
        Utility.logInfo("calcolo physical_id mancanti nel csv");
        for (int i = 0; i < conflocked.getSensors().size(); i++) {
            if (!conflocked.getSensors().get(i).getIsInCsv() && !conflocked.getSensors().get(i).iterable()) {
                aggCsvMissing += conflocked.getSensors().get(i).getPhysical_id() + " ; ";
            }
        }
        if (aggCsvMissing.length() == 0) {
            aggCsvMissing = "NESSUN PHYSICAL_ID MANCANTE";
        }
        return "CSV dal Campo : Physical_id mancanti\n" + aggCsvMissing;
    }

    public String csvError() {
        String aggCsvError = "";
        Utility.logInfo("calcolo physical_id errati nel csv");
        List<String> calc = new ArrayList<String>();
        for (int i = 0; i < physicalsCsv.size(); i++) {
            boolean isIn = true;
            for (int j = 0; j < conflocked.getSensors().size(); j++) {

                if (!physicalsCsv.get(i).toUpperCase()
                        .equals(conflocked.getSensors().get(j).getPhysical_id().toUpperCase())) {
                    isIn = false;
                    break;
                }
            }
            if (isIn) {
                calc.add(physicalsCsv.get(i));
            }
        }
        if (calc.size() > 0) {
            for (int i = 0; i < calc.size(); i++) {
                aggCsvError += calc.get(i) + " ; ";
            }
        } else {
            aggCsvError = "NESSUN PHYSICAL_ID ERRATO";
        }

        return "CSV dal Campo : Physical_id errati \n" + aggCsvError;
    }

    public String DBmissing() {
        String aggDBmissing = "";
        Utility.logInfo("calcolo physical_id mancanti nel db");
        for (int i = 0; i < conflocked.getSensors().size(); i++) {
            if (!conflocked.getSensors().get(i).getIsInDb()) {
                aggDBmissing += conflocked.getSensors().get(i).getPhysical_id() + " ; ";
            }
        }

        if (aggDBmissing.length() == 0) {
            aggDBmissing = "NESSUN PHYSICAL_ID ERRATO";
        }

        return "Database : Physical_id errati \n" + aggDBmissing;
    }

    public FileConfComplete newFConf() {
        FileConfComplete conf = new FileConfComplete(null, "");
        FileExcelsEstractor fc = new FileExcelsEstractor();
        while (true) {
            try {
                conf.setSensors(fc.getPhysical_idToXlxs("file di configurazione"));
                conf.setAsset(fc.getAsset());
                break;
            } catch (Exception e) {
                System.out.println(
                        "questo percorso sembra avere problemi, prova a rinominare il file se persiste e riprova");
                newFConf();
            }
        }
        testAsset(conf);
        new ConfToConf(conf.getSensors());
        return conf;
    }

    public void testAsset(FileConfComplete conf) {
        // controllo se l'asset è stato estrapolato correttamente dal file di
        // configurazione
        if (conf.getAsset() != null && conf.getAsset().length() == 11) {
            Utility.logInfo("asset corretto " + conf.getAsset());
        } else {
            System.out.println("questo asset non sembra corretto inserire asset");
            conf.setAsset(Utility.getString("asset"));
        }
    }

    public void setConf(FileConfComplete conf) {
        this.conflocked = conf;
    }

    public FileConfComplete getConf() {
        return conflocked;
    }
}
