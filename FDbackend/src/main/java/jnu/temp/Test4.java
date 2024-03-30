package jnu.temp;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class Test4 {
    public static void main(String[] args) throws DocumentException {
        SAXReader saxReader = new SAXReader();
//        File xmlFile = new File("src/main/resources/paperFile/word测试论文/[Content_Types].xml");
//        Document document = saxReader.read(xmlFile);
        Document document = saxReader.read("src/main/resources/paperFile/word测试论文/word/document.xml");
        Element rootElement = document.getRootElement();
        System.out.println(rootElement.getName());
    }
}
