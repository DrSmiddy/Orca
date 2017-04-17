package com.github.drsmiddy.orca;

import java.util.Arrays;

public class DirectoryScanner {

	public static DirectoryNode scanDirectory(String path)
	{
		DirectoryNode node = new DirectoryNode(path);
		
		scanRecursiv(node);
		
		return node;
	}
	
	private static void scanRecursiv(DirectoryNode node)
	{
		if (node.isDirectory()){
			node.setChildren(0);
			for (DirectoryNode childNode:node.getChildrenNodes()){
				scanRecursiv(childNode);
			}
		}
	}
	
}