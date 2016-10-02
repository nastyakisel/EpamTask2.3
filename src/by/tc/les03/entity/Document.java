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
	// List<String> lines = new ArrayList<String>();

	List<MyElement> childElems = new ArrayList<MyElement>();

	public List<MyElement> getChildElems() {
		return childElems;
	}

	public void getAllElemens() {
		for (MyElement line : childElems) {
			System.out.println("Имя элемента:" + " " + line.getName() + " "
					+ "Имя родителя:" + line.getParent().getName() + " "
					+ "Описание: " + line.getDescription());

		}
	}

	public MyElement getRoot() {
		return childElems.get(childElems.size() - 1);
	}

	public ArrayList<MyElement> getElementsByTagName(String tagName) {
		ArrayList<MyElement> listElem = new ArrayList<MyElement>();
		for (MyElement myElem : childElems) {
			if (myElem.getName().equals(tagName)) {
				listElem.add(myElem);
			}
		}
		return listElem;

	}

	public MyElement getParentByTagName(String tagName) {
		MyElement parent = null;
		for (MyElement myElem : childElems) {
			if (myElem.getName().equals(tagName)) {
				parent = myElem;
				break;
			}
		}
		return parent;
	}

	public void getAttributesByElementName(String elementName) {
		ArrayList<MyElement> elemList = getElementsByTagName(elementName);

		for (MyElement myElem : elemList) {
			if (myElem.isHasAttributes()) {
				for (Attribute atrr : myElem.getAttributes()) {
					System.out.println("Имя элемента: " + " "
							+ myElem.getName() + " " + "Атрибуты:" + " "
							+ atrr.getAttributeName() + " " + "= "
							+ atrr.getAttributeValue());

				}
			} else {
				System.out.println("Эелемент не имеет атриботов");
			}

		}
	}

	public List<MyElement> getChildsByElementName(String parentName) {

		ArrayList<MyElement> listElem = getElementsByTagName(parentName);
		for (MyElement myElem : listElem) {

			return myElem.getChild();
		}
		return new ArrayList<MyElement>();

	}

}
