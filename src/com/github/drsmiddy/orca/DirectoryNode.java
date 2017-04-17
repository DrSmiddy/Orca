package com.github.drsmiddy.orca;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends File{

	public DirectoryNode(String pathname) {
		super(pathname);
		children = new ArrayList<>();
	}

	private ArrayList<DirectoryNode> children;

	public ArrayList<DirectoryNode> getChildrenNodes() {
		return children;
	}
	
	public void setChildren(List<File> newChildren) {
		for (File child:newChildren){
			children.add(new DirectoryNode(child.getPath()));
		}
	}
	
	public DirectoryNode getChildNodeRecursiv(String name){
		DirectoryNode childNode = null;
		int index = 0;
		int maxIndex = children.size() - 1;
		
		do{
			if(children.get(index).getName().equalsIgnoreCase(name)){
				childNode = children.get(index);
				break;
			}
			else if(index == maxIndex){
				break;
			}
			else {
				childNode = children.get(index).getChildNodeRecursiv(name);
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
}
