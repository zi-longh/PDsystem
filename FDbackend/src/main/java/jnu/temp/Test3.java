package jnu.temp;

import jnu.service.xmlprocessor.DocxInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static jnu.service.xmlprocessor.CommentOperation.*;
import static jnu.service.xmlprocessor.PreProcess.clearAllComments;
import static jnu.service.xmlprocessor.PreProcess.paperPreProcess;
import static jnu.utils.DocxUtils.unZipDocx;
import static jnu.utils.DocxUtils.zipDocx;

public class Test3 {
    public static void main(String[] args) throws DocumentException, IOException {
        /* 测试让word文件无法打开的问题 */

        String xmlDirectory = unZipDocx("src/main/resources/paperFile/word测试论文.docx");

        DocxInfo docxInfo = new DocxInfo();
        paperPreProcess(xmlDirectory, docxInfo);
        clearAllComments(xmlDirectory);


        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(xmlDirectory + "/word/document.xml");
        Element bodyElement = document.getRootElement().element("body");
        List<Element> pElements = bodyElement.elements();
        Element pElement = pElements.get(0);
        ArrayList<String> contentList = new ArrayList<>();
        contentList.add("这是一个测试批注！");


        // 清空所有的批注标签


        addCommentWithAllP(xmlDirectory, pElements.get(0), contentList, "测试", 2);
        addCommentWithAllP(xmlDirectory, pElements.get(1), contentList, "测试", 3);
        addCommentNoRange(xmlDirectory, pElements.get(2), contentList, "测试", 4);
        addCommentNoRangeInFirstLine(xmlDirectory, pElements.get(3), contentList, "测试", 5);
        addCommentWithRange(xmlDirectory, pElements.get(4), 0, contentList, "测试", 9);

        // 保存document.xml文件
        OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
        format.setEncoding("UTF-8");
        XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/document.xml")), format);
        xmlwriter.write(document);
        xmlwriter.close();

        zipDocx(xmlDirectory, 2);


    }
}
