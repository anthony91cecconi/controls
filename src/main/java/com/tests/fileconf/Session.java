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
    private String asset;
    private List<FileConfEntity> fConf;
    private List<PysicalDbEntity> physicalsDb;
    private List<String> physicalsCsv;
    private ConfToConf controllerConf;
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
                    sessionMax();
                    menu();
                    break;
                default:
                    break;
            }
        }
    }

    private void sessionMax() throws SQLException, IOException {
        int count = 0;
        while (true) {
            if (count > 0 && !Utility.yesOrNo("ancora ? si o no?")) {
                break;
            }
            // connessione con file di configurazione e db
            newFConf();

            conDb = new ConnectorDbMysql();

            // recupero dati e confronti
            physicalsDb = conDb.getAllPhysical_id(asset);
            controllerConf = new ConfToConf(fConf);
            controllerConf.controlDuplicatesPhysicalId();
            controllerConf.controlDuplicatesSerial();
            controllerConfDb = new ConfToDbAndCsv(fConf, physicalsDb, physicalsCsv);
            controllerConfDb.confVsDb();
            controllerConfDb.confVsCsv();

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
            String ora = sdf.format(date);

            File file = new File("Disallineamenti(db_fconf)-" + asset + "_" + ora + ".txt");

            FileWriter writer = new FileWriter(file);

            // -------------------------------------------------------------------------------------------------------
            writer.write(duplicatePhysical() + "\n\n\n");
            // -------------------------------------------------------------------------------------------------------
            writer.write(duplicateSerial() + "\n\n\n");
            // -------------------------------------------------------------------------------------------------------
            writer.write(DBmissing() + "\n\n\n");
            // -------------------------------------------------------------------------------------------------------

            writer.close();

            Utility.logInfo("writer chiuso, file txt generato");

            writer.close();
            Utility.logInfo("writer chiuso");
            Main.store.addFilesConfs(new FileConfComplete(fConf, asset));
            count++;
        }

    }

    private void session() throws SQLException, IOException {

        FileExcelsEstractor fc = new FileExcelsEstractor();
        physicalsCsv = fc.getPhysical_idCsv();

        // connessione con file di configurazione e db
        while (true) {
            try {
                fConf = fc.getPhysical_idToXlxs("file di configurazione");
                break;
            } catch (Exception e) {
                System.out.println(
                        "questo percorso sembra avere problemi, prova a rinominare il file se persiste e riprova");
            }
        }

        conDb = new ConnectorDbMysql();

        // controllo se l'asset è stato estrapolato correttamente dal file di
        // configurazione
        if (fc.getAsset() != null && fc.getAsset().length() == 11) {
            asset = fc.getAsset();
        } else {
            System.out.println("questo asset non sembra corretto inserire asset");
            asset = Utility.getString("asset");
        }

        // recupero dati e confronti
        physicalsDb = conDb.getAllPhysical_id(asset);
        controllerConf = new ConfToConf(fConf);
        controllerConf.controlDuplicatesPhysicalId();
        controllerConf.controlDuplicatesSerial();
        controllerConfDb = new ConfToDbAndCsv(fConf, physicalsDb, physicalsCsv);
        controllerConfDb.confVsDb();
        controllerConfDb.confVsCsv();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
        String ora = sdf.format(date);

        File file = new File("Disallineamenti-" + asset + "_" + ora + ".txt");

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

        Utility.logInfo("writer chiuso, file txt generato");

        writer.close();
        Utility.logInfo("writer chiuso");
        Main.store.addFilesConfs(new FileConfComplete(fConf, asset));
    }

    public String duplicatePhysical() {
        String aggDuplicatePhysical = "";
        Utility.logInfo("File di configurazione : Physical_id duplicati - calcolati");
        for (int i = 0; i < fConf.size(); i++) {
            if (fConf.get(i).getPhysicalDuplicate()) {
                aggDuplicatePhysical += fConf.get(i).getPhysical_id() + " ; ";
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
        for (int i = 0; i < fConf.size(); i++) {
            if (fConf.get(i).getSerialDuplicate()) {
                aggDuplicateSerial += fConf.get(i).getNumeroSeriale() + " ; ";
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
        for (int i = 0; i < fConf.size(); i++) {
            if (!fConf.get(i).getIsInCsv() && !fConf.get(i).iterable()) {
                aggCsvMissing += fConf.get(i).getPhysical_id() + " ; ";
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
            for (int j = 0; j < fConf.size(); j++) {

                if (!physicalsCsv.get(i).toUpperCase().equals(fConf.get(j).getPhysical_id().toUpperCase())) {
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
        for (int i = 0; i < fConf.size(); i++) {
            if (!fConf.get(i).getIsInDb()) {
                aggDBmissing += fConf.get(i).getPhysical_id() + " ; ";
            }
        }

        if (aggDBmissing.length() == 0) {
            aggDBmissing = "NESSUN PHYSICAL_ID ERRATO";
        }

        return "Database : Physical_id errati \n" + aggDBmissing;
    }

    public void newFConf() {
        FileExcelsEstractor fc = new FileExcelsEstractor();
        while (true) {
            try {
                fConf = fc.getPhysical_idToXlxs("file di configurazione");
                break;
            } catch (Exception e) {
                System.out.println(
                        "questo percorso sembra avere problemi, prova a rinominare il file se persiste e riprova");
                newFConf();
            }
        }
        // controllo se l'asset è stato estrapolato correttamente dal file di
        // configurazione
        if (fc.getAsset() != null && fc.getAsset().length() == 11) {
            asset = fc.getAsset();
        } else {
            System.out.println("questo asset non sembra corretto inserire asset");
            asset = Utility.getString("asset");
        }
    }
}
