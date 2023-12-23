package sporttagapp.pdfgen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.io.InputStreamResource;

import sporttagapp.pdfgen.services.CreatePdfService;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @Autowired
    private CreatePdfService createPdfService;

    @GetMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(value = "/get-pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public  ResponseEntity<InputStreamResource>  getPdf() throws IOException {
        ByteArrayInputStream inputStream = null;
        try (ByteArrayOutputStream ous = createPdfService.createRiegenPdf()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.pdf");

            byte[] bytes = ous.toByteArray();

            inputStream = new ByteArrayInputStream(bytes);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
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