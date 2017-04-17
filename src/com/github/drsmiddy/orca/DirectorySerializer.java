package com.github.drsmiddy.orca;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DirectorySerializer {

	public static DirectoryNode deserializeDirectory(String path) throws ParserConfigurationException, SAXException, IOException
	{
		DirectoryNode node = new DirectoryNode(path);
		SAXParserFactory saxParserFoctory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFoctory.newSAXParser();
		DefaultHandler xmlHandler = new XMLHandler(node);
		
			saxParser.parse(node, xmlHandler);
		
			return node;
	}
	
	public static void serializeDirectory(DirectoryNode rootNode) throws ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.newDocument();
		
		Element element = doc.createElement("RootDir");
		doc.appendChild(element);
		element.setAttribute("name", rootNode.getName());
		for(DirectoryNode node:rootNode.listNodes()){
			writeElement(doc, element, node);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(System.out);
		
		transformer.transform(source, result);
		
		System.out.println("\nFile Saved.");
	}
	
	public static void writeElement(Document doc, Node parentNode, DirectoryNode node)
	{
		Element element = doc.createElement(node.getSerializedTagName());
		parentNode.appendChild(element);
		element.setAttribute("name", node.getName());
		
		for(DirectoryNode childNode:node.listNodes()){
			writeElement(doc, element, childNode);
		}
	}
}
