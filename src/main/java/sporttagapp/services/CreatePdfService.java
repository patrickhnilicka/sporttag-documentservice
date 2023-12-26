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

		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		FOUserAgent userAgent = fopFactory.newFOUserAgent();

		// set output format
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, pdfOutputStream);

		// Load template
		TransformerFactory transformerFactory = TransformerFactory.newInstance("net.sf.saxon.BasicTransformerFactory",
				null);
		Transformer transformer = transformerFactory
				.newTransformer(new StreamSource(new File("src/main/resources/static/Riegenblatt_template.xsl")));

		// Set value of parameters in stylesheet
		transformer.setParameter("version", "2.0");

		// Input for XSLT transformations
		Source xmlSource = new StreamSource(new File("src/main/resources/static/data.xml"));

		Result result = new SAXResult(fop.getDefaultHandler());

		transformer.transform(xmlSource, result);
	}

}