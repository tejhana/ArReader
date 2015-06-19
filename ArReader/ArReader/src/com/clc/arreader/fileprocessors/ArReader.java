package com.clc.arreader.fileprocessors;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.clc.arreader.util.UniversalNamespaceCache;

public class ArReader {

	private DocumentBuilderFactory factory;
	private  DocumentBuilder builder;
	private Document doc;
	private XPathFactory xpathFactory;
	private XPath xpath;

	public ArReader(String FileName) {
	   
	    factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    
	    doc = null;
	    try {
	        builder = factory.newDocumentBuilder();
	        doc = builder.parse(FileName);
	
	        // Create XPathFactory object
	        xpathFactory = XPathFactory.newInstance();
	
	        // Create XPath object
	        xpath = xpathFactory.newXPath();
	        
	        xpath.setNamespaceContext(new UniversalNamespaceCache(doc, false));
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	    	e.printStackTrace();
	    }
    }
        
	public DocumentBuilderFactory getFactory() {
		return factory;
	}
	public void setFactory(DocumentBuilderFactory factory) {
		this.factory = factory;
	}
	public DocumentBuilder getBuilder() {
		return builder;
	}
	public void setBuilder(DocumentBuilder builder) {
		this.builder = builder;
	}
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	public XPathFactory getXpathFactory() {
		return xpathFactory;
	}
	public void setXpathFactory(XPathFactory xpathFactory) {
		this.xpathFactory = xpathFactory;
	}
	public XPath getXpath() {
		return xpath;
	}
	public void setXpath(XPath xpath) {
		this.xpath = xpath;
	}
	
}
