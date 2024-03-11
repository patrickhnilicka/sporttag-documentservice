package org.sporttagapp.documentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sporttagapp.documentservice.services.services.CreateExcelService;
import org.sporttagapp.documentservice.services.services.CreatePdfService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.sporttagapp.documentservice.dataclasses.Student;
import org.sporttagapp.documentservice.services.services.ExcelDataService;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private CreatePdfService createPdfService;
    private CreateExcelService createExcelService;
    private ExcelDataService excelDataService;

    public HelloController(CreatePdfService createPdfService, CreateExcelService createExcelService, ExcelDataService excelDataService) {
        this.createPdfService = createPdfService;
        this.createExcelService = createExcelService;
        this.excelDataService = excelDataService;
    }

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
            //headers.add("Content-Disposition", "inline; filename=example.pdf");

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

    @PostMapping(value = "/get-students")
    public String getStudentsFromExcel(@RequestParam("file")MultipartFile file) throws Exception {
        List<Student> students;
        students = excelDataService.getStudentDataFromExcel(file.getInputStream());
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(students);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}