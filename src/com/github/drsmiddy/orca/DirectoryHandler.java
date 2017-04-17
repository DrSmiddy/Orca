package com.github.drsmiddy.orca;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class DirectoryHandler implements IDirectoryHandler {

	DirectoryNode directory;
	
	@Override
	public void scanDirectory(String path) {
		directory = DirectoryScanner.scanDirectory(path);
	}

	@Override
	public void printDirectory() {
		try {
			DirectorySerializer.serializeDirectory(directory);
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
