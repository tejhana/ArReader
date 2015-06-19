package com.clc.arreader.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.clc.arreader.beans.ARTitleOutputBean;
import com.clc.arreader.fileprocessors.ArTitilesIsbnReader;
import com.clc.arreader.fileprocessors.ArTitlesReader;
import com.clc.arreader.util.FileUtility;
import com.clc.arreader.util.UniversalNamespaceCache;

public class ArReaderDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	String iBookId = "";
	String iEan = "";
	String iQuizId = "";
	String iReader = "";
	String iInterest = "";
	String iPoints = "";
	
	Boolean existingFileDeleted;
	String crLf = "\r\n"; 
	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	String topOfFileHeader = "ArReader Titles-TitlesIsbn File - " + timeStamp;
	final String AR_OUTPUT_FILE = "ArOutput.txt";
	
	FileUtility fileUtility = new FileUtility();
	
	try {
	    	existingFileDeleted = fileUtility.deletExistingTextFile(AR_OUTPUT_FILE);
	    	System.out.println("existingFileDeleted - " +existingFileDeleted);
	    	fileUtility.createNewTextFile(AR_OUTPUT_FILE, topOfFileHeader);
	    	fileUtility.writeToTextFile(AR_OUTPUT_FILE, crLf);
	    	
	} catch (IOException e1) {
		System.out.println("Error with FileUtility - deletExistingTextFile");
		e1.printStackTrace();
	}
	
	ArTitlesReader arTitleReader = new ArTitlesReader();
	long numberOfDataRows = (long) arTitleReader.getNumberOfDataRows(arTitleReader);
	System.out.println("numberOfDataRows: " + numberOfDataRows);
		
	for(long index=1; index <= numberOfDataRows ; index++)
    {
    	boolean vchQuizTypeRp = arTitleReader.getvchQuizTypeRp(arTitleReader, index);
    	
		if(vchQuizTypeRp)
    	{
			iBookId = arTitleReader.getIbook(arTitleReader, index);
			iQuizId = arTitleReader.getIquizId(arTitleReader, index);
			iReader = arTitleReader.getIReadingLevel(arTitleReader, index);
			iInterest = arTitleReader.getVchInterestLevel(arTitleReader, index);
			iPoints = arTitleReader.getIarPoints(arTitleReader, index);
			
			System.out.println("***************************");
			System.out.println("iBookId - "+ iBookId);	
			System.out.println("iQuizId - "+ iQuizId);
			System.out.println("iReader - "+ iReader);
			System.out.println("iInterest - "+ iInterest);
			System.out.println("iPoints - "+ iPoints);
            System.out.println("***************************");
            
            //Read AR_Titles_Isbn files with iBook filter on 978 starts with for vchEan and return a count of how many rows exist
            //loop through those rows and create final output beans
        	
            ArTitilesIsbnReader arTitleIsbnReader = new ArTitilesIsbnReader();
        	
        	NodeList nodes = arTitleIsbnReader.getVchEan978(arTitleIsbnReader, iBookId);
        
        	int counter=0;
        	
        	for (int i = 0; i < nodes.getLength(); i++) 
        	{
        		Element el = (org.w3c.dom.Element) nodes.item(i);
                if(el.hasAttribute("vchEAN"))
                {
                	iEan = el.getAttribute("vchEAN");
  
                	ARTitleOutputBean arTItleOutput = new ARTitleOutputBean(iBookId, iEan, iQuizId, iReader, iInterest, iPoints);
                	String outputRecord = arTItleOutput.toString();
                	System.out.println(outputRecord);
                	
                	try {
						fileUtility.writeToTextFile(AR_OUTPUT_FILE, outputRecord);
						fileUtility.writeToTextFile(AR_OUTPUT_FILE, crLf);
					} catch (IOException e) {
						System.out.println("Error with FileUtility - writeToTextFIle");
						e.printStackTrace();
					}
                	counter = i;
                }

        	}

            	System.out.println("Number of rows for iBook - " + iBookId + " - " + counter);
         }            
            
    }

   }
}
