package sporttagapp.pdfgen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.http.HttpHeaders;

import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import sporttagapp.pdfgen.services.CreateExcelService;
import sporttagapp.pdfgen.services.CreatePdfService;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @Autowired
    private CreatePdfService createPdfService;
    @Autowired
    private CreateExcelService createExcelService;

    @GetMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(value = "/get-pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public  ResponseEntity<InputStreamResource>  getPdf() throws IOException {
        ByteArrayInputStream inputStream = null;
        try (ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream()) {
            createPdfService.getPdf(pdfOutput);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.pdf");

            byte[] bytes = pdfOutput.toByteArray();

            inputStream = new ByteArrayInputStream(bytes);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(inputStream));

        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (TransformerException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return ResponseEntity.ok().build();
        //return IOUtils.toByteArray(in); 
    }

    @GetMapping(value = "/get-excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public  ResponseEntity<InputStreamResource>  getExcel() throws IOException {
        ByteArrayInputStream inputStream = null;
        try (ByteArrayOutputStream excelOutput = new ByteArrayOutputStream()) {
            createExcelService.getExcel(excelOutput);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.xlsx");

            byte[] bytes = excelOutput.toByteArray();

            inputStream = new ByteArrayInputStream(bytes);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new InputStreamResource(inputStream));

        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return ResponseEntity.ok().build();
        //return IOUtils.toByteArray(in); 
    }

}