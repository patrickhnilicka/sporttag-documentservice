package org.sporttagapp.documentservice.services.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sporttagapp.documentservice.dataclasses.Riegenzuteilung;
import org.springframework.stereotype.Service;
import org.sporttagapp.documentservice.dataclasses.ExcelStudent;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelDataService {
    Logger logger = LoggerFactory.getLogger(ExcelDataService.class);

    private static String gender = "gender";
    private static String nachname = "name";
    private static String vorname = "vorname";
    private static String klasseZahl = "klasse zahl";
    private static String klasseBuchstabe = "klasse buchstabe";
    private static String geburtstag = "geburtstag";
    private static String sportklasse = "sportklasse";
    private static String lehrperson = "lp sporttag";
    private static String id = "id";
    private static String riege = "riege";

    private static List<String> studentExcelColumnHeaders = new ArrayList<>(Arrays.asList(gender, nachname, vorname, klasseZahl,
            klasseBuchstabe, geburtstag, sportklasse, lehrperson));
    private static List<String> riegenzuteilungExcelColumnHeaders = new ArrayList<>(Arrays.asList(id, vorname, nachname, sportklasse, riege));

    public List<ExcelStudent> getStudentDataFromExcel(InputStream file) throws Exception {
        Map<String, Integer> columnNameToColumnIndexMap = new HashMap<>();
        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet ws = wb.getSheetAt(0);
        Iterator<Row> rowIterator = ws.iterator();

        Row headerRow = rowIterator.next();
        Iterator<Cell> firstRowCellIterator = headerRow.cellIterator();

        while (firstRowCellIterator.hasNext()) {
            Cell cell = firstRowCellIterator.next();
            String cellValue = cell.getStringCellValue().trim().toLowerCase();
            if (studentExcelColumnHeaders.contains(cellValue)) {
                columnNameToColumnIndexMap.put(cellValue, cell.getColumnIndex());
            } else {
                throw new Exception("Tabellenheader ist nicht im richtigen Format: " + cellValue + " nicht gefunden.");
            }
        }

        List<ExcelStudent> students = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if(row.getCell(columnNameToColumnIndexMap.get(vorname)).getStringCellValue().trim() == "")
                break;
            String geburtstag_string = "";
            try{
                geburtstag_string = dateFormat.format(row.getCell(columnNameToColumnIndexMap.get(geburtstag)).getDateCellValue());
            } catch (Exception e){
                throw new Exception("Fehler beim Parsen des Geburtsdatums auf Zeile " + row.getRowNum());
            }

            students.add(new ExcelStudent(
                    row.getCell(columnNameToColumnIndexMap.get(gender)).getStringCellValue(),
                    row.getCell(columnNameToColumnIndexMap.get(vorname)).getStringCellValue(),
                    row.getCell(columnNameToColumnIndexMap.get(nachname)).getStringCellValue(),
                    ((int) row.getCell(columnNameToColumnIndexMap.get(klasseZahl)).getNumericCellValue()),
                    row.getCell(columnNameToColumnIndexMap.get(klasseBuchstabe)).getStringCellValue(),
                    geburtstag_string,
                    row.getCell(columnNameToColumnIndexMap.get(sportklasse)).getStringCellValue(),
                    row.getCell(columnNameToColumnIndexMap.get(lehrperson)).getStringCellValue()

            ));

            System.out.println("Reading File Completed.");
        }
        file.close();
        return students;
    }

    public List<Riegenzuteilung> getRiegenzuteilungFromExcel(InputStream file) throws Exception {
        Map<String, Integer> columnNameToColumnIndexMap = new HashMap<>();
        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet ws = wb.getSheetAt(0);
        Iterator<Row> rowIterator = ws.iterator();

        Row headerRow = rowIterator.next();
        Iterator<Cell> firstRowCellIterator = headerRow.cellIterator();

        while (firstRowCellIterator.hasNext()) {
            Cell cell = firstRowCellIterator.next();
            String cellValue = cell.getStringCellValue().trim().toLowerCase();
            if (riegenzuteilungExcelColumnHeaders.contains(cellValue.toLowerCase())) {
                columnNameToColumnIndexMap.put(cellValue, cell.getColumnIndex());
            } else {
                throw new Exception("Tabellenheader ist nicht im richtigen Format: " + cellValue + " nicht gefunden.");
            }
        }

        List<Riegenzuteilung> riegenzuteilungs = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if(row.getCell(columnNameToColumnIndexMap.get(vorname)).getStringCellValue().trim() == "")
                break;
            Long id = 0L, riegeId = 0L;
            try{
                id = Long.parseLong(row.getCell(columnNameToColumnIndexMap.get(this.id)).getStringCellValue());
                riegeId = Long.parseLong(row.getCell(columnNameToColumnIndexMap.get(this.riege)).getStringCellValue());
            } catch (Exception e){
                logger.error("Fehler beim Parsen der Zeile " + row.getRowNum());
            }
            riegenzuteilungs.add(new Riegenzuteilung(
                    id,
                    riegeId
            ));

            System.out.println("Reading File Completed.");
        }
        file.close();
        return riegenzuteilungs;
    }
}
