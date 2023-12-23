package sporttagapp.pdfgen.services;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.*;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class CreatePdfService {

	public void getPdf(OutputStream pdfOutputStream)
			throws IOException, SAXException, TransformerException {
		System.out.println("Create pdf file ...");
		File tempFile = File.createTempFile("fop-" + System.currentTimeMillis(), ".pdf");

		// holds references to configuration information and cached data
		// reuse this instance if you plan to render multiple documents
		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		FOUserAgent userAgent = fopFactory.newFOUserAgent();

		try {
			// set output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, pdfOutputStream);

			// Load template
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory
					.newTransformer(new StreamSource(new File("src/main/resources/static/Riegenblatt_template.xsl")));

			// Set value of parameters in stylesheet
			transformer.setParameter("version", "1.0");

			// Input for XSLT transformations
			Source xmlSource = new StreamSource(new File("src/main/resources/static/data.xml"));

			Result result = new SAXResult(fop.getDefaultHandler());

			transformer.transform(xmlSource, result);
		} finally {
			tempFile.delete();
		}
	}

	/*
	 * public ByteArrayOutputStream createRiegenPdf() throws IOException {//
	 * Riegenblatt riegenblatt) throws IOException {
	 * // Creating PDF document object
	 * PDDocument document = new PDDocument();
	 * 
	 * PDPage blankPage = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(),
	 * PDRectangle.A4.getWidth()));
	 * 
	 * // Adding the blank page to the document
	 * document.addPage(blankPage);
	 * // Retrieving the pages of the document
	 * PDPage page = document.getPage(0);
	 * PDPageContentStream contentStream = new PDPageContentStream(document, page);
	 * 
	 * // Begin the Content stream
	 * contentStream.beginText();
	 * 
	 * // Setting the font to the Content stream
	 * contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
	 * 
	 * // Setting the position for the line
	 * contentStream.newLineAtOffset(25, 500);
	 * 
	 * String text = "This is the sample document and we are adding content to it.";
	 * 
	 * // Adding text in the form of string
	 * contentStream.showText(text);
	 * 
	 * // Ending the content stream
	 * contentStream.endText();
	 * 
	 * contentStream.addRect(50, 50, 100, 100);
	 * contentStream.setStrokingColor(Color.DARK_GRAY);
	 * contentStream.stroke();
	 * 
	 * System.out.println("Content added");
	 * 
	 * // Closing the content stream
	 * contentStream.close();
	 * 
	 * ByteArrayOutputStream ous = new ByteArrayOutputStream();
	 * 
	 * document.save(ous);
	 * document.close();
	 * return ous;
	 * }
	 */
}