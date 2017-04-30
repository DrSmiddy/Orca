package com.github.drsmiddy.orca.dirNodes;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.github.drsmiddy.orca.nodeInterfaces.IDeserializedNode;
import com.github.drsmiddy.orca.nodeInterfaces.INode;
import com.github.drsmiddy.orca.nodeInterfaces.INodeAttribute;

public class DeserializedNode implements IDeserializedNode {

	private List<INode> neighborNodes;
	private List<INodeAttribute> attributes;
	private boolean serializedAsOwnRoot = false;
	private boolean excludedDuringSerialization = false;
	private String typeName;
	
	public DeserializedNode() 
	{
		neighborNodes = new ArrayList<INode>();
		attributes = new ArrayList<INodeAttribute>();
	}

	@Override
	public List<INode> getNeighborNodes()
	{
		return neighborNodes;
	}
	

	@Override
	public INode getNeighborNodeRecursivly(String name) 
	{
		INode childNode = null;
		int index = 0;
		int maxIndex = neighborNodes.size() - 1;
		
		do{
			if((neighborNodes.get(index)).getName().equalsIgnoreCase(name)){
				childNode = neighborNodes.get(index);
				break;
			}
			else if(index == maxIndex){
				break;
			}
			else {
				childNode = neighborNodes.get(index).getNeighborNode(name);
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
		return typeName;
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
		int maxIndex = neighborNodes.size() - 1;
		
		do{
			if((neighborNodes.get(index)).getName().equalsIgnoreCase(name)){
				childNode = neighborNodes.get(index);
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

	@Override
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public void setChildNode(IDeserializedNode node) {
		neighborNodes.add(node);
	}

	@Override
	public void setAttribute(INodeAttribute attribute) {
		attributes.add(attribute);
	}

	@Override
	public String getName() {
		ListIterator<INodeAttribute> iterator = attributes.listIterator();
		
		while(iterator.hasNext()){
			INodeAttribute attribute = iterator.next();
			if(attribute.getName().equalsIgnoreCase("name")){
				return attribute.getValue().toString();
			}
		}
		
		return "";
	}

	public boolean equalsINode(INode nodeToCompare){
		List<INodeAttribute> attributesToCompare = nodeToCompare.getAttributes();
		
		if(!this.getTypeName().equalsIgnoreCase(nodeToCompare.getTypeName())){
			return false;
		}
		for(INodeAttribute attribute:this.attributes){
			if(!attributesToCompare.contains(attribute)){
				return false;
			}
		}
		
		return true;
	}
}
