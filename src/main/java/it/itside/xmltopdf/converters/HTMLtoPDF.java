package it.itside.xmltopdf.converters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.CompressionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;

public class HTMLtoPDF {
	
	public static void htmlToPDF(byte [] html, OutputStream osPDF) throws IOException{
		htmlToPDF(new ByteArrayInputStream(html), osPDF);
	}
	
	public static void htmlToPDF(InputStream isHTML, OutputStream osPDF) throws IOException{
		PdfWriter writer = new PdfWriter(osPDF, new WriterProperties()
				.setCompressionLevel(CompressionConstants.BEST_COMPRESSION).setFullCompressionMode(true));
		PdfDocument pdf = new PdfDocument(writer);
		pdf.setTagged();
		PageSize pageSize = PageSize.A4;
		pdf.setDefaultPageSize(pageSize);
		
		MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.PRINT);
		ConverterProperties properties = new ConverterProperties();
		properties.setMediaDeviceDescription(mediaDeviceDescription);
		HtmlConverter.convertToPdf(isHTML, pdf, properties);
		pdf.close();
	}
	
}
