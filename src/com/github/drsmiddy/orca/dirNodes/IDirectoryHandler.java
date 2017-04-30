package com.github.drsmiddy.orca.dirNodes;

import java.io.File;

import com.github.drsmiddy.orca.nodeInterfaces.IDeserializedNode;

public interface IDirectoryHandler {

	public void scanDirectory(String path);
	
	public void printDirectory(File outputFile);
	
	public boolean compareWithSerialized(IDeserializedNode deserializedNode);
	
}
