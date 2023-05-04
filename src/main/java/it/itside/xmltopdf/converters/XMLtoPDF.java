package it.itside.xmltopdf.converters;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;


public class XMLtoPDF {
	
	public static void xmlToPDF(InputStream isXSL, InputStream isXMLs[], OutputStream osPDFs[], boolean inMemory)
			throws TransformerException, IOException {
		xmlToPDF(XMLtoHTML.getTransformer(isXSL), isXMLs, osPDFs, inMemory);
	}
	
	public static void xmlToPDF(Transformer transformer, InputStream isXMLs[], OutputStream osPDFs[], boolean inMemory)
			throws TransformerException, IOException {
		if (isXMLs.length != osPDFs.length)
			throw new IllegalArgumentException("isXMLs.length != osHTMLs.length");
		
		for (int i = 0; i < isXMLs.length; i++)
			xmlToPDF(transformer, isXMLs[i], osPDFs[i], inMemory);
	}
	
	public static void xmlToPDF(InputStream isXSL, InputStream isXML, OutputStream osPDF, boolean inMemory)
			throws TransformerException, IOException {
		xmlToPDF(XMLtoHTML.getTransformer(isXSL), isXML, osPDF, inMemory);
	}
	
	public static void xmlToPDF(Transformer transformer, InputStream isXML, OutputStream osPDF, boolean inMemory)
			throws TransformerException, IOException {
		File fTemp = null;
		OutputStream osHTML = null;
		if (inMemory)
			osHTML = new ByteArrayOutputStream();
		else {
			fTemp = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString() + ".html");
			osHTML = new FileOutputStream(fTemp);
		}

		XMLtoHTML.xmlToHTML(transformer, isXML, osHTML);

		if (inMemory)
			HTMLtoPDF.htmlToPDF(((ByteArrayOutputStream) osHTML).toByteArray(), osPDF);
		else {
			try (FileInputStream fisTemp = new FileInputStream(fTemp)) {
				HTMLtoPDF.htmlToPDF(fisTemp, osPDF);
			}
			if (!fTemp.delete())
				fTemp.deleteOnExit();
		}
	}

}
