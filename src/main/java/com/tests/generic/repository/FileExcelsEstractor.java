package com.tests.generic.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tests.generic.entitis.FileConfEntity;
import com.tests.utility.Utility;

public class FileExcelsEstractor {
    private String asset;

    /*
     * recupera i physical_id dalla stringa fornita dall'utente
     * 
     * @ut @scanner di passaggio per fornire i metodi per ricevere la stringa
     */
    public List<String> getPhysical_idCsv() {
        String line = Utility.getString("i physical_id dinamici copiati dal csv");
        line += ";" + Utility.getString("i physical_id statici copiati dal csv");
        List<String> physicals = new ArrayList<String>();
        String[] colums = line.trim().split(";");
        for (int i = 0; i < colums.length; i++) {
            physicals.add(colums[i]);
        }
        return physicals;
    }

    /*
     * permette di estrapolare le informazioni dal file di configurazione in formato
     * xlxs
     * 
     * @ut @scanner di passaggio per fornire i metodi per ricevere la stringa
     * TODO:capire come migliorare il sistema e recuperare più dati utili
     * 
     */
    public List<FileConfEntity> getPhysical_idToXlxs(String file) throws IOException {
        System.out.print("inserire il path assoluto di " + file);
        String filePath = new File(Utility.getString(file))
                .getAbsolutePath();
        Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheetAt(0);
        List<FileConfEntity> files = new ArrayList<FileConfEntity>();

        int contatore = 0;
        // Iterare sulle righe del foglio
        for (Row row : sheet) {
            if (contatore == 0) {
                try {
                    this.asset = String.format("%.0f", row.getCell(1).getNumericCellValue());
                } catch (Exception e) {
                    System.out.println("asset code corrotto bisognera inserirlo manualmente");
                }
            }
            if (contatore >= 18) {
                String[] riga = new String[5];
                int count = 0;
                // Iterare sulle celle della riga
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    String cellValue = "";

                    if (cell != null) {
                        // Controllare il tipo di cella e convertire il valore in stringa
                        switch (cell.getCellType()) {
                            case STRING:
                                cellValue = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                cellValue = String.valueOf(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            default:
                                break;
                        }
                    }
                    if (i <= 1 || i == 5 || i == 11 || i == 19) {
                        riga[count] = cellValue;
                        count++;
                    }

                }

                if (!riga[0].isEmpty()) {
                    FileConfEntity conf = new FileConfEntity(riga[0], riga[1], riga[2], riga[3],
                            riga[4], asset);
                    files.add(conf);
                }

            }
            contatore++;
        }
        workbook.close();
        return files;
    }

    // GET e SETTER
    public String getAsset() {
        return asset;
    }

}
