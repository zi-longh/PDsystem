package jnu.service.xmlprocessor;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 记录docx文档的中的信息，如样式id、关系id等。
 * TODO: 暂时只用于页脚页眉的检测和校正
 * */
public class DocxInfo {
    private String header1Rid;
    private String header2Rid;
    private String footer1Rid;
    private String footer2Rid;
    private String headerStyleId;
    private String footerStyleId;
    private boolean isHaveHeader;
    private boolean isHaveFooter;
    private boolean isHaveMainBody; // 是否有正文，即是否有绪论location标签

    private boolean isHaveNumbering; // 是否有编号设置？


    {
        isHaveHeader = false;
        isHaveFooter = false;
        isHaveMainBody = false;
        isHaveNumbering = false;
    }

    public static void setStyleId(String xmlDirectory, String StyleId, String fileName, String content) throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        String xmlFile = xmlDirectory + "/word/" + fileName;
        Document document = reader.read(xmlFile);
        Element rootElement = document.getRootElement();
        if(fileName.contains("footer")){
            Element pStyleElement = rootElement.element("sdt").element("sdtContent").element("p").element("pPr").element("pStyle");
            pStyleElement.attribute("val").setValue(StyleId);
        }else{
            Element pStyleElement = rootElement.element("p").element("pPr").element("pStyle");
            pStyleElement.attribute("val").setValue(StyleId);
            if(content != null){
                Element tElement = rootElement.element("p").element("r").addElement("w:t");
                tElement.setText(content);
            }
        }

        // 保存
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(Files.newOutputStream(Paths.get(xmlFile)), format);
        writer.write(document);
        writer.close();
    }

    public DocxInfo(String header1Rid, String header2Rid, String footer1Rid, String footer2Rid, String headerStyleId, String footerStyleId, boolean isHaveHeader, boolean isHaveFooter) {
        this.header1Rid = header1Rid;
        this.header2Rid = header2Rid;
        this.footer1Rid = footer1Rid;
        this.footer2Rid = footer2Rid;
        this.headerStyleId = headerStyleId;
        this.footerStyleId = footerStyleId;
        this.isHaveHeader = isHaveHeader;
        this.isHaveFooter = isHaveFooter;
    }

    public DocxInfo() {
    }

    public String getHeader1Rid() {
        return header1Rid;
    }

    public void setHeader1Rid(String header1Rid) {
        this.header1Rid = header1Rid;
    }

    public String getHeader2Rid() {
        return header2Rid;
    }

    public void setHeader2Rid(String header2Rid) {
        this.header2Rid = header2Rid;
    }

    public String getFooter1Rid() {
        return footer1Rid;
    }

    public void setFooter1Rid(String footer1Rid) {
        this.footer1Rid = footer1Rid;
    }

    public String getFooter2Rid() {
        return footer2Rid;
    }

    public void setFooter2Rid(String footer2Rid) {
        this.footer2Rid = footer2Rid;
    }

    public String getHeaderStyleId() {
        return headerStyleId;
    }

    public void setHeaderStyleId(String headerStyleId) {
        this.headerStyleId = headerStyleId;
    }

    public String getFooterStyleId() {
        return footerStyleId;
    }

    public void setFooterStyleId(String footerStyleId) {
        this.footerStyleId = footerStyleId;
    }

    public boolean isHaveHeader() {
        return isHaveHeader;
    }

    public void setHaveHeader(boolean haveHeader) {
        isHaveHeader = haveHeader;
    }

    public boolean isHaveFooter() {
        return isHaveFooter;
    }

    public void setHaveFooter(boolean haveFooter) {
        isHaveFooter = haveFooter;
    }

    public boolean isHaveMainBody() {
        return isHaveMainBody;
    }

    public void setHaveMainBody(boolean haveMainBody) {
        isHaveMainBody = haveMainBody;
    }

    public boolean isHaveNumbering() {
        return isHaveNumbering;
    }

    public void setHaveNumbering(boolean haveNumbering) {
        isHaveNumbering = haveNumbering;
    }
}
