package com.github.drsmiddy.orca.dirNodes;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.github.drsmiddy.orca.nodeInterfaces.IDeserializedNode;
import com.github.drsmiddy.orca.nodeInterfaces.INode;

public class DirectoryHandler implements IDirectoryHandler {

	DirectoryNode directory;
	
	@Override
	public void scanDirectory(String path) {
		directory = new DirectoryNode(path);
	}

	@Override
	public void printDirectory(File outputFile) {
		try {
			Serializer.serializeNode(directory, outputFile);
		} catch (ParserConfigurationException | TransformerException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean compareWithSerialized(IDeserializedNode deserializedNode) {
		boolean result = compare(directory, deserializedNode);
		return result;
	}
	
	public static boolean compare(INode node, INode deserializedNode){
		if(((IDeserializedNode) deserializedNode).equalsINode(node)){
			for(INode neighborNode:node.getNeighborNodes()){
				System.out.println("Normal" + neighborNode.getName());
				for(INode deserializedNeighborNode:deserializedNode.getNeighborNodes()){					
					System.out.println("DeS" + deserializedNeighborNode.getName());
					if(neighborNode.getName().equals(deserializedNeighborNode.getName())){
						boolean result = compare(neighborNode, deserializedNeighborNode);
						System.out.println(result);
						return result;
					}
				}
			}
		}
		return false;
	}

}
