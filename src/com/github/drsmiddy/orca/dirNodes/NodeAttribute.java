package com.github.drsmiddy.orca.dirNodes;

import com.github.drsmiddy.orca.nodeInterfaces.INodeAttribute;

public class NodeAttribute implements INodeAttribute {
	
	private String name;
	
	private Object value;
	
	public NodeAttribute(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
