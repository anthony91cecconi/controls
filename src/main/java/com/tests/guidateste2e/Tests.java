package com.tests.guidateste2e;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Tests {
    String asset;

    public void session() throws IOException {
        this.asset = "test";
        try (// creazione file excell
                XSSFWorkbook workbook = new XSSFWorkbook()) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
            String ora = sdf.format(date);
            XSSFSheet sheet = workbook.createSheet();
            sheet.setColumnWidth(0, 2500);
            sheet.setColumnWidth(1, 20000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 20000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 10000);
            sheet.setColumnWidth(8, 5000);

            t("questo è un tutorial passo passo che ti guiderà nei test E2E di un edge e scrivera l'excel per te");

            XSSFCellStyle intestazioneStiStyle = workbook.createCellStyle();
            intestazioneStiStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            intestazioneStiStyle.setBorderBottom(BorderStyle.THIN);
            intestazioneStiStyle.setBorderLeft(BorderStyle.THIN);
            intestazioneStiStyle.setBorderRight(BorderStyle.THIN);
            intestazioneStiStyle.setBorderTop(BorderStyle.THIN);
            intestazioneStiStyle.setAlignment(HorizontalAlignment.CENTER);
            XSSFRow row = sheet.createRow(0); // Crea la prima riga
            String[] row1 = { "Step", "Test Interoperabilità", "Data verifica", "Esito verifica", "Problema rilevato",
                    "Gravità", "Azione correttiva richiesta", "Commenti" };
            for (int i = 0; i < row1.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(row1[i]);
                cell.setCellStyle(intestazioneStiStyle);
            }

            // SECONDA RIGA
            XSSFRow row2 = sheet.createRow(1);
            XSSFCellStyle bigCell = workbook.createCellStyle();
            bigCell.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            bigCell.setBorderBottom(BorderStyle.THIN);
            bigCell.setBorderLeft(BorderStyle.NONE);
            bigCell.setBorderRight(BorderStyle.NONE);
            bigCell.setBorderTop(BorderStyle.THIN);
            bigCell.setAlignment(HorizontalAlignment.CENTER);

            for (int i = 0; i < row1.length; i++) {
                XSSFCell cell = row2.createCell(0);
                cell.setCellStyle(bigCell);
                if (i == 4) {
                    cell.setCellValue("EDGE");
                }
            }

            FileOutputStream out = new FileOutputStream("controlli-" + asset + "del-" + ora + ".xlsx");
            workbook.write(out);
            out.close();
        }
    }

    public void t(String t) {
        System.out.println(t);
    }

}
