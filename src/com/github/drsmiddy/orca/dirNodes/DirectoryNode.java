package com.github.drsmiddy.orca.dirNodes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.drsmiddy.orca.nodeInterfaces.INode;
import com.github.drsmiddy.orca.nodeInterfaces.INodeAttribute;

public class DirectoryNode extends File implements INode{

	private List<INode> childNodes;
	private List<INodeAttribute> attributes;
	private boolean serializedAsOwnRoot = false;
	private boolean excludedDuringSerialization = false;
	
	public DirectoryNode(String pathname) 
	{
		super(pathname);
		createChildNodes();
		createAttributesInitial();
	}
	
	private void createAttributesInitial()
	{
		attributes = new ArrayList<INodeAttribute>();
		attributes.add(new NodeAttribute("name", this.getName()));
	}

	private void createChildNodes() 
	{
		childNodes = new ArrayList<>();
		if (this.isDirectory()){
			for (File child:this.listFiles()){
				childNodes.add(new DirectoryNode(child.getPath()));
			}
		}
	}
	

	@Override
	public List<INode> getNeighborNodes()
	{
		return childNodes;
	}
	

	@Override
	public INode getNeighborNodeRecursivly(String name) 
	{
		INode childNode = null;
		int index = 0;
		int maxIndex = childNodes.size() - 1;
		
		do{
			if(((File) childNodes.get(index)).getName().equalsIgnoreCase(name)){
				childNode = childNodes.get(index);
				break;
			}
			else if(index == maxIndex){
				break;
			}
			else {
				childNode = childNodes.get(index).getNeighborNode(name);
				if (childNode != null){
					break;
				}
				++index;
			}
		}while(true);
		
		return childNode;
	}

	@Override
	public String getTypeName()
	{
		if(this.isDirectory()){
			return "Dir";
		}
		else return "File";
	}

	@Override
	public List<INodeAttribute> getAttributes() 
	{
		return attributes;
	}

	@Override
	public INode getNeighborNode(String name) 
	{
		INode childNode = null;
		int index = 0;
		int maxIndex = childNodes.size() - 1;
		
		do{
			if(((File) childNodes.get(index)).getName().equalsIgnoreCase(name)){
				childNode = childNodes.get(index);
				break;
			}
			else if(index == maxIndex){
				break;
			}
		}while(true);
		
		return childNode;
	}

	@Override
	public boolean isExcludedDuringSerialization() {
		return excludedDuringSerialization;
	}

	@Override
	public boolean isSerializedAsOwnRoot() {
		return serializedAsOwnRoot;
	}

	@Override
	public void excludeDuringSerialization(boolean bool) {
		excludedDuringSerialization = bool;
		
	}

	@Override
	public void serializeAsOwnRoot(boolean bool) {
		serializedAsOwnRoot = bool;
		
	}
}
