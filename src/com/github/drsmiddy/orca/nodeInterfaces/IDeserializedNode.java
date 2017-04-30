package com.github.drsmiddy.orca.nodeInterfaces;

public interface IDeserializedNode extends INode{

	public void setTypeName(String typeName);
	
	public void setChildNode(IDeserializedNode node);
	
	public void setAttribute(INodeAttribute attribute);

	public boolean equalsINode(INode node);
}
