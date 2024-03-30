package jnu.service.xmlprocessor;

import jnu.utils.DocxUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 提供对论文中的批注的操作。
 * */
public class CommentOperation {

    /**
     * 清空文档所有的批注
     * */
    public static void clearComment(String xmlDirectory, int docxEndCommentNum){
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element rootElement = document.getRootElement();

            // 删除document.xml中所有的批注，包括<w:commentRangeStart>、<w:commentRangeEnd>、<w:commentReference>
            rootElement.selectNodes("//w:commentRangeStart").forEach(Node::detach);
            rootElement.selectNodes("//w:commentRangeEnd").forEach(Node::detach);
            rootElement.selectNodes("//w:commentReference").forEach(Node::detach);

            // 删除文末的批注（如果有的话，直接整个段落删除）
            List<Element> pElements = rootElement.element("body").elements("p");
            while(docxEndCommentNum > 0){
                Element pElement = pElements.get(pElements.size() - 1);
                pElement.detach();
                docxEndCommentNum--;
            }

            OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
            format.setEncoding("UTF-8");
            XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
            xmlwriter.write(document);
            xmlwriter.close();
        } catch (DocumentException | IOException e) {
            System.err.println("当前函数名：clearComment，错误信息：清空批注失败，" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * 添加批注信息到comments.xml文件中
     * TODO: 等待测试
     * */
    public static void addCommentToCommentsXML(String xmlDirectory, ArrayList<String> contentList, String commentType, int commentId) throws DocumentException, IOException {
        // 读取comments.xml文件
        String commentsXmlPath = xmlDirectory + "/word/comments.xml";
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(commentsXmlPath);
        Element rootElement = document.getRootElement();
        // 添加批注
        Element commentElement = rootElement.addElement("w:comment");
        commentElement.addAttribute("w:id", String.valueOf(commentId));
        commentElement.addAttribute("w:author", commentType);
        commentElement.addAttribute("w:date", DocxUtils.getNowTime());
        commentElement.addAttribute("w:initials", "default");

        for (String content : contentList) {
            Element pElement = commentElement.addElement("w:p");
//            Element pprElement = pElement.addElement("w:pPr");
//            Element pStyleElement = pprElement.addElement("w:pStyle");
//            pStyleElement.addAttribute("w:val", "p4"); // 设置批注的样式
            Element rElement = pElement.addElement("w:r");
            rElement.addElement("w:t").addText(content);
        }
        // 保存comments.xml文件
        OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
        format.setEncoding("UTF-8");
        XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(commentsXmlPath)), format);
        xmlwriter.write(document);
        xmlwriter.close();
    }

    /**
     * 要求在所给段落的特定w:r位置添加批注，需要添加批注开始和结束标签
     * */
    public static void addCommentWithRange(String xmlDirectory, Element pElement, int index, ArrayList<String> contentList, String commentType, int commentId) {
        List<Element> rElementList = pElement.elements("r");
        Element commentRangeStart = DocumentHelper.createElement("w:commentRangeStart");
        commentRangeStart.addAttribute("w:id", String.valueOf(commentId));
        Element commentRangeEnd = DocumentHelper.createElement("w:commentRangeEnd");
        commentRangeEnd.addAttribute("w:id", String.valueOf(commentId));
        Element rElement = DocumentHelper.createElement("w:r");
        rElement.addElement("w:commentReference", "http://schemas.openxmlformats.org/wordprocessingml/2006/main").addAttribute("w:id", String.valueOf(commentId));
        // 注意插入顺序
        rElementList.add(index + 1, rElement);
        rElementList.add(index + 1, commentRangeEnd);
        rElementList.add(index, commentRangeStart);
        try {
            addCommentToCommentsXML(xmlDirectory, contentList, commentType, commentId);
        } catch (DocumentException | IOException e) {
            System.err.println("当前函数名：addCommentWithRange，错误信息：添加批注失败，" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("函数名：addCommentWithRange，添加批注成功。");
    }

    /**
     * 在所给段落的末尾添加批注，不设定范围
     * */
    public static void addCommentNoRange(String xmlDirectory, Element pElement, ArrayList<String> contentList, String commentType, int commentId) {
        Element relement = pElement.addElement("w:r");
        relement.addElement("w:commentReference").addAttribute("w:id", String.valueOf(commentId));
        try {
            addCommentToCommentsXML(xmlDirectory, contentList, commentType, commentId);
        } catch (DocumentException | IOException e) {
            System.err.println("当前函数名：addCommentNoRange，错误信息：添加批注失败，" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 在所给段落的段首添加批注，不设定范围
     * */
    public static void addCommentNoRangeInFirstLine(String xmlDirectory, Element pElement, ArrayList<String> contentList, String commentType, int commentId) {
        if (Objects.equals(pElement.getName(), "tbl")) {
            pElement = pElement.element("tr").element("tc").element("p");
        }
        Element rElement = pElement.element("r"); // 获取第一个<w:r>标签
        if (rElement == null) {
            rElement = pElement.addElement("w:r");
            Element c = DocumentHelper.createElement("w:commentReference");
            c.addAttribute("w:id", String.valueOf(commentId));
            rElement.add(c);
            try {
                addCommentToCommentsXML(xmlDirectory, contentList, commentType, commentId);
            } catch (DocumentException | IOException e) {
                System.err.println("当前函数名：addCommentNoRangeInFirstLine，错误信息：添加批注失败，" + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            List<Element> rElements = rElement.elements();
            Element c = DocumentHelper.createElement("w:commentReference");
            c.addAttribute("w:id", String.valueOf(commentId));
            rElements.add(0, c);
            try {
                addCommentToCommentsXML(xmlDirectory, contentList, commentType, commentId);
            } catch (DocumentException | IOException e) {
                System.err.println("当前函数名：addCommentNoRangeInFirstLine，错误信息：添加批注失败，" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 在所给段落的段首添加批注，选中整个段落
     * */
    public static void addCommentWithAllP(String xmlDirectory, Element pElement, ArrayList<String> contentList, String commentType, int commentId) {
        pElement.addElement("w:commentRangeEnd").addAttribute("w:id", String.valueOf(commentId));
        pElement.addElement("w:r").addElement("w:commentReference").addAttribute("w:id", String.valueOf(commentId));
        List<Element> rElements = pElement.elements();
        Element c = DocumentHelper.createElement("w:commentRangeStart");
        c.addAttribute("w:id", String.valueOf(commentId));
        rElements.add(0, c);
        try {
            addCommentToCommentsXML(xmlDirectory, contentList, commentType, commentId);
        } catch (DocumentException | IOException e) {
            System.err.println("当前函数名：addCommentWithAllP，错误信息：添加批注失败，" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 在所给文段的首部添加批注。即在w:r的首位添加w:commentReference标签。
     * */
    public static void addCommentInWr(String xmlDirectory, Element rElement, ArrayList<String> contentList, String commentType, int commentId) {
        Element c = DocumentHelper.createElement("w:commentReference");
        c.addAttribute("w:id", String.valueOf(commentId));
        List<Element> rElements = rElement.elements();
        rElements.add(0, c);
        try {
            addCommentToCommentsXML(xmlDirectory, contentList, commentType, commentId);
        } catch (DocumentException | IOException e) {
            System.err.println("当前函数名：addCommentInWr，错误信息：添加批注失败，" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 在整个文档的末尾添加说明，以在文末插入段落的方式，而非批注的方式。
     * 需要传入文档的bodyElement。
     * 注意：插入的段落要在文档的<w:body>的<w:sectPr>标签之前，且在location标签之后，所以就是在倒数第二个位置插入。
     */
    public static void addCommentInDocxEnd(String xmlDirectory, Element bodyElement, ArrayList<String> contentList, String commentType) {
        Element pElement = DocumentHelper.createElement("w:p");
        Element pprElement = pElement.addElement("w:pPr", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        pprElement.addElement("w:widowControl", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        pprElement.addElement("w:jc", "http://schemas.openxmlformats.org/wordprocessingml/2006/main").addAttribute("w:val", "left");
        Element rElement = pElement.addElement("w:r", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        Element rpr = rElement.addElement("w:rPr", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        rpr.addElement("w:rFonts", "http://schemas.openxmlformats.org/wordprocessingml/2006/main").addAttribute("w:hAnsi", "eastAsia");
        rpr.addElement("w:sz", "http://schemas.openxmlformats.org/wordprocessingml/2006/main").addAttribute("w:val", "36");
        rpr.addElement("w:szCs", "http://schemas.openxmlformats.org/wordprocessingml/2006/main").addAttribute("w:val", "36");
        rpr.addElement("w:color", "http://schemas.openxmlformats.org/wordprocessingml/2006/main").addAttribute("w:val", "FF0000");

        rElement.addElement("w:br", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        rElement.addElement("w:t", "http://schemas.openxmlformats.org/wordprocessingml/2006/main").addText("-------" + commentType + "-------：");
        rElement.addElement("w:br", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        for (String content : contentList) {
            rElement.addElement("w:t", "http://schemas.openxmlformats.org/wordprocessingml/2006/main").addText(content);
            rElement.addElement("w:br", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        }

        // 插入段落
        List<Element> elements = bodyElement.elements();
        elements.add(elements.size() - 1, pElement);
    }

}
