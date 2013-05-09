package pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.pdfbox.exceptions.COSVisitorException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.edit.PDPageContentStream;
import org.pdfbox.pdmodel.font.PDFont;
import org.pdfbox.pdmodel.font.PDType1Font;
import org.pdfbox.pdmodel.graphics.xobject.PDJpeg;

/**
 * @author sander
 *
 */
public class GeneratePdf {

	public GeneratePdf(){

	}

	public ByteArrayOutputStream getDocument(String message) throws IOException, COSVisitorException{
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		
	    ByteArrayOutputStream output = new ByteArrayOutputStream(); 

		PDFont font = PDType1Font.HELVETICA;      
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		contentStream.beginText();
		contentStream.moveTextPositionByAmount(10, 770);
		contentStream.setFont(font, 10);
		contentStream.drawString(message);
		contentStream.endText();
		
		document.save(output);
		document.close();
		
		return output;
	}
}
