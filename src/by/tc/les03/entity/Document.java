package by.tc.les03.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;



public class Document {
	List<String> lines = new ArrayList<String>();
	
	//List<MyElement> allElems;
	List<MyElement> childElems = new ArrayList<MyElement>();
	Stack<MyElement> stack = new Stack<MyElement>();
	
	
	public void read(){
		Iterator iterator = lines.iterator();
		while (iterator.hasNext()) {
			String readedLine = (String) iterator.next();
			readLine(readedLine);
		}
	}
	
	public void readFile(String lineToParse) {
		String trimLine = lineToParse.trim();
		
		
		/*if (!trimLine.isEmpty()) {
			String someStr = trimLine.substring(1);
			
			if(someStr.contains("<") && someStr.charAt(someStr.indexOf("<") + 1) != '/') {
				lines.remove(lines.size()-1);
				String [] array = someStr.split("<");
				for (int i = 0; i < array.length; i++) {
					String newLine = "<" + array[i];
					lines.add(newLine);
				}
				
			}*/
		
		if (!lines.isEmpty()) {
		if (!trimLine.startsWith("<") && !lines.get(lines.size()-1).contains(">")) {
			trimLine = lines.get(lines.size()-1) + " " + trimLine;
			lines.remove(lines.size()-1);
			
		}
		}
		lines.add(trimLine);
	}
		

	
	public void readLine(String lineToParse) {
		String trimLine = lineToParse.trim();
		List<Attribute> arrtList = new ArrayList<Attribute>();
		
		if (!trimLine.isEmpty()) {
			String someStr = trimLine.substring(1);
			//System.out.println(someStr);
			if(someStr.contains("<") && someStr.charAt(someStr.indexOf("<") + 1) != '/') {
			String [] array = someStr.split("<");
			for (int i = 0; i < array.length; i++) {
				String newLine = "<" + array[i];
				readLine(newLine);
			}
			
		}
		}
		
	
		if (trimLine.startsWith("<") && trimLine.charAt(1) != '/') {
			String elementName = getElementName(trimLine);
			String elementText = getElementText(trimLine);
			
			arrtList = findAttribute(trimLine);
			for(Attribute att: arrtList) {
	    		System.out.println("Имя атрибута: " + att.getAttributeName() + " " + "Значение атрибута: " + att.getAttributeValue());
	    	}
			
			MyElement elem = new MyElement();
			elem.setName(elementName);
			elem.setDescription(elementText);
		
			if (!arrtList.isEmpty()) {
				elem.setAttributes(arrtList);
				elem.setHasAttributes(true);
			}
			//allElems.add(elem);
			stack.push(elem);
		}
		
		if (!trimLine.startsWith("<") && !trimLine.endsWith(">")) {
			if (!stack.isEmpty()) {
				MyElement last = stack.peek();
				String newText = last.getDescription().concat(" " + trimLine);
				last.setDescription(newText);
			}
			
		}
		if (findEndOfTag(trimLine)) {
			MyElement child = stack.pop();
			childElems.add(child);
			
					
				if (!stack.isEmpty()) {
					MyElement parent = stack.peek();
					child.setParent(parent);
						
					}
		}
		
		
		}
		
	
		public boolean findEndOfTag(String str) {
			
			String ch = "</";
			String cg = "/>";
			if (str.contains(ch) || str.contains(cg)) {
				return true;
			}
			return false;
		}
	
		public void split(String str) {
			
		}
	
		public String getElementName(String str) {
			
			if (str.contains(">")) {
				String subString = str.substring(1, str.indexOf(">"));
				if (subString.contains("\u0020")) {
					
					subString = str.substring(1, str.indexOf("\u0020"));
				}
				return subString;
			}
			else {
				String subString = str.substring(1, str.indexOf("\u0020"));
				return subString;
			}
		}
		
		
		public String getElementText(String str) {
			
			String s = str.substring(1);
			if (str.contains(">")) {
			String st = s.substring(str.indexOf(">"));
			if (str.contains("</")) {
				String elemText = s.substring(s.indexOf(">") + 1, s.indexOf("<"));
				return elemText;
			}
			return st;
			}
			else {
			return new String();
			}
		}
		
		public void splitLine(String str) {

			
				String [] array = str.split("<");
				System.out.println("Hallo");
				for (int i = 0; i < array.length; i++) {
					String newLine = "<" + array[i];
					System.out.println(newLine);
				}
			
			
		}
		
		
		public List<Attribute> findAttribute(String str) {
			List<Attribute> attrList = new ArrayList<Attribute>();
			String subString = null;
			if (str.contains("\u0020") && str.contains("=")) {
				if (str.contains(">")) {
					subString = str.substring(str.indexOf("\u0020"), str.indexOf(">"));
					subString = subString.trim();
				}
				else {
					subString = str.substring(str.indexOf("\u0020"));
					subString = subString.trim();
				}
				String[] splitedLine = subString.split("="); // наш массив после split
				
				Attribute attr1 = new Attribute();
				attr1.setAttributeName(splitedLine[0].trim());
				attrList.add(attr1);
				
				for (int i = 1; i < splitedLine.length; i++) {
					Attribute attr = new Attribute();
					
					String newLine = splitedLine[i].trim().substring(1);
					
					String attrValue = newLine.substring(0, newLine.indexOf("\""));
					String attrName = newLine.substring(newLine.indexOf("\"") + 1);
					String attrName2 = attrName.trim();
					attrList.get(i-1).setAttributeValue(attrValue);
					attr.setAttributeName(attrName2);
					attrList.add(attr);
					}
				return attrList;
			} // конец if
			
			return new ArrayList<Attribute>();
		}
		
		public void getAllElemens() {
			for (MyElement line: childElems) {
				System.out.println("Имя элемента:" + " " + line.getName() + " " + "Имя родителя:" + line.getParent().getName() + " " + "Описание: " + line.getDescription());
				/*if (line.isHasAttributes()) {
				List<Attribute> attrList = new ArrayList<Attribute>();
				attrList = line.getAttributes();
				System.out.println("Имя элемента с атрибутом:" + " " + line.getName());
				for (Attribute attr: attrList) {
					System.out.println("Имя атрибута: " + attr.getAttributeName() + " " + "Значение атрибута: " + attr.getAttributeValue());
				}
				}*/
			}
		}
		
		public MyElement getElementByTagName(String tagName) {
			for (MyElement line: childElems) {
				if (line.getName() == tagName) {
					MyElement myElem = line;
					System.out.println("Имя элемента " + " " + tagName);
					return myElem;
				}
			}
			System.out.println("Элемент не найден");
			return new MyElement();
			
		}
		
		public void getParentByTagName(String tagName) {
			for (MyElement line: childElems) {
				if (line.getName() == tagName) {
					MyElement myElem = line.getParent();
					System.out.println("Имя родителя для элемента " + " " + tagName + " " + "-" + " " + myElem.getParent().getName());
					
				}
			}
			System.out.println("Элемент не найден");
		}
		
		public void getElement() {
			if (childElems.get(2).getName() == "welcome-file ") {
			
			System.out.println(childElems.get(2).getName());
			}
			System.out.println("Элемент не найден");
		}
		
		
		
	
}
