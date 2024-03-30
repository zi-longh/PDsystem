package jnu.temp;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Test2 {
    public static void main(String[] args) throws DocumentException, IOException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("src/main/resources/document.xml");
        Element r = document.getRootElement().element("body").element("p").element("pPr").element("pStyle");

        List<Element> rList = r.elements();
        Element rNew = DocumentHelper.createElement("new1");
        rList.add(0,rNew);


        // 保存到xml
         OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
         format.setEncoding("UTF-8");
         XMLWriter xmlwriter = new XMLWriter(new FileWriter("src/main/resources/document.xml"), format);
         xmlwriter.write(document);
         xmlwriter.close();


    }
}
