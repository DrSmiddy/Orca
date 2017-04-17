package com.github.drsmiddy.orca;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends File{

	private ArrayList<DirectoryNode> childNodes;
	
	public DirectoryNode(String pathname) {
		super(pathname);
		createChildNodes();
	}

	private void createChildNodes() {
		childNodes = new ArrayList<>();
		if (this.isDirectory()){
			for (File child:this.listFiles()){
				childNodes.add(new DirectoryNode(child.getPath()));
			}
		}
	}
	
	public DirectoryNode[] listNodes(){
		DirectoryNode[] childNodeArray = new DirectoryNode[childNodes.size()];
		childNodes.toArray(childNodeArray);
		return childNodeArray;
	}
	
	public DirectoryNode getChildNodeRecursiv(String name){
		DirectoryNode childNode = null;
		int index = 0;
		int maxIndex = childNodes.size() - 1;
		
		do{
			if(childNodes.get(index).getName().equalsIgnoreCase(name)){
				childNode = childNodes.get(index);
				break;
			}
			else if(index == maxIndex){
				break;
			}
			else {
				childNode = childNodes.get(index).getChildNodeRecursiv(name);
				if (childNode != null){
					break;
				}
				++index;
			}
		}while(true);
		
		if (childNode != null){
			return childNode;
		}
		
		return null;
		}
	
	public String getSerializedTagName()
	{
		if(this.isDirectory()){
			return "Dir";
		}
		else return "File";
	}
}
