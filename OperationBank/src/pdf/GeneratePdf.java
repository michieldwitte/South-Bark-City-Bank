package pdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.pdfbox.exceptions.COSVisitorException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.edit.PDPageContentStream;
import org.pdfbox.pdmodel.font.PDFont;
import org.pdfbox.pdmodel.font.PDType1Font;
import org.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

/**
 * @author sander
 *
 */
public class GeneratePdf {

	public GeneratePdf(){

	}

	public ByteArrayOutputStream getDocument(String message, String shared_secret) throws IOException, COSVisitorException{
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		PDFont font = PDType1Font.HELVETICA;      
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    ByteArrayOutputStream qr_output_stream = QRCode.from(shared_secret).to(ImageType.PNG).withSize(300, 300).stream();
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		
		contentStream.beginText();
		contentStream.setFont(font, 10);

		contentStream.moveTextPositionByAmount(10, 770);
		contentStream.drawString(message);
		contentStream.endText();
		
		
		contentStream.close();
		document.save(output);
		document.close();
		
		
		return output;
	}
}
