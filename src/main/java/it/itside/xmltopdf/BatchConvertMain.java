package it.itside.xmltopdf;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import it.itside.xmltopdf.converters.XMLtoHTML;
import it.itside.xmltopdf.converters.XMLtoPDF;

public class BatchConvertMain {

	public static void main(String[] args) throws TransformerException, IOException {
		if (args.length != 2) {
			printUsage();
			return;
		}

		File fXSL = new File(args[0]);
		FileInputStream isXSL = new FileInputStream(fXSL);
		Transformer transformer = XMLtoHTML.getTransformer(isXSL);

		File[] xmls = new File(args[1]).listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.getName().toLowerCase().endsWith(".xml"))
					return true;
				return false;
			}
		});

		FileOutputStream fos[] = new FileOutputStream[xmls.length];
		for (int i = 0; i < xmls.length; i++) {
			File fXML = xmls[i];
			fos[i] = new FileOutputStream(new File(fXML.getParentFile(), fXML.getName() + ".pdf"));
			try (FileInputStream isXML = new FileInputStream(fXML)) {
				XMLtoPDF.xmlToPDF(transformer, isXML, fos[i], false);
			} finally {
				fos[i].close();
			}
		}
	}

	private static void printUsage() {
		System.err.println("Usage: java -jar xmltopdf.jar XSL_FILE_PATH XMLs_FOLDER_PATH");
	}

}
