package org.sporttagapp.documentservice.services.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.sporttagapp.documentservice.dataclasses.SportklasseStudent;
import org.sporttagapp.documentservice.dataclasses.Student;
import org.springframework.stereotype.Service;

@Service
public class CreateExcelService {
    public void getExcel(OutputStream out)
            throws IOException {
        System.out.println("Create pdf file ...");

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Riegeneinteilung für den Sporttag");

        // Write the output to stream
        wb.write(out);
        out.close();

        wb.close();
    }

    public void getSportlehrerExcel(Map<String, SportklasseStudent> sportklasseStudentMap, OutputStream out)
            throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(out);

        Iterator<Map.Entry<String, SportklasseStudent>> iterator = sportklasseStudentMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SportklasseStudent> entry = iterator.next();
            String sportklasse = entry.getKey();
            SportklasseStudent sportklasseStudent = entry.getValue();
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();

            // Create header row
            Row header = sheet.createRow(0);

            Cell headerCellId = header.createCell(0);
            headerCellId.setCellValue("Id");
            Cell headerCellName = header.createCell(1);
            headerCellName.setCellValue("Name");
            Cell headerCellVorname = header.createCell(2);
            headerCellVorname.setCellValue("Vorname");
            Cell headerCellSportklasse = header.createCell(3);
            headerCellSportklasse.setCellValue("Sportklasse");
            Cell headerCellRiege = header.createCell(4);
            headerCellRiege.setCellValue("Riege");

            // Add entries
            int rowNr = 1;
            for (Student student : sportklasseStudent.students()){
                Row row = sheet.createRow(rowNr);
                Cell cellId = row.createCell(0);
                cellId.setCellValue(student.id());
                Cell cellName = row.createCell(1);
                cellName.setCellValue(student.nachname());
                Cell cellVorname = row.createCell(2);
                cellVorname.setCellValue(student.vorname());
                Cell cellSportklasse = row.createCell(3);
                cellSportklasse.setCellValue(sportklasseStudent.klassenname());
                ++rowNr;
            }

            // Add Excelfile to Zip
            ZipEntry zipEntry = new ZipEntry(sportklasse + ".xlsx");
            zipOut.putNextEntry(zipEntry);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            zipOut.write(os.toByteArray());
            wb.close();
            os.close();
        }
        zipOut.close();
        out.close();
    }

}
