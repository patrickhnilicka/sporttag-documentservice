package sporttagapp.pdfgen.services;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

}
