package it.itside.xmltopdf.converters;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLtoHTML {
	
	public static void xmlToHTML(byte[] xsl, InputStream[] isXMLs, OutputStream[] osHTMLs) throws TransformerException {
		xmlToHTML(new ByteArrayInputStream(xsl), isXMLs, osHTMLs);
	}

	public static void xmlToHTML(InputStream isXSL, InputStream[] isXMLs, OutputStream[] osHTMLs)
			throws TransformerException {
		xmlToHTML(getTransformer(isXSL), isXMLs, osHTMLs);
	}
	
	public static void xmlToHTML(Transformer transformer, InputStream[] isXMLs, OutputStream[] osHTMLs)
			throws TransformerException {
		if (isXMLs.length != osHTMLs.length)
			throw new IllegalArgumentException("isXMLs.length != osHTMLs.length");
		for (int i = 0; i < isXMLs.length; i++)
			xmlToHTML(transformer, isXMLs[i], osHTMLs[i]);
	}
	
	public static void xmlToHTML(byte[] xsl, InputStream isXML, OutputStream osHTML) throws TransformerException {
		xmlToHTML(new ByteArrayInputStream(xsl), isXML, osHTML);
	}
	
	public static void xmlToHTML(InputStream isXSL, InputStream isXML, OutputStream osHTML)
			throws TransformerException {
		Transformer transformer = getTransformer(isXSL);
		xmlToHTML(transformer, isXML, osHTML);
	}
	
	public static void xmlToHTML(Transformer transformer, InputStream isXML, OutputStream osHTML)
			throws TransformerException {
		transformer.transform(new StreamSource(isXML), new StreamResult(osHTML));
	}
	
	public static Transformer getTransformer(InputStream isXSL) throws TransformerConfigurationException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = new StreamSource(isXSL);
		return factory.newTransformer(xslt);
	}
	
}
