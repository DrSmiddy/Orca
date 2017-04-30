package com.github.drsmiddy.orca.nodeInterfaces;

import java.util.List;

public interface INode {
	
	public List<INode> getNeighborNodes();
	
	public INode getNeighborNode(String name);
	
	public INode getNeighborNodeRecursivly(String name);
	
	public String getTypeName();
	
	public List<INodeAttribute> getAttributes();

	public boolean isExcludedDuringSerialization();
	
	public void excludeDuringSerialization(boolean bool);
	
	public boolean isSerializedAsOwnRoot();
	
	public void serializeAsOwnRoot(boolean bool);
	
	public String getName();
}
