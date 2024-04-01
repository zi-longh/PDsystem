package jnu.service.xmlprocessor;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.zilong.fdbackend.controller.FileController.ROOT_PATH;

/**
 * 预处理
 * */
public class PreProcess {
    /**
     * [完成测试]
     * 对解压后的论文xml文件进行预处理<br>
     * step1: 检查文件夹是否存在<br>
     * step2: 是否是docx解压后的文件夹<br>
     * step3: 对解压后的xml文件进行预处理<br>
     *  <p>3.1 使用默认的comments.xml，清除原有的批注。</p>
     *  <p>3.2 使用默认的header1,header2,footer。</p>
     *  <p>3.3 读取document.xml.rels文件，添加关联信息。</p>
     *  <p>3.4 读取[Content_Types].xml文件，添加文件的类型。</p>
     * 影响：<br>
     * 1. 会清空原有的批注信息<br>
     * 2. 添加默认的批注样式<br>
     * 3. 添加people.xml和comments.xml文件的关联信息<br>
     * @param xmlDirectory 解压后的xml文件夹路径
     */
    public static void paperPreProcess(String xmlDirectory, DocxInfo docxInfo) throws IOException, DocumentException {
        // 1.检查此文件夹是否存在
        Path path = Paths.get(xmlDirectory);
        if (!Files.exists(path) || !Files.isDirectory(path)) { // 文件夹不存在或不是文件夹
            throw new RuntimeException("函数名：paperPreprocess，错误信息：文件夹不存在");
        }

        // 2.是否是docx解压后的文件夹
        /* 检查xml文件夹内的文件名，必须有如下文件存在，否则不是docx解压后的文件夹，抛出异常
         * _rels
         * _rels/.rels
         * docProps
         * word
         * word/_rels
         * word/_rels/document.xml.rels
         * word/document.xml
         * word/styles.xml
         * [Content_Types].xml
         */
        File folder = new File(xmlDirectory);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException("函数名：paperPreprocess，错误信息：文件夹为空");
        }
        boolean[] flag = new boolean[9]; // 0-8分别代表上述文件是否存在, 默认值为false
        for (File file : files) { // 逐个检查文件是否存在
            if (file.getName().equals("_rels")) {
                flag[0] = true;
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    for (File subFile : subFiles) {
                        if (subFile.getName().equals(".rels")) {
                            flag[1] = true;
                            break;
                        }
                    }
                }
            } else if (file.getName().equals("docProps")) {
                flag[2] = true;
            } else if (file.getName().equals("word")) {
                flag[3] = true;
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    for (File subFile : subFiles) {
                        if (subFile.getName().equals("_rels")) {
                            flag[4] = true;
                            File[] subSubFiles = subFile.listFiles();
                            if (subSubFiles != null) {
                                for (File subSubFile : subSubFiles) {
                                    if (subSubFile.getName().equals("document.xml.rels")) {
                                        flag[5] = true;
                                        break;
                                    }
                                }
                            }
                        } else if (subFile.getName().equals("document.xml")) {
                            flag[6] = true;
                        } else if (subFile.getName().equals("styles.xml")) {
                            flag[7] = true;
                        }
                    }
                }
            } else if (file.getName().equals("[Content_Types].xml")) {
                flag[8] = true;
            }
        }
        for (boolean b : flag) {
            if (!b) {
                throw new RuntimeException("函数名：paperPreprocess，错误信息：文件夹不是docx解压后的文件夹或者已经损坏！");
            }
        }
        System.out.println("指定文件夹：" + xmlDirectory + " 是docx解压后的文件夹");

        // 3.对解压后的xml文件进行预处理，为了方便以后在docx中添加批注。
        // 需要初始化或处理comments.xml, header1,header2,footer1,footer2
        // document.xml.rels, [Content_Types].xml

        Path commentsPath = Paths.get(xmlDirectory + "/word/comments.xml");
        if (Files.exists(commentsPath)) {
            Files.delete(commentsPath);
        }
        Files.copy(Paths.get(ROOT_PATH+"/defaultXmlFiles/comments.xml"), commentsPath);

        Path header1Path = Paths.get(xmlDirectory + "/word/header1.xml");
        if (Files.exists(header1Path)) {
            Files.delete(header1Path);
        }
        Files.copy(Paths.get(ROOT_PATH+"/defaultXmlFiles/header1.xml"), header1Path);

        Path header2Path = Paths.get(xmlDirectory + "/word/header2.xml");
        if (Files.exists(header2Path)) {
            Files.delete(header2Path);
        }
        Files.copy(Paths.get(ROOT_PATH+"/defaultXmlFiles/header2.xml"), header2Path);

        Path footer1Path = Paths.get(xmlDirectory + "/word/footer1.xml");
        if (Files.exists(footer1Path)) {
            Files.delete(footer1Path);
        }
        Files.copy(Paths.get(ROOT_PATH+"/defaultXmlFiles/footer1.xml"), footer1Path);

        Path footer2Path = Paths.get(xmlDirectory + "/word/footer2.xml");
        if (Files.exists(footer2Path)) {
            Files.delete(footer2Path);
        }
        Files.copy(Paths.get(ROOT_PATH+"/defaultXmlFiles/footer2.xml"), footer2Path);


        SAXReader saxReader = new SAXReader();
        // 3.2读取document.xml.rels文件，添加关联信息。
        Document documentRel = saxReader.read(xmlDirectory + "/word/_rels/document.xml.rels");
        Element rootElementRel = documentRel.getRootElement();
        List<Element> relationshipNodeList = rootElementRel.elements("Relationship");
        System.out.println("relationshipNodeList.size() = " + relationshipNodeList.size());

        int maxId = 0; // 用于找到最大的Id值
        boolean isExistComments = false; // 是否写了comments.xml文件的关联信息
        boolean isExistHeader1 = false; // 是否写了header1.xml文件的关联信息
        boolean isExistHeader2 = false; // 是否写了header2.xml文件的关联信息
        boolean isExistFooter1 = false; // 是否写了footer1.xml文件的关联信息
        boolean isExistFooter2 = false; // 是否写了footer2.xml文件的关联信息
        boolean isExistNumbering = false; // 是否写了numbering.xml文件的关联信息
        for (Element element : relationshipNodeList) {
            System.out.println(element.attributeValue("Id"));
            if (Objects.equals(element.attributeValue("Target"), "comments.xml")) {
                isExistComments = true;
            }
            if (Objects.equals(element.attributeValue("Target"), "header1.xml")) {
                isExistHeader1 = true;
                docxInfo.setHaveHeader(true);
                docxInfo.setHeader1Rid(element.attributeValue("Id"));
            }
            if (Objects.equals(element.attributeValue("Target"), "header2.xml")) {
                isExistHeader2 = true;
                docxInfo.setHaveHeader(true);
                docxInfo.setHeader2Rid(element.attributeValue("Id"));
            }
            if (Objects.equals(element.attributeValue("Target"), "footer1.xml")) {
                isExistFooter1 = true;
                docxInfo.setHaveFooter(true);
                docxInfo.setFooter1Rid(element.attributeValue("Id"));
            }
            if (Objects.equals(element.attributeValue("Target"), "footer2.xml")) {
                isExistFooter2 = true;
                docxInfo.setHaveFooter(true);
                docxInfo.setFooter2Rid(element.attributeValue("Id"));
            }
            if (Objects.equals(element.attributeValue("Target"), "numbering.xml")) {
                isExistNumbering = true;
                docxInfo.setHaveNumbering(true);
            }
            int id = Integer.parseInt(element.attributeValue("Id").substring(3));
            if (id > maxId) maxId = id; // 记录最大的id
        }
        System.out.println("maxId = " + maxId);


        File contentTypesFile = new File(xmlDirectory + "/[Content_Types].xml");
        Document documentContentTypes = saxReader.read(contentTypesFile);
        Element rootElementContentTypes = documentContentTypes.getRootElement();


        // 如果没有文件的关联信息，则添加到document.xml.rels文件中
        // 如果没有文件类型信息，则添加到[Content_Types].xml文件中
        if (!isExistComments) {
            Element commentsElement = documentRel.getRootElement().addElement("Relationship");
            commentsElement.addAttribute("Id", "rId" + (++maxId));
            commentsElement.addAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/comments");
            commentsElement.addAttribute("Target", "comments.xml");

            rootElementContentTypes.addElement("Override").addAttribute("PartName", "/word/comments.xml").addAttribute("ContentType", "application/vnd.openxmlformats-officedocument.wordprocessingml.comments+xml");
        }
        if (!isExistHeader1) {
            Element header1Element = documentRel.getRootElement().addElement("Relationship");
            header1Element.addAttribute("Id", "rId" + (++maxId));
            header1Element.addAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/header");
            header1Element.addAttribute("Target", "header1.xml");
            docxInfo.setHeader1Rid("rId" + maxId);

            rootElementContentTypes.addElement("Override").addAttribute("PartName", "/word/header1.xml").addAttribute("ContentType", "application/vnd.openxmlformats-officedocument.wordprocessingml.header+xml");
        }
        if (!isExistHeader2) {
            Element header2Element = documentRel.getRootElement().addElement("Relationship");
            header2Element.addAttribute("Id", "rId" + (++maxId));
            header2Element.addAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/header");
            header2Element.addAttribute("Target", "header2.xml");
            docxInfo.setHeader2Rid("rId" + maxId);

            rootElementContentTypes.addElement("Override").addAttribute("PartName", "/word/header2.xml").addAttribute("ContentType", "application/vnd.openxmlformats-officedocument.wordprocessingml.header+xml");
        }
        if (!isExistFooter1) {
            Element footer1Element = documentRel.getRootElement().addElement("Relationship");
            footer1Element.addAttribute("Id", "rId" + (++maxId));
            footer1Element.addAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/footer");
            footer1Element.addAttribute("Target", "footer1.xml");
            docxInfo.setFooter1Rid("rId" + maxId);

            rootElementContentTypes.addElement("Override").addAttribute("PartName", "/word/footer1.xml").addAttribute("ContentType", "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml");
        }
        if (!isExistFooter2) {
            Element footer2Element = documentRel.getRootElement().addElement("Relationship");
            footer2Element.addAttribute("Id", "rId" + (++maxId));
            footer2Element.addAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/footer");
            footer2Element.addAttribute("Target", "footer2.xml");
            docxInfo.setFooter2Rid("rId" + maxId);

            rootElementContentTypes.addElement("Override").addAttribute("PartName", "/word/footer2.xml").addAttribute("ContentType", "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml");
        }
        if (!isExistNumbering) {
            Element numberingElement = documentRel.getRootElement().addElement("Relationship");
            numberingElement.addAttribute("Id", "rId" + (++maxId));
            numberingElement.addAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/numbering");
            numberingElement.addAttribute("Target", "numbering.xml");

            rootElementContentTypes.addElement("Override").addAttribute("PartName", "/word/numbering.xml").addAttribute("ContentType", "application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml");
        }

        // 保存[Content_Types].xml文件
        OutputFormat formatContentTypes = OutputFormat.createPrettyPrint();// 指定XML编码
        formatContentTypes.setEncoding("UTF-8");
        XMLWriter xmlwriterContentTypes = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/[Content_Types].xml")), formatContentTypes);
        xmlwriterContentTypes.write(documentContentTypes);
        xmlwriterContentTypes.close();

        // 保存document.xml.rels 文件
        OutputFormat formatRel = OutputFormat.createPrettyPrint();// 指定XML编码
        formatRel.setEncoding("UTF-8");
        XMLWriter xmlwriterRel = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/_rels/document.xml.rels")), formatRel);
        xmlwriterRel.write(documentRel);
        xmlwriterRel.close();

        /* 如果存在numbering设置，则在numbering中添加默认设置，并且在各级标题的样式设置中（即style.xml文件）设置编号 */
        if (isExistNumbering) {
            String defaultStyleOfNumberingPath = ROOT_PATH+"/defaultXmlFiles/numbering.txt";
            String fileContent = new String(Files.readAllBytes(Paths.get(defaultStyleOfNumberingPath))); // 读取文件内容到字符串
            Document documentNumbering = saxReader.read(xmlDirectory + "/word/numbering.xml");
            // 把默认的编号样式添加到numbering.xml文件中
            Element rootElementNumbering = documentNumbering.getRootElement();
            CDATA cdata = DocumentHelper.createCDATA(fileContent); // 创建CDATA, 用于存放xml格式的字符串
            rootElementNumbering.add(cdata);

            OutputFormat formatNumbering = OutputFormat.createPrettyPrint();// 指定XML编码
            formatNumbering.setEncoding("UTF-8");
            XMLWriter xmlwriterNumbering = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/numbering.xml")), formatNumbering);
            xmlwriterNumbering.write(documentNumbering);
            xmlwriterNumbering.close();

            // 读取处理后的numbering.xml文件, 并将numbering.xml文件中的CDATA标签框删除
            // 读取和写入均采用UTF-8编码
            RandomAccessFile file = new RandomAccessFile(xmlDirectory + "/word/numbering.xml", "rw");
            long fileLength = file.length();
            byte[] content = new byte[(int) fileLength];
            file.readFully(content);
            // 将所有的"<![CDATA["替换为"", "]]>"替换为""
            String modifiedContent = new String(content).replaceAll("<!\\[CDATA\\[", "").replaceAll("]]>", "");

            file.seek(0); // 将文件指针指向文件开头
            // 清空原文件后写入修改后的内容，覆盖原文件，写入编码为UTF-8
            file.setLength(0); // 清空原文件
            file.write(modifiedContent.getBytes());
            file.close();

            // 读取styles.xml文件，设置编号样式
            Document documentStyle = saxReader.read(xmlDirectory + "/word/styles.xml");
            Element rootElementStyle = documentStyle.getRootElement();

            Node node1 = rootElementStyle.selectSingleNode("//w:name[@w:val='heading 1']");
            Node node2 = rootElementStyle.selectSingleNode("//w:name[@w:val='heading 2']");
            Node node3 = rootElementStyle.selectSingleNode("//w:name[@w:val='heading 3']");
            if (node1 != null) {
                Element pPrElement = node1.getParent().element("pPr");
                Element numPrElement = pPrElement.element("numPr");
                if ( numPrElement != null){ // 如果已经存在numPr，则删除
                    numPrElement.detach();
                }
                numPrElement = pPrElement.addElement("w:numPr");
                numPrElement.addElement("w:ilvl").addAttribute("w:val", "0");
                numPrElement.addElement("w:numId").addAttribute("w:val", "99");
            }
            if (node2 != null) {
                Element pPrElement = node2.getParent().element("pPr");
                Element numPrElement = pPrElement.element("numPr");
                if ( numPrElement != null){ // 如果已经存在numPr，则删除
                    numPrElement.detach();
                }
                numPrElement = pPrElement.addElement("w:numPr");
                numPrElement.addElement("w:ilvl").addAttribute("w:val", "1");
                numPrElement.addElement("w:numId").addAttribute("w:val", "99");
            }
            if (node3 != null) {
                Element pPrElement = node3.getParent().element("pPr");
                Element numPrElement = pPrElement.element("numPr");
                if ( numPrElement != null){ // 如果已经存在numPr，则删除
                    numPrElement.detach();
                }
                numPrElement = pPrElement.addElement("w:numPr");
                numPrElement.addElement("w:ilvl").addAttribute("w:val", "2");
                numPrElement.addElement("w:numId").addAttribute("w:val", "99");
            }

            OutputFormat formatStyle = OutputFormat.createPrettyPrint();// 指定XML编码
            formatStyle.setEncoding("UTF-8");
            XMLWriter xmlwriterStyle = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/styles.xml")), formatStyle);
            xmlwriterStyle.write(documentStyle);
            xmlwriterStyle.close();

        }

        /*
        // 3.3在style.xml文件中添加批注样式，默认的批注样式在defaultXmlFiles文件夹下的styleofcomments.txt文件中
        String defaultStyleOfCommentsPath = ROOT_PATH+"/defaultXmlFiles/styleofcomments.txt";
        String fileContent = new String(Files.readAllBytes(Paths.get(defaultStyleOfCommentsPath))); // 读取文件内容到字符串
        Document documentStyle = saxReader.read(xmlDirectory + "/word/styles.xml");
        // 把默认的批注样式添加到style.xml文件中
        Element rootElement = documentStyle.getRootElement();
        CDATA cdata = DocumentHelper.createCDATA(fileContent); // 创建CDATA, 用于存放xml格式的字符串
        rootElement.add(cdata);

        OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
        format.setEncoding("UTF-8");
        XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/styles.xml")), format);
        xmlwriter.write(documentStyle);
        xmlwriter.close();

        // 读取处理后的styles.xml文件, 并将styles.xml文件中的CDATA标签框删除
        // 读取和写入均采用UTF-8编码
        RandomAccessFile file = new RandomAccessFile(xmlDirectory + "/word/styles.xml", "rw");
        long fileLength = file.length();
        byte[] content = new byte[(int) fileLength];
        file.readFully(content);
        // 将所有的"<![CDATA["替换为"", "]]>"替换为""
        String modifiedContent = new String(content).replaceAll("<!\\[CDATA\\[", "").replaceAll("]]>", "");

        file.seek(0); // 将文件指针指向文件开头
        // 清空原文件后写入修改后的内容，覆盖原文件，写入编码为UTF-8
        file.setLength(0); // 清空原文件
        file.write(modifiedContent.getBytes(StandardCharsets.UTF_8));
        file.close();*/

        System.out.println("预处理完成，已清空原有的批注信息，添加了默认的批注样式，可以在docx中添加批注了。");
    }

    public static void clearAllComments(String xmlDirectory) throws IOException, DocumentException {
        // 清空所有的批注标签
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(xmlDirectory + "/word/document.xml");
        List<Node> startNodes = document.selectNodes("//w:commentRangeStart");
        List<Node> endNodes = document.selectNodes("//w:commentRangeEnd");
        List<Node> commentNodes = document.selectNodes("//w:commentReference");
        for (Node node : startNodes) {
            node.detach();
        }
        for (Node node : endNodes) {
            node.detach();
        }
        for (Node node : commentNodes) {
            node.detach();
        }

        // 保存document.xml文件
        OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
        format.setEncoding("UTF-8");
        XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/document.xml")), format);
        xmlwriter.write(document);
        xmlwriter.close();
    }
}
