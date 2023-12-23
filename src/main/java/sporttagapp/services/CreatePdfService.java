package sporttagapp.pdfgen.services;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;

import sporttagapp.pdfgen.dataclasses.Riegenblatt;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

@Service
public class CreatePdfService {

	public ByteArrayOutputStream getPdf() throws IOException {
		// Creating PDF document object
		PDDocument document = new PDDocument();

		PDPage blankPage = new PDPage();

		// Adding the blank page to the document
		document.addPage(blankPage);

		ByteArrayOutputStream ous = new ByteArrayOutputStream();

		document.save(ous);
		document.close();
		return ous;
	}

	public ByteArrayOutputStream createRiegenPdf() throws IOException {// Riegenblatt riegenblatt) throws IOException {
		// Creating PDF document object
		PDDocument document = new PDDocument();

		PDPage blankPage = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));

		// Adding the blank page to the document
		document.addPage(blankPage);
		// Retrieving the pages of the document
		PDPage page = document.getPage(0);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// Begin the Content stream
		contentStream.beginText();

		// Setting the font to the Content stream
		contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);

		// Setting the position for the line
		contentStream.newLineAtOffset(25, 500);

		String text = "This is the sample document and we are adding content to it.";

		// Adding text in the form of string
		contentStream.showText(text);

		// Ending the content stream
		contentStream.endText();

		contentStream.addRect(50, 50, 100, 100);
		contentStream.setStrokingColor(Color.DARK_GRAY);
        contentStream.stroke();

		System.out.println("Content added");

		// Closing the content stream
		contentStream.close();

		ByteArrayOutputStream ous = new ByteArrayOutputStream();

		document.save(ous);
		document.close();
		return ous;
	}

}