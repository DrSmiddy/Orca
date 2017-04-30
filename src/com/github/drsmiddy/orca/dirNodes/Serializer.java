package com.github.drsmiddy.orca.dirNodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.github.drsmiddy.orca.nodeInterfaces.IDeserializedNode;
import com.github.drsmiddy.orca.nodeInterfaces.INode;
import com.github.drsmiddy.orca.nodeInterfaces.INodeAttribute;

public class Serializer {

	public static IDeserializedNode deserializeNode(String path) throws ParserConfigurationException, SAXException, IOException, XMLStreamException
	{
		FileInputStream input = new FileInputStream(path);
		final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        final XMLStreamReader parser = inputFactory.createXMLStreamReader(input);
		
        IDeserializedNode rootNode = new DeserializedNode();
        
        while(parser.hasNext()){
        	int xmlEvent = parser.next();
    		if(xmlEvent == XMLStreamConstants.START_ELEMENT){
    			rootNode.setTypeName(parser.getLocalName());
    			break;
    		}
        }
        
        deserialize(parser, rootNode);
		
        return rootNode;
	}
	
	private static void deserialize(XMLStreamReader parser, IDeserializedNode node) throws XMLStreamException
	{
        int attributeCount = 0;
        while(parser.hasNext()){
        	int xmlEvent = parser.next();
        	if(xmlEvent == XMLStreamConstants.ATTRIBUTE){
        		node.setAttribute(new NodeAttribute(parser.getAttributeLocalName(attributeCount), parser.getAttributeValue(attributeCount)));
        		attributeCount++;
        	}
        	else if (xmlEvent == XMLStreamConstants.START_ELEMENT){
        		IDeserializedNode neighborNode = new DeserializedNode();
        		neighborNode.setTypeName(parser.getLocalName());
        		node.setChildNode(neighborNode);
        		deserialize(parser, neighborNode);
        	}
        	else if(xmlEvent == XMLStreamConstants.END_ELEMENT){
        		break;
        	}
        }
	}
	
	public static void serializeNode(INode rootNode, File outputFile) throws ParserConfigurationException, TransformerException, FileNotFoundException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		OutputStream outStream = new FileOutputStream(outputFile);
		
		Document doc = builder.newDocument();
		
		Element element = doc.createElement(rootNode.getTypeName());
		doc.appendChild(element);
		for(INodeAttribute attribute:rootNode.getAttributes()){
			element.setAttribute(attribute.getName(), attribute.getValue().toString());
		}
		for(INode node:rootNode.getNeighborNodes()){
			writeElement(doc, element, node);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(outStream);
		
		transformer.transform(source, result);
		
		System.out.println("\nFile Saved.");
	}
	
	public static void writeElement(Document doc, Node parentNode, INode node)
	{
		Element element = doc.createElement(node.getTypeName());
		parentNode.appendChild(element);
		for(INodeAttribute attribute:node.getAttributes()){
			element.setAttribute(attribute.getName(), attribute.getValue().toString());
		}
		
		for(INode childNode:node.getNeighborNodes()){
			writeElement(doc, element, childNode);
		}
	}
}
