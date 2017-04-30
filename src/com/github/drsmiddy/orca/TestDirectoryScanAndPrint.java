package com.github.drsmiddy.orca;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import com.github.drsmiddy.orca.dirNodes.DirectoryHandler;
import com.github.drsmiddy.orca.dirNodes.Serializer;

public class TestDirectoryScanAndPrint {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
		DirectoryHandler handler = new DirectoryHandler();
		
		handler.scanDirectory(args[0]);
		handler.printDirectory(new File(args[0], "/.serializedNodes.orca"));
		System.out.println(handler.compareWithSerialized(Serializer.deserializeNode(args[0] + "/.serializedNodes.orca")));
	}

}
