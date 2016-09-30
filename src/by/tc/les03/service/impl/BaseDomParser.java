package by.tc.les03.service.impl;

import java.io.*;
import java.util.*;

import by.tc.les03.entity.Document;


import by.tc.les03.entity.MyElement;

import by.tc.les03.service.DomParser;

public class BaseDomParser implements DomParser {
	
	Document document;
	
	
	public Document getDocument() {
		return document;
	}



	public void parse(String fileName) throws IOException{
		document = new Document();
		try {
		File filetoparse = new File(fileName);
		FileReader fileReader = new FileReader(filetoparse);
		BufferedReader reader = new BufferedReader(fileReader);
		
		
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			
			document.readFile(line);
		}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	

	
}
