package pdf;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author sander
 *
 */
public class GeneratePdf {

	public GeneratePdf(){

	}

	public void createPDF(HttpServletResponse response, String GUUID, String shared_secret) throws ServletException, IOException {
		response.addHeader("Content-Type", "application/force-download"); 
		response.addHeader("Content-Disposition", "attachment; filename=\register"+ GUUID +".pdf");
		//response.setContentType("application/pdf");
		OutputStream out=response.getOutputStream();
		try {
			Document doc = new Document();
			PdfWriter.getInstance(doc, out);
			doc.open();
			doc.add(new Paragraph(GUUID));
			BarcodeQRCode qrcode = new BarcodeQRCode(shared_secret, 0, 0, null);
	        Image qrcodeImage = qrcode.getImage();
	        qrcodeImage.setAbsolutePosition(10,500);
	        qrcodeImage.scalePercent(500);
	        doc.add(qrcodeImage);
			doc.close();
		} catch (DocumentException exc){
			throw new IOException(exc.getMessage());
		} finally {            
			out.close();
		}
	}
}
