package by.tc.les03.test;

import java.io.IOException;



import org.xml.sax.SAXException;

import by.tc.les03.entity.Document;
import by.tc.les03.entity.MyElement;
import by.tc.les03.service.impl.BaseDomParser;
import by.tc.les03.service.impl.BaseDomParser2;

public class DomExecute {
	
	
	public static void main(String[] args) throws IOException {
		
		BaseDomParser domParser = new BaseDomParser();
		domParser.parse("src/res/web.xml");
		//domParser.parse("src/res/menu.xml");
		Document document = domParser.getDocument();
		document.read();
		document.getAllElemens();
		
		
	}
}