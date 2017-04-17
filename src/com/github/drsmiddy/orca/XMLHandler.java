package com.github.drsmiddy.orca;

import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler{
	
	DirectoryNode rootNode;
	
	public XMLHandler(DirectoryNode root){
		rootNode = root;
	}
	
	
	
}
