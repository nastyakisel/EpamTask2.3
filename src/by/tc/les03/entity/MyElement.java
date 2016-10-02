package by.tc.les03.entity;

import java.util.*;

public class MyElement {
	
	private String name;
	private String description;
	private List<MyElement> child;
	private MyElement parent;
	private List<Attribute> attributes;
	
	private boolean hasAttributes;

	public boolean isHasAttributes() {
		return hasAttributes;
	}

	public void setHasAttributes(boolean hasAttributes) {
		this.hasAttributes = hasAttributes;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public MyElement() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public List<MyElement> getChild() {
		return child;
	}

	public void setChild(List<MyElement> child) {
		this.child = child;
	}

	public MyElement getParent() {
		return parent;
	}

	public void setParent(MyElement parent) {
		this.parent = parent;
	}
	
}
