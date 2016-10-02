package by.tc.les03.service.impl;

import java.io.*;
import java.util.*;

import by.tc.les03.entity.Attribute;
import by.tc.les03.entity.Document;
import by.tc.les03.entity.MyElement;
import by.tc.les03.service.DomParser;

public class BaseDomParser implements DomParser {

	List<String> lines = new ArrayList<String>();
	Stack<MyElement> stack = new Stack<MyElement>();
	List<MyElement> childElems = new ArrayList<MyElement>();
	private Document document;

	public Document getDocument() {
		return document;
	}

	public void parse(String fileName) throws IOException {
		document = new Document();
		try {
			File filetoparse = new File(fileName);
			FileReader fileReader = new FileReader(filetoparse);
			BufferedReader reader = new BufferedReader(fileReader);

			String line = null;
			while ((line = reader.readLine()) != null) {
				readFile(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		read();
	}

	public void read() {
		Iterator iterator = lines.iterator();
		while (iterator.hasNext()) {
			String readedLine = (String) iterator.next();
			readLine(readedLine);
		}
	}

	public void readFile(String lineToParse) {
		String trimLine = lineToParse.trim();

		if (!trimLine.isEmpty()) {
			String someStr = trimLine.substring(1);

			if (someStr.contains("<")
					&& someStr.charAt(someStr.indexOf("<") + 1) != '/') {
				lines.remove(lines.size() - 1);
				String[] array = someStr.split("<");
				for (int i = 0; i < array.length; i++) {
					String newLine = "<" + array[i];
					lines.add(newLine);
				}

			}
		}

		if (!lines.isEmpty()) {
			if (!trimLine.startsWith("<")
					&& !lines.get(lines.size() - 1).contains(">")) {
				trimLine = lines.get(lines.size() - 1) + " " + trimLine;
				lines.remove(lines.size() - 1);

			}
		}
		lines.add(trimLine);
	}

	public void readLine(String lineToParse) {
		String trimLine = lineToParse.trim();
		List<Attribute> arrtList = new ArrayList<Attribute>();

		List<MyElement> childList = new ArrayList<MyElement>();

		if (!trimLine.isEmpty()) {
			String someStr = trimLine.substring(1);

			if (someStr.contains("<")
					&& someStr.charAt(someStr.indexOf("<") + 1) != '/') {
				String[] array = someStr.split("<");
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

			MyElement elem = new MyElement();
			elem.setName(elementName);
			elem.setDescription(elementText);
			elem.setChild(childList);

			if (!arrtList.isEmpty()) {
				elem.setAttributes(arrtList);
				elem.setHasAttributes(true);
			}

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
			document.getChildElems().add(child);

			if (!stack.isEmpty()) {
				MyElement parent = stack.peek();
				child.setParent(parent);

				parent.getChild().add(child);

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
		} else {
			String subString = str.substring(1, str.indexOf("\u0020"));
			return subString;
		}
	}

	public String getElementText(String str) {

		String s = str.substring(1);
		if (str.contains(">")) {
			String st = s.substring(str.indexOf(">"));
			if (str.contains("</")) {
				String elemText = s.substring(s.indexOf(">") + 1,
						s.indexOf("<"));
				return elemText;
			}
			return st;
		} else {
			return new String();
		}
	}

	public void splitLine(String str) {

		String[] array = str.split("<");
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
				subString = str.substring(str.indexOf("\u0020"),
						str.indexOf(">"));
				subString = subString.trim();
			} else {
				subString = str.substring(str.indexOf("\u0020"));
				subString = subString.trim();
			}
			String[] splitedLine = subString.split("=");

			Attribute attr1 = new Attribute();
			attr1.setAttributeName(splitedLine[0].trim());
			attrList.add(attr1);

			for (int i = 1; i < splitedLine.length; i++) {
				Attribute attr = new Attribute();

				String newLine = splitedLine[i].trim().substring(1);

				String attrValue = newLine.substring(0, newLine.indexOf("\""));
				String attrName = newLine.substring(newLine.indexOf("\"") + 1);
				String attrName2 = attrName.trim();
				attrList.get(i - 1).setAttributeValue(attrValue);
				attr.setAttributeName(attrName2);
				attrList.add(attr);
			}
			return attrList;
		}

		return new ArrayList<Attribute>();
	}

}
