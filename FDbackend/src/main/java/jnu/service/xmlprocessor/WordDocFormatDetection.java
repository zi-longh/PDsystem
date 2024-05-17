package jnu.service.xmlprocessor;

import jnu.template.*;
import jnu.utils.DocxUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jnu.service.xmlprocessor.ElementCreator.*;
import static jnu.service.xmlprocessor.ReferencesDetect.isReferenceValid;
import static jnu.service.xmlprocessor.Utils.*;
import static jnu.utils.DocxUtils.docxToPdf;
/**
 * word文档处理类
 */
@AllArgsConstructor
@NoArgsConstructor
public class WordDocFormatDetection {
    private Integer commentsNum; // 记录添加的批注数量，在开始检测时初始化为1, 0留给概述批注
    private Integer commentsNumOf1; // 记录自动修改的批注数量
    private Integer commentsNumOf2; // 记录待修改的批注数量
    private Integer commentsNumOf3; // 记录建议修改的批注数量
    private String docFilePath; // 要处理的文件路径
    private String paperName; // 论文名称
    private String paperEnglishName; //论文英文名称
    private String templateId; // 模板id，若无法识别该id则会使用默认模板
    private int paperDtcResult = -1; // 论文格式检测结果。 0检测通过，1通过但可修改，2不通过。 默认值为-1，表示未检测。
    private String resultDocxName; // 处理后的docx文件名，仅当paperDtcResult不为-1时有效。
    private String resultPDFName; // 处理后的pdf文件名，仅当paperDtcResult=0时有效。
    private int docxEndCommentNum; // 记录文档末尾的批注数量
    private List<String> outlineList; // 记录论文的大纲，用于检测论文的目录是否符合要求
    private String username; // 用户名
    private String isSendToTeacher; // 是否发送给老师
    private String teacherUsername; // 老师用户名
    private TemplateInfo templateInfo; // 模板信息


    {
        /* 初始化块 */
        commentsNum = 1;
        docxEndCommentNum = 0;
        outlineList = new ArrayList<>();
        commentsNumOf1 = 0;
        commentsNumOf2 = 0;
        commentsNumOf3 = 0;

    }

    public void init() {
        commentsNum = 1;
        docxEndCommentNum = 0;
        outlineList = new ArrayList<>();
        commentsNumOf1 = 0;
        commentsNumOf2 = 0;
        commentsNumOf3 = 0;
        paperDtcResult = -1;
        resultDocxName = null;
        resultPDFName = null;
        templateInfo = new TemplateInfo(templateId, paperName);
    }

    @Override
    public String toString() {
        return "WordDocFormatDetection{" +
                "commentsNum=" + commentsNum +
                ", commentsNumOf1=" + commentsNumOf1 +
                ", commentsNumOf2=" + commentsNumOf2 +
                ", commentsNumOf3=" + commentsNumOf3 +
                ", docFilePath='" + docFilePath + '\'' +
                ", paperName='" + paperName + '\'' +
                ", paperEnglishName='" + paperEnglishName + '\'' +
                ", templateId='" + templateId + '\'' +
                ", paperDtcResult=" + paperDtcResult +
                ", resultDocxName='" + resultDocxName + '\'' +
                ", resultPDFName='" + resultPDFName + '\'' +
                ", docxEndCommentNum=" + docxEndCommentNum +
                ", outlineList=" + outlineList +
                '}';
    }

    /* 其他方法 */

    public void countCommentsNum(String type) {
        switch (type) {
            case "待修改批注":
                commentsNumOf1++;
                break;
            case "自动修改批注":
                commentsNumOf2++;
                break;
            case "建议修改批注":
                commentsNumOf3++;
                break;
            case "概述批注":
                break;
            default:
                System.err.println("当前函数名：countCommentsNum，错误信息：错误的参数信息：type = " + type + "，请检查参数。");
                throw new RuntimeException("type参数错误。");
        }
    }


    /**
     * 对论文中的重复内容添加内容添加批注，提醒作者修改
     * @param xmlDirectory 解压后的xml文件夹路径
     * @param bodyElement body标签
     * @param contentName 重复内容的名称（标签id名）
     * @param placeValue 重复标签的place属性值
     * @param sectionName 重复内容的具体描述
     * @param placeIndex 控制重复内容的位置，-1表示在标签前（结束标题），1表示在标签后（开始标题）
     * */
    public void addCommentForRepeatContent(String xmlDirectory, Element bodyElement, String contentName, String placeValue, String sectionName, int placeIndex) {
        List<Element> pElements = bodyElement.elements();
        for (int i = 0; i < pElements.size(); i++) {
            Element pElement = pElements.get(i);
            if (pElement.getName().equals("location") && pElement.attributeValue("id").equals(contentName) && pElement.attributeValue("place").equals(placeValue)) {
                addComment(xmlDirectory,
                        pElements.get(i + placeIndex),
                        new ArrayList<String>() {{
                            add("论文的" + contentName + "部分存在重复的" + sectionName + "，请删除多余的部分后再次检测。");
                        }},
                        "待修改批注",
                        "整段");
            }
        }
    }

    /**
     * 对论文中的缺失内容添加内容添加批注，提醒作者添加
     * @param xmlDirectory 解压后的xml文件夹路径
     * @param firstP body标签下的第一个p标签
     * @param contentName 缺失内容的名称（标签id名）
     * @param sectionOrder 论文的次序要求
     * @param index 当前部分在次序要求中的索引
     * */
    public void addCommentForLackContent(String xmlDirectory, Element firstP, String contentName, ArrayList<String> sectionOrder, int index) {
        ArrayList<String> contentList = new ArrayList<>();
        contentList.add("论文疑似缺乏“" + contentName + "”，或已有的“" + contentName + "”部分不完整，请补全。");
        if (index == 0) {
            contentList.add("注意: " + contentName + "部分应该在论文的开头处。");
        } else if (index == sectionOrder.size() - 1) {
            contentList.add("注意: " + contentName + "部分应该在论文的结尾处。");
        } else {
            contentList.add("注意: " + contentName + "部分应该在" + sectionOrder.get(index - 1) + "和" + sectionOrder.get(index + 1) + "之间。");
        }
        addComment(xmlDirectory, firstP, contentList, "待修改批注", "段首");
    }

    /**
     * 用于在检测过程中添加批注，提供多种添加批注的方法。每使用一次，commentsNum自增1。
     * 带有一个重载方法，用于在指定范围内添加批注
     * @param xmlDirectory 解压后的xml文件夹路径
     * @param pElement 待检查的<w:p>标签
     * @param contentList 批注内容
     * @param commentType 批注类型
     * @param placeStr 批注位置
     * */
    private void addComment(String xmlDirectory, Element pElement, ArrayList<String> contentList, String commentType, String placeStr) {
        switch (placeStr) {
            case "段首":
                CommentOperation.addCommentNoRangeInFirstLine(xmlDirectory, pElement, contentList, commentType, commentsNum);
                break;
            case "段尾":
                CommentOperation.addCommentNoRange(xmlDirectory, pElement, contentList, commentType, commentsNum);
                break;
            case "整段":
                CommentOperation.addCommentWithAllP(xmlDirectory, pElement, contentList, commentType, commentsNum);
                break;
            case "句首":
                CommentOperation.addCommentInWr(xmlDirectory, pElement, contentList, commentType, commentsNum);
                break;
            case "文末":
                CommentOperation.addCommentInDocxEnd(xmlDirectory, pElement, contentList, commentType);
                docxEndCommentNum++;
                break;
            default:
                System.err.println("当前函数名：addComment，错误信息：错误的参数信息：placeStr = " + placeStr + "，请检查参数。");
                throw new RuntimeException("placeStr参数错误。");
        }
        commentsNum++;
        countCommentsNum(commentType);
    }

    private void addComment(String xmlDirectory, Element pElement, ArrayList<String> contentList, String commentType, String placeStr, int index) {
        if (placeStr.equals("指定范围")) {
            CommentOperation.addCommentWithRange(xmlDirectory, pElement, index, contentList, commentType, commentsNum);
        } else {
            System.err.println("当前函数名：addComment，错误信息：错误的参数信息：placeStr = " + placeStr + "，请检查参数。");
            throw new RuntimeException("placeStr参数错误。");
        }
        commentsNum++;
        countCommentsNum(commentType);
    }

    /**
     * 添加概述批注并返回检测结果
     * @param xmlDirectory 解压后的xml文件夹路径
     * @return 检测结果。0检测通过，1通过但可修改，2不通过。
     */
    private int generateSummaryComment(String xmlDirectory) {
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(xmlDirectory + "/word/document.xml");
            Element rootElement = document.getRootElement();
            Element bodyElement = rootElement.element("body");
            Element firstP = bodyElement.element("p");
            ArrayList<String> contentList = new ArrayList<>();
            contentList.add("本次检测共生成批注" + (commentsNum - 1) + "处，其中：");
            contentList.add("1. 待修改批注：" + commentsNumOf1 + "处。");
            contentList.add("2. 自动修改批注：" + commentsNumOf2 + "处。");
            contentList.add("3. 建议修改批注：" + commentsNumOf3 + "处。");
            contentList.add("另外有" + docxEndCommentNum + "处文档末尾的批注。");
            contentList.add("请作者根据批注内容进行修改。");
            addComment(xmlDirectory, firstP, contentList, "概述批注", "段首");

            // 保存document.xml文件
            OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
            format.setEncoding("UTF-8");
            XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/document.xml")), format);
            xmlwriter.write(document);
            xmlwriter.close();

            if (commentsNumOf1 != 0) {
                return 2;
            } else if (commentsNumOf3 != 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO: 返回值待定
     * 检测论文的“诚信声明”部分，SOH=Statement of Honesty
     * @param xmlDirectory 解压后的xml文件夹路径
     */
    public void detectSOH(String xmlDirectory, ArrayList<String> sectionOrder) {
        // 获取论文的“诚信声明”部分的模板要求
        StatementOfHonesty sohReq = templateInfo.getStatementOfHonesty();

        // 获取次序要求和诚信声明的次序id
        int sohIndex = sectionOrder.indexOf("诚信声明");

        // 读取document.xml文件
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element rootElement = document.getRootElement();
            // 1. 读取“诚信声明”部分的标签，判断是否存在或重复
            List<Node> locationNodes = rootElement.selectNodes("//location[@id='诚信声明']");
            List<Node> locationNodesStart = rootElement.selectNodes("//location[@id='诚信声明' and @place='start']");
            List<Node> locationNodesEnd = rootElement.selectNodes("//location[@id='诚信声明' and @place='end']");
            // 1. 非正常情况
            if (!(locationNodesStart.size() == 1 && locationNodesEnd.size() == 1)) {
                // 1.1 未找到“诚信声明”部分的标签
                if (locationNodes.isEmpty() || locationNodesStart.isEmpty() || locationNodesEnd.isEmpty()) {
                    System.out.println("未找到“诚信声明”部分的标签");
                    // 未找到“诚信声明”部分的标签，在顶部插入一个批注说明缺乏“诚信声明”部分。
                    Element firstP = rootElement.element("body").element("p");
                    addCommentForLackContent(xmlDirectory, firstP, StatementOfHonesty.contentName, sectionOrder, sohIndex);
                }
                // 1.2 有多个“诚信声明”部分（重复） -> 测试完成
                if (locationNodesStart.size() > 1 || locationNodesEnd.size() > 1) {
                    if (locationNodesStart.size() > 1) {
                        System.out.println("存在多个“诚信声明”部分的开始标签");
                        // 在“诚信声明”开始标签之后第一个段落中插入一个批注说明存在多个“诚信声明”部分,批注直接选中整个段落
                        /* 标记有冗余的“诚信声明”标题 */
                        List<Element> pElements = rootElement.elements().get(0).elements(); // 获取body标签下的所有子标签
                        Element nextP = null; // 用于记录<location>标签的下一个<p>
                        for (int i = 0; i < pElements.size(); i++) {
                            if (pElements.get(i).getName().equals("location") && pElements.get(i).attributeValue("id").equals("诚信声明") && pElements.get(i).attributeValue("place").equals("start")) {
                                nextP = pElements.get(i + 1); // 定位到“诚信声明”标题处，直接选中整个段落添加批注。
                                ArrayList<String> commentList = new ArrayList<>();
                                commentList.add("存在冗余的“诚信声明”标题，请删除多余的“诚信声明”部分后，再次检查。");
                                String commentType = "待修改批注";
                                addComment(xmlDirectory, nextP, commentList, commentType, "整段");
                            }
                        }
                    }
                    if (locationNodesEnd.size() > 1) {
                        /* TODO: 等待测试 */
                        System.out.println("存在多个“诚信声明”部分的结束标签");
                        // 在“诚信声明”结束标签之前第一个段落中插入一个批注说明存在多个“诚信声明”部分,批注直接选中整个段落
                        List<Element> pElements = rootElement.elements().get(0).elements(); // 获取body标签下的所有子标签
                        Element previousP = null; // 用于记录<location>结束标签的上一个<p>
                        for (int i = 0; i < pElements.size(); i++) {
                            if (pElements.get(i).getName().equals("location") && pElements.get(i).attributeValue("id").equals("诚信声明") && pElements.get(i).attributeValue("place").equals("end")) {
                                previousP = pElements.get(i - 1);
                                ArrayList<String> contentList = new ArrayList<>();
                                contentList.add("存在冗余的“诚信声明”署名或日期，请删除冗余部分。");
                                String commentType = "待修改批注";
                                addComment(xmlDirectory, previousP, contentList, commentType, "整段");
                            }
                        }

                    }
                }
                // 保存document.xml文件
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            }
            // 2. 当“诚信声明”部分的标签存在，且开始和结束标签都只有一个时，检查样式是否正确
            else {
                /* 从start干到end TODO:
                 *  根据格式要求类逐个判断样式是否正确，错的话就改。外面有的样式先看外面，使用了样式id外面的没有再深入style.xml找，改
                 */
                List<Element> pElements = rootElement.element("body").elements();// 获取body标签下的所有子标签
                int startIndex = -1; // 用于记录“诚信声明”部分的开始标签的索引
                int endIndex = -1; // 用于记录“诚信声明”部分的结束标签的索引

                // 2.1 定位“诚信声明”部分的开始和结束标签的索引
                for (int i = 0; i < pElements.size(); i++) {
                    Element locationElement = pElements.get(i);
                    if (locationElement.getName().equals("location") && locationElement.attributeValue("id").equals("诚信声明")) {
                        if (locationElement.attributeValue("place").equals("start")) {
                            startIndex = i;
                        } else if (locationElement.attributeValue("place").equals("end")) {
                            endIndex = i;
                        }
                    }
                    if (startIndex != -1 && endIndex != -1) {
                        break; // 找到了开始和结束标签，结束循环
                    }
                }

                // 2.2 结束标签在开始标签之前的情况
                if (endIndex <= startIndex) {
                    Element previousP = pElements.get(endIndex - 1);
                    // 选中整个著名和日期的段落添加批注
                    ArrayList<String> contentList = new ArrayList<>();
                    contentList.add("异常的著名和日期内容，请检查“诚信声明”部分，著名和日期位置应该在诚信声明正文的后面。请修改后重新检测！");
                    addComment(xmlDirectory, previousP, contentList, "待修改批注", "整段");
                    // 保存document.xml文件
                    OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                    format.setEncoding("UTF-8");
                    XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                    xmlwriter.write(document);
                    xmlwriter.close();
                }

                // 2.3 检查“诚信声明”部分的样式是否正确
                for (int i = startIndex; i <= endIndex; i++) {
                    // 检查诚信声明样式是否正确
                    Element pElement = pElements.get(i);
                    /* 标题检查 */
                    if (i == startIndex + 1) {
                        detectHeadingStyle(xmlDirectory, pElement, StatementOfHonesty.contentName, sohReq.getHeadingRep());
                        continue;
                    }
                    /* 段落内容格式检查 */
                    if (pElement.getName().equals("p")) { // 只检查<w:p>标签
                        if (pElement.selectNodes("descendant::w:t").isEmpty()) {
                            continue; // 没有文本内容的不检查
                        }
                        detectParagraphStyle(xmlDirectory, pElement, StatementOfHonesty.contentName, sohReq.getParagraphRep());
                    }

                    if (i == endIndex - 1) { // 诚信声明的署名和日期，需要修复额外添加的段落缩进。
                        Element pPr = pElement.element("pPr");
                        if (pPr != null) {
                            pPr.remove(pPr.element("ind"));
                        }
                    }
                }

                /* 诚信声明内容（文本）内容检查 */
                String sohContent = sohReq.getContent();
                for (int i = startIndex + 2; i < endIndex; i++) {
                    Element pElement = pElements.get(i);
                    if (pElement.selectNodes("descendant::w:t").isEmpty()) {
                        continue; // 没有文本内容的不检查
                    }
                    List<Node> tNodes = pElement.selectNodes("descendant::w:t");
                    StringBuilder content = new StringBuilder();
                    for (Node node : tNodes) {
                        content.append(node.getText());
                    }

                    System.out.println("读取到的诚信声明内容：" + content.toString() + "\n模板要求的内容：" + sohContent);
                    if (!content.toString().trim().equals(sohContent)) {
                        System.out.println("诚信声明内容不符合要求");
                        // 发现了不符合要求的内容，添加批注
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("请仔细检查“诚信声明”的正文部分，疑似与模板不符。");
                        addComment(xmlDirectory, pElement, contentList, "待修改批注", "段尾");

                        // 保存document.xml文件
                        OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                        format.setEncoding("UTF-8");
                        XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/document.xml")), format);
                        xmlwriter.write(document);
                        xmlwriter.close();
                    }
                    break;
                }

                // 保存document.xml文件
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/document.xml")), format);
                xmlwriter.write(document);
                xmlwriter.close();
            }
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO:
     * 检测论文的“中文摘要”部分
     * @param xmlDirectory 解压后的xml文件夹路径
     */
    public void detectAbstractOfChinese(String xmlDirectory, ArrayList<String> sectionOrder) {

        // 获取论文的“中文摘要”部分的模板要求
        AbstractOfChinese AOCReq = templateInfo.getAbstractOfChinese();

        // 获取次序要求和次序id
        int aocIndex = sectionOrder.indexOf(AbstractOfChinese.contentName);

        // 开始检测
        // 1. 处理非正常情况，即“中文摘要”部分内容不完整或冗余
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element rootElement = document.getRootElement();
            List<Node> locationNodes = rootElement.selectNodes("//location[@id='中文摘要']");
            List<Node> locationNodesStart = rootElement.selectNodes("//location[@id='中文摘要' and @place='start']");
            List<Node> locationNodesEnd = rootElement.selectNodes("//location[@id='中文摘要' and @place='end']");
            List<Node> locationNodesAbstract = rootElement.selectNodes("//location[@id='中文摘要' and @place='abstract']");
            Element bodyElement = rootElement.element("body");

            if (!(locationNodes.size() == 3 && locationNodesStart.size() == 1 && locationNodesEnd.size() == 1 && locationNodesAbstract.size() == 1)) {
                // 1.1 有冗余部分
                if (locationNodesStart.size() > 1 || locationNodesEnd.size() > 1 || locationNodesAbstract.size() > 1) {
                    // 1.1.1 有多个“中文摘要”部分的开始标签
                    if (locationNodesStart.size() > 1) {
                        addCommentForRepeatContent(xmlDirectory, bodyElement, AbstractOfChinese.contentName, "start", "中文标题", 1);
                    }
                    if (locationNodesEnd.size() > 1) {
                        addCommentForRepeatContent(xmlDirectory, bodyElement, AbstractOfChinese.contentName, "end", AbstractOfChinese.markWord2, -1);
                    }
                    if (locationNodesAbstract.size() > 1) {
                        addCommentForRepeatContent(xmlDirectory, bodyElement, AbstractOfChinese.contentName, "abstract", AbstractOfChinese.markWord1, 1);
                    }
                }
                // 1.2 有部分缺失
                if (locationNodes.isEmpty() || locationNodesStart.isEmpty() || locationNodesEnd.isEmpty() || locationNodesAbstract.isEmpty()) {
                    addCommentForLackContent(xmlDirectory, bodyElement.element("p"), AbstractOfChinese.contentName, sectionOrder, aocIndex);
                }

                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            } else {
                // 2. 各部分都存在且唯一，开始检查样式
                int startIndex = -1; // 用于记录“中文摘要”部分的开始标签的索引
                int abstractIndex = -1; // 用于记录“中文摘要”部分的正文标签的索引
                int endIndex = -1; // 用于记录“中文摘要”部分的结束标签的索引
                List<Element> pElements = bodyElement.elements();// 获取body标签下的所有子标签
                for (int i = 0; i < pElements.size(); i++) {
                    Element locationElement = pElements.get(i);
                    if (locationElement.getName().equals("location") && locationElement.attributeValue("id").equals("中文摘要")) {
                        if (locationElement.attributeValue("place").equals("start")) {
                            startIndex = i;
                        } else if (locationElement.attributeValue("place").equals("end")) {
                            endIndex = i;
                        } else if (locationElement.attributeValue("place").equals("abstract")) {
                            abstractIndex = i;
                        }
                    }
                    if (startIndex != -1 && endIndex != -1 && abstractIndex != -1) {
                        break; // 找到了开始和结束标签，结束循环
                    }
                }
                // 2.1 顺序异常的情况
                if (!(startIndex < abstractIndex && abstractIndex < endIndex)) {
                    // 2.1.1 开始标签在正文标签之后
                    if (startIndex > abstractIndex) {
                        addComment(xmlDirectory,
                                pElements.get(abstractIndex + 1),
                                new ArrayList<String>() {{
                                    add("论文“中文摘要”的摘要正文应当在论文中文标题之后，请修改！");
                                }},
                                "待修改批注", "整段"
                        );
                    }
                    // 2.1.2 结束标签在正文标签之前
                    if (abstractIndex > endIndex) {
                        addComment(xmlDirectory,
                                pElements.get(endIndex - 1),
                                new ArrayList<String>() {{
                                    add("论文“中文摘要”的关键词应当在摘要正文之后，请修改！");
                                }},
                                "待修改批注", "整段"
                        );
                    }

                    OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                    format.setEncoding("UTF-8");
                    XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                    xmlwriter.write(document);
                    xmlwriter.close();
                }

                // 2.2 检查“中文摘要”部分的样式是否正确
                ArrayList<Element> emptyElements = new ArrayList<>(); // 用于记录没有文本内容的<w:p>标签
                for (int i = startIndex + 1; i <= endIndex; i++) {
                    Element pElement = pElements.get(i);
                    if (!Objects.equals(pElement.getName(), "p")) {
                        continue; // 不是<w:p>标签，不检查
                    }
                    /* 标题检查 */
                    if (i == startIndex + 1) {
                        detectHeadingStyle(xmlDirectory, pElement, AbstractOfChinese.contentName, AOCReq.getHeadingRep());
                        continue;
                    }
                    /* 跳过空段 */
                    if (isEmptyParagraph(pElement)) {
                        // 没有文本内容的空段落会存入emptyElements，后续统一删除
                        emptyElements.add(pElement);
                        continue;
                    }
                    /* 摘要正文内容 */
                    // 处理方法是统一处理，先读取所有数据再按要求生成
                    if (i == abstractIndex + 1) {
                        int contentLength = convertToStdAbstract(
                                pElement,
                                AOCReq.getParagraphRep(),
                                AOCReq.isPrefixBold(),
                                AOCReq.getPrefixFont(),
                                AOCReq.getRecommendedMaxContentLength()
                        );
                        addComment(xmlDirectory, pElement,
                                new ArrayList<String>() {{
                                    add("摘要正文内容已自动修正为标准格式。");
                                }}, "自动修改批注", "段首");
                        if (contentLength > AOCReq.getRecommendedMaxContentLength() && AOCReq.getRecommendedMaxContentLength() != 0) {
                            // 添加建议修改批注
                            addComment(xmlDirectory, pElement,
                                    new ArrayList<String>() {{
                                        add("摘要正文内容字数已经达到" + contentLength + "字，超过了推荐字数：" + AOCReq.getRecommendedMaxContentLength() + "字, 建议适当删减。");
                                    }}, "建议修改批注", "整段");
                        }

                        if (contentLength < AOCReq.getRecommendedMinContentLength() && AOCReq.getRecommendedMinContentLength() != 0) {
                            // 添加建议修改批注
                            addComment(xmlDirectory, pElement,
                                    new ArrayList<String>() {{
                                        add("摘要正文内容字数为" + contentLength + "字，低于了推荐字数：" + AOCReq.getRecommendedMinContentLength() + "字, 建议适当补充。");
                                    }}, "建议修改批注", "整段");
                        }
                        continue;
                    }
                    /* 关键词内容检查 */
                    if (i == endIndex - 1) {
                        int keyWordNum = convertToStdKeywords(
                                pElement,
                                AOCReq.getParagraphRep(),
                                AOCReq.isPrefixBold(),
                                AOCReq.getPrefixFont(),
                                AOCReq.getRecommendedMaxKeywordsCount()
                        );
                        addComment(xmlDirectory, pElement,
                                new ArrayList<String>() {{
                                    add("关键词内容已自动修正为标准格式。");
                                }}, "自动修改批注", "段首");
                        if (keyWordNum > AOCReq.getRecommendedMaxKeywordsCount() && AOCReq.getRecommendedMaxKeywordsCount() != 0) {
                            // 添加建议修改批注
                            addComment(xmlDirectory, pElement,
                                    new ArrayList<String>() {{
                                        add("关键词数量已经达到" + keyWordNum + "个，超过了推荐数量：" + AOCReq.getRecommendedMaxKeywordsCount() + "个, 建议适当删减。");
                                    }}, "建议修改批注", "整段");
                        }

                        if (keyWordNum < AOCReq.getRecommendedMinKeywordsCount() && AOCReq.getRecommendedMinKeywordsCount() != 0) {
                            // 添加建议修改批注
                            addComment(xmlDirectory, pElement,
                                    new ArrayList<String>() {{
                                        add("关键词数量为" + keyWordNum + "个，低于了推荐数量：" + AOCReq.getRecommendedMinKeywordsCount() + "个, 建议适当补充。");
                                    }}, "建议修改批注", "整段");
                        }

                        break;
                    }
                    /* 其他内容按照段落标准检测，正常情况下不会运行 */
                    detectParagraphStyle(xmlDirectory, pElement, AbstractOfChinese.contentName, AOCReq.getParagraphRep());
                }
                // 删除空段落
                for (Element emptyElement : emptyElements) {
                    emptyElement.detach();
                }
                // 保存document.xml文件
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            }
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO:检测论文的“英文摘要”部分
     * 与中文摘要检测方法类似，不再赘述
     * @param xmlDirectory 解压后的xml文件夹路径
     */
    public void detectAbstractOfEnglish(String xmlDirectory, ArrayList<String> sectionOrder) {
        // 获取论文的“英文摘要”部分的模板要求
        AbstractOfEnglish AOEReq = templateInfo.getAbstractOfEnglish();
        // 获取次序要求和次序id
        int aocIndex = sectionOrder.indexOf(AbstractOfEnglish.contentName);

        // 开始检测
        // 1. 处理非正常情况，即“英文摘要”部分内容不完整或冗余
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element rootElement = document.getRootElement();
            List<Node> locationNodes = rootElement.selectNodes("//location[@id='英文摘要']");
            List<Node> locationNodesStart = rootElement.selectNodes("//location[@id='英文摘要' and @place='start']");
            List<Node> locationNodesEnd = rootElement.selectNodes("//location[@id='英文摘要' and @place='end']");
            List<Node> locationNodesAbstract = rootElement.selectNodes("//location[@id='英文摘要' and @place='abstract']");
            Element bodyElement = rootElement.element("body");

            if (!(locationNodes.size() == 3 && locationNodesStart.size() == 1 && locationNodesEnd.size() == 1 && locationNodesAbstract.size() == 1)) {
                // 1.1 有冗余部分
                if (locationNodesStart.size() > 1 || locationNodesEnd.size() > 1 || locationNodesAbstract.size() > 1) {
                    // 1.1.1 有多个“英文摘要”部分的开始标签
                    if (locationNodesStart.size() > 1) {
                        addCommentForRepeatContent(xmlDirectory, bodyElement, AbstractOfEnglish.contentName, "start", "英文标题", 1);
                    }
                    if (locationNodesEnd.size() > 1) {
                        addCommentForRepeatContent(xmlDirectory, bodyElement, AbstractOfEnglish.contentName, "end", AbstractOfEnglish.markWord2, -1);
                    }
                    if (locationNodesAbstract.size() > 1) {
                        addCommentForRepeatContent(xmlDirectory, bodyElement, AbstractOfEnglish.contentName, "abstract", AbstractOfEnglish.markWord1, 1);
                    }
                }
                // 1.2 有部分缺失
                if (locationNodes.isEmpty() || locationNodesStart.isEmpty() || locationNodesEnd.isEmpty() || locationNodesAbstract.isEmpty()) {
                    addCommentForLackContent(xmlDirectory, bodyElement.element("p"), AbstractOfEnglish.contentName, sectionOrder, aocIndex);
                }

                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            } else {
                // 2. 各部分都存在且唯一，开始检查样式
                int startIndex = -1; // 用于记录“英文摘要”部分的开始标签的索引
                int abstractIndex = -1; // 用于记录“英文摘要”部分的正文标签的索引
                int endIndex = -1; // 用于记录“英文摘要”部分的结束标签的索引
                List<Element> pElements = bodyElement.elements();// 获取body标签下的所有子标签
                for (int i = 0; i < pElements.size(); i++) {
                    Element locationElement = pElements.get(i);
                    if (locationElement.getName().equals("location") && locationElement.attributeValue("id").equals("英文摘要")) {
                        if (locationElement.attributeValue("place").equals("start")) {
                            startIndex = i;
                        } else if (locationElement.attributeValue("place").equals("end")) {
                            endIndex = i;
                        } else if (locationElement.attributeValue("place").equals("abstract")) {
                            abstractIndex = i;
                        }
                    }
                    if (startIndex != -1 && endIndex != -1 && abstractIndex != -1) {
                        break; // 找到了开始和结束标签，结束循环
                    }
                }
                // 2.1 顺序异常的情况
                if (!(startIndex < abstractIndex && abstractIndex < endIndex)) {
                    // 2.1.1 开始标签在正文标签之后
                    if (startIndex > abstractIndex) {
                        addComment(xmlDirectory,
                                pElements.get(abstractIndex + 1),
                                new ArrayList<String>() {{
                                    add("论文“英文摘要”的摘要正文应当在论文英文标题之后，请修改！");
                                }},
                                "待修改批注", "整段"
                        );
                    }
                    // 2.1.2 结束标签在正文标签之前
                    if (abstractIndex > endIndex) {
                        addComment(xmlDirectory,
                                pElements.get(endIndex - 1),
                                new ArrayList<String>() {{
                                    add("论文“英文摘要”的关键词应当在摘要正文之后，请修改！");
                                }},
                                "待修改批注", "整段"
                        );
                    }

                    OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                    format.setEncoding("UTF-8");
                    XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                    xmlwriter.write(document);
                    xmlwriter.close();
                }

                // 2.2 检查“英文摘要”部分的样式是否正确
                ArrayList<Element> emptyElements = new ArrayList<>(); // 用于记录没有文本内容的<w:p>标签
                for (int i = startIndex + 1; i <= endIndex; i++) {
                    Element pElement = pElements.get(i);
                    if (!Objects.equals(pElement.getName(), "p")) {
                        continue; // 不是<w:p>标签，不检查
                    }
                    /* 标题检查 */
                    if (i == startIndex + 1) {
                        detectHeadingStyle(xmlDirectory, pElement, AbstractOfEnglish.contentName, AOEReq.getHeadingRep());
                        continue;
                    }
                    /* 跳过空段 */
                    if (isEmptyParagraph(pElement)) {
                        // 没有文本内容的空段落会存入emptyElements，后续统一删除
                        emptyElements.add(pElement);
                        continue;
                    }
                    /* 摘要正文内容 */
                    // 处理方法是统一处理，先读取所有数据再按要求生成
                    if (i == abstractIndex + 1) {
                        int contentLength = convertToStdAbstractEnglish(
                                pElement,
                                AOEReq.getParagraphRep(),
                                AOEReq.isPrefixBold(),
                                AOEReq.getPrefixFont(),
                                AOEReq.getRecommendedMaxContentLength()
                        );
                        addComment(xmlDirectory, pElement,
                                new ArrayList<String>() {{
                                    add("摘要正文内容已自动修正为标准格式。");
                                }}, "自动修改批注", "段首");
                        if (contentLength > AOEReq.getRecommendedMaxContentLength() && AOEReq.getRecommendedMaxContentLength() != 0) {
                            // 添加建议修改批注
                            addComment(xmlDirectory, pElement,
                                    new ArrayList<String>() {{
                                        add("摘要正文内容字数已经达到" + contentLength + "个单词，超过了推荐字数：" + AOEReq.getRecommendedMaxContentLength() + "字, 建议适当删减。");
                                    }}, "建议修改批注", "整段");
                        }

                        System.out.println("--------");
                        System.out.println("摘要正文内容字数：" + contentLength + "个单词");
                        System.out.println("--------");
                        if (contentLength < AOEReq.getRecommendedMinContentLength() && AOEReq.getRecommendedMinContentLength() != 0) {
                            // 添加建议修改批注
                            addComment(xmlDirectory, pElement,
                                    new ArrayList<String>() {{
                                        add("摘要正文内容字数为" + contentLength + "个单词，低于了推荐字数：" + AOEReq.getRecommendedMinContentLength() + "字, 建议适当补充。");
                                    }}, "建议修改批注", "整段");
                        }

                        continue;
                    }
                    /* 关键词内容检查 */
                    if (i == endIndex - 1) {
                        int keyWordNum = convertToStdKeywordsEnglish(
                                pElement,
                                AOEReq.getParagraphRep(),
                                AOEReq.isPrefixBold(),
                                AOEReq.getPrefixFont(),
                                AOEReq.getRecommendedMaxKeywordsCount()
                        );
                        addComment(xmlDirectory, pElement,
                                new ArrayList<String>() {{
                                    add("关键词内容已自动修正为标准格式。");
                                }}, "自动修改批注", "段首");
                        if (keyWordNum > AOEReq.getRecommendedMaxKeywordsCount() && AOEReq.getRecommendedMaxKeywordsCount() != 0) {
                            // 添加建议修改批注
                            addComment(xmlDirectory, pElement,
                                    new ArrayList<String>() {{
                                        add("关键词数量已经达到" + keyWordNum + "个，超过了推荐数量：" + AOEReq.getRecommendedMaxKeywordsCount() + "个, 建议适当删减。");
                                    }}, "建议修改批注", "整段");
                        }

                        if (keyWordNum < AOEReq.getRecommendedMinKeywordsCount() && AOEReq.getRecommendedMinKeywordsCount() != 0) {
                            // 添加建议修改批注
                            addComment(xmlDirectory, pElement,
                                    new ArrayList<String>() {{
                                        add("关键词数量为" + keyWordNum + "个，低于了推荐数量：" + AOEReq.getRecommendedMinKeywordsCount() + "个, 建议适当补充。");
                                    }}, "建议修改批注", "整段");
                        }
                        break;
                    }
                    /* 其他内容按照段落标准检测，正常情况下不会运行 */
                    detectParagraphStyle(xmlDirectory, pElement, AbstractOfEnglish.contentName, AOEReq.getParagraphRep());
                }
                // 删除空段落
                for (Element emptyElement : emptyElements) {
                    emptyElement.detach();
                }
                // 保存document.xml文件
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            }
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * TODO:检测论文的“结论"部分
     * @param xmlDirectory 解压后的xml文件夹路径
     * @param sectionOrder 论文各部分的次序
     * */
    public void detectConclusion(String xmlDirectory, ArrayList<String> sectionOrder) {
        // 获取论文的“结论”部分的模板要求
        Conclusion conclusionReq = templateInfo.getConclusion();
        // 获取次序id
        int conclusionIndex = sectionOrder.indexOf(Conclusion.contentName);

        // 开始检测
        // 1. 处理非正常情况，即“结论”部分内容缺失或冗余
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element rootElement = document.getRootElement();
            List<Node> locationNodes = rootElement.selectNodes("//location[@id='结论']");
            Element bodyElement = rootElement.element("body");

            if (!(locationNodes.size() == 1)) {
                if (locationNodes.isEmpty()) {
                    addCommentForLackContent(xmlDirectory, bodyElement.element("p"), Conclusion.contentName, sectionOrder, conclusionIndex);
                } else if (locationNodes.size() > 1) {
                    addCommentForRepeatContent(xmlDirectory, bodyElement, Conclusion.contentName, "start", Conclusion.markWord, 1);
                }
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            } else {
                // 2. 存在且唯一，开始检查样式
                int startIndex = -1; // 用于记录“结论”部分的开始标签的索引
                List<Element> pElements = bodyElement.elements();// 获取body标签下的所有子标签
                for (int i = 0; i < pElements.size(); i++) {
                    Element locationElement = pElements.get(i);
                    if (locationElement.getName().equals("location") && locationElement.attributeValue("id").equals("结论")) {
                        startIndex = i;
                    }
                    if (startIndex != -1) {
                        break; // 找到了开始标签，结束循环
                    }
                }
                ArrayList<Element> emptyElements = new ArrayList<>(); // 用于记录没有文本内容的<w:p>标签
                StringBuilder content = new StringBuilder(); // 用于记录结论的内容
                for (int i = startIndex + 1; i < pElements.size(); i++) {
                    Element pElement = pElements.get(i);
                    if (Objects.equals(pElement.getName(), "location")) {
                        break;
                        // 遇到了下一个location标签，结束循环
                    }
                    if (!Objects.equals(pElement.getName(), "p")) {
                        continue; // 不是<w:p>标签，不检查
                    }
                    /* 标题检查 */
                    if (i == startIndex + 1) {
                        detectHeadingStyle(xmlDirectory, pElement, Conclusion.contentName, conclusionReq.getHeadingRep());
                        continue;
                    }
                    /* 段落内容格式检查和空段检查 */
                    if (isEmptyParagraph(pElement)) {
                        // 没有文本内容的空段落会存入emptyElements，后续统一删除
                        emptyElements.add(pElement);
                        continue;
                    }
                    detectParagraphStyle(xmlDirectory, pElement, Conclusion.contentName, conclusionReq.getParagraphRep());

                    // 读取结论的内容
                    List<Node> tNodes = pElement.selectNodes("descendant::w:t");
                    for (Node node : tNodes) {
                        content.append(node.getText().trim());
                    }
                }

                // 删除空段落
                for (Element emptyElement : emptyElements) {
                    emptyElement.detach();
                }

                // 内容字数检测
                int contentLength = content.toString().length();
                if (contentLength > conclusionReq.getRecommendedMaxContentLength() && conclusionReq.getRecommendedMaxContentLength() != 0) {
                    // 添加建议修改批注
                    addComment(xmlDirectory, pElements.get(startIndex + 1),
                            new ArrayList<String>() {{
                                add("结论内容字数已经达到" + contentLength + "字，超过了推荐字数：" + conclusionReq.getRecommendedMaxContentLength() + "字, 建议适当删减。");
                            }}, "建议修改批注", "段尾");
                }

                // 保存document.xml文件
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            }
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO:检测论文的“参考文献”部分
     * 实际实现方法与结论部分检测方法类似，不再赘述
     * @param xmlDirectory 解压后的xml文件夹路径
     * @param sectionOrder 论文各部分的次序
     * */
    public void detectThanks(String xmlDirectory, ArrayList<String> sectionOrder) {
        // 获取论文的“致谢”部分的模板要求
        Thanks thanksReq = templateInfo.getThanks();
        // 获取次序id
        int index = sectionOrder.indexOf(Thanks.contentName);

        // 开始检测
        // 1. 处理非正常情况，即“致谢”部分内容确实或冗余
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element rootElement = document.getRootElement();
            List<Node> locationNodes = rootElement.selectNodes("//location[@id='致谢']");
            Element bodyElement = rootElement.element("body");

            if (!(locationNodes.size() == 1)) {
                if (locationNodes.isEmpty()) {
                    addCommentForLackContent(xmlDirectory, bodyElement.element("p"), Thanks.contentName, sectionOrder, index);
                } else if (locationNodes.size() > 1) {
                    addCommentForRepeatContent(xmlDirectory, bodyElement, Thanks.contentName, "start", Thanks.markWord, 1);
                }
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            } else {
                // 2. 存在且唯一，开始检查样式
                int startIndex = -1; // 用于记录“致谢”部分的开始标签的索引
                List<Element> pElements = bodyElement.elements();// 获取body标签下的所有子标签
                for (int i = 0; i < pElements.size(); i++) {
                    Element locationElement = pElements.get(i);
                    if (locationElement.getName().equals("location") && locationElement.attributeValue("id").equals("致谢")) {
                        startIndex = i;
                    }
                    if (startIndex != -1) {
                        break; // 找到了开始标签，结束循环
                    }
                }
                ArrayList<Element> emptyElements = new ArrayList<>(); // 用于记录没有文本内容的<w:p>标签
                StringBuilder content = new StringBuilder(); // 用于记录致谢的内容
                for (int i = startIndex + 1; i < pElements.size(); i++) {
                    Element pElement = pElements.get(i);
                    if (Objects.equals(pElement.getName(), "location")) {
                        break;
                        // 遇到了下一个location标签，结束循环
                    }
                    if (!Objects.equals(pElement.getName(), "p")) {
                        continue; // 不是<w:p>标签，不检查
                    }
                    /* 标题检查 */
                    if (i == startIndex + 1) {
                        detectHeadingStyle(xmlDirectory, pElement, Thanks.contentName, thanksReq.getHeadingRep());
                        continue;
                    }
                    /* 段落内容格式检查和空段检查 */
                    if (isEmptyParagraph(pElement)) {
                        // 没有文本内容的空段落会存入emptyElements，后续统一删除
                        emptyElements.add(pElement);
                        continue;
                    }
                    detectParagraphStyle(xmlDirectory, pElement, Thanks.contentName, thanksReq.getParagraphRep());

                    // 读取致谢的内容
                    List<Node> tNodes = pElement.selectNodes("descendant::w:t");
                    for (Node node : tNodes) {
                        content.append(node.getText().trim());
                    }
                }

                // 删除空段落
                for (Element emptyElement : emptyElements) {
                    emptyElement.detach();
                }

                // 内容字数检测
                int contentLength = content.toString().length();
                if (contentLength > thanksReq.getRecommendedMaxContentLength() && thanksReq.getRecommendedMaxContentLength() != 0) {
                    // 添加建议修改批注
                    addComment(xmlDirectory, pElements.get(startIndex + 1),
                            new ArrayList<String>() {{
                                add("致谢内容已经达到" + contentLength + "字，超过了推荐字数：" + thanksReq.getRecommendedMaxContentLength() + "字, 建议适当删减。");
                            }}, "建议修改批注", "段尾");
                }

                // 保存document.xml文件
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            }
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO: 暂时只完成标题和段落样式的检测，之后做参考文献的内容
     * 检测参考文献的样式是否正确
     * */
    public void detectReferences(String xmlDirectory, ArrayList<String> sectionOrder) {
        // 获取论文的“参考文献”部分的模板要求
        References referencesReq = templateInfo.getReferences();
        // 获取次序id
        int index = sectionOrder.indexOf(References.contentName);

        // 开始检测
        // 1. 处理非正常情况，即“参考文献”部分内容确实或冗余
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element rootElement = document.getRootElement();
            List<Node> locationNodes = rootElement.selectNodes("//location[@id='参考文献']");
            Element bodyElement = rootElement.element("body");

            if (!(locationNodes.size() == 1)) {
                if (locationNodes.isEmpty()) {
                    addCommentForLackContent(xmlDirectory, bodyElement.element("p"), References.contentName, sectionOrder, index);
                } else if (locationNodes.size() > 1) {
                    addCommentForRepeatContent(xmlDirectory, bodyElement, References.contentName, "start", References.markWord, 1);
                }
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            } else {
                // 2. 存在且唯一，开始检查样式
                int startIndex = -1; // 用于记录“参考文献”部分的开始标签的索引
                List<Element> pElements = bodyElement.elements();// 获取body标签下的所有子标签
                for (int i = 0; i < pElements.size(); i++) {
                    Element locationElement = pElements.get(i);
                    if (locationElement.getName().equals("location") && locationElement.attributeValue("id").equals("参考文献")) {
                        startIndex = i;
                    }
                    if (startIndex != -1) {
                        break; // 找到了开始标签，结束循环
                    }
                }
                ArrayList<Element> emptyElements = new ArrayList<>(); // 用于记录没有文本内容的<w:p>标签
                ArrayList<Element> referenceElements = new ArrayList<>(); // 用于记录参考文献的<w:p>标签
                for (int i = startIndex + 1; i < pElements.size(); i++) {
                    Element pElement = pElements.get(i);
                    if (Objects.equals(pElement.getName(), "location")) {
                        break;
                        // 遇到了下一个location标签，结束循环
                    }
                    if (!Objects.equals(pElement.getName(), "p")) {
                        continue; // 不是<w:p>标签，不检查
                    }
                    /* 标题检查 */
                    if (i == startIndex + 1) {
                        detectHeadingStyle(xmlDirectory, pElement, References.contentName, referencesReq.getHeadingRep());
                        continue;
                    }
                    /* 段落内容格式检查和空段检查 */
                    if (isEmptyParagraph(pElement)) {
                        // 没有文本内容的空段落会存入emptyElements，后续统一删除
                        emptyElements.add(pElement);
                        continue;
                    }
                    detectParagraphStyle(xmlDirectory, pElement, References.contentName, referencesReq.getParagraphRep());
                    /* 检测参考文献格式是否正确 */
                    referenceFormatDetect(xmlDirectory, pElement, 1);
                    referenceElements.add(pElement);
                }

                // 删除空段落
                for (Element emptyElement : emptyElements) {
                    emptyElement.detach();
                }

                // 参考文献条数检测
                if (referenceElements.size() < referencesReq.getRecommendedMinCount() && referencesReq.getRecommendedMinCount() != 0) {
                    // 添加建议修改批注
                    int finalRNums = referenceElements.size();
                    addComment(xmlDirectory, pElements.get(startIndex + 1),
                            new ArrayList<String>() {{
                                add("当前论文参考文献只有" + finalRNums + "条，少于了推荐条数：" + referencesReq.getRecommendedMinCount() + "条, 建议增加参考文献数量，提高论文的可信度。");
                            }}, "建议修改批注", "段尾");
                }

                // 全文参考文献引用检测
                /*
                1. 无法定位正文和绪论的位置，无法进行全文参考文献引用检测
                2. 处理有不合规的引用
                    1. 含有非法符号，提醒但不自动修。
                    2. 引用顺序不是从小到大，如[3,2,1]，提醒但不自动修。
                    3. 没有右上角标，提醒但不自动修。
                3. 处理非法引用
                    1. 引用的文献不存在，提醒但不自动修。
                    2. 存在但没有引用，提醒但不自动修。
                    3. 没有按照顺序引用
                */
                if (bodyElement.selectNodes("//location[@id='绪论']").size() != 1) {
                    // 无法识别论文绪论和正文部分的位置，无法进行全文参考文献引用检测
                    addComment(xmlDirectory, pElements.get(startIndex + 1),
                            new ArrayList<String>() {{
                                add("无法识别论文绪论和正文部分的位置，无法进行全文参考文献引用检测。");
                            }}, "待修改批注", "段尾");
                } else {

                    // 进行全文参考文献引用检测
                    // 获取绪论和正文部分的开始标签的索引
                    int MainBodyIndex = -1;
                    for (int i = 0; i < pElements.size(); i++) {
                        Element locationElement = pElements.get(i);
                        if (locationElement.getName().equals("location") && locationElement.attributeValue("id").equals("绪论")) {
                            MainBodyIndex = i;
                        }
                        if (MainBodyIndex != -1) {
                            break; // 找到了开始标签，结束循环
                        }
                    }

                    ArrayList<Quote> quotes = new ArrayList<>(); // 存储所有的引用的位置
                    // 把所有的引用找到并存储
                    for (int i = MainBodyIndex + 1; i < pElements.size(); i++) {
                        Element pElement = pElements.get(i);
                        if (Objects.equals(pElement.getName(), "location")) {
                            break;
                            // 遇到了下一个location标签，结束循环
                        }
                        if (!Objects.equals(pElement.getName(), "p")) {
                            continue; // 不是<w:p>标签，不搜索
                        }
                        if (pElement.selectNodes("descendant::w:r").isEmpty()) {
                            continue; // 跳过空段落
                        }
                        List<Node> rtNodes = pElement.selectNodes("descendant::w:t");
                        StringBuilder text = new StringBuilder();
                        for (Node node : rtNodes) {
                            String rText = node.getText().trim();
                            // 如果是有引用且没有右上角标的文献，那么就是不合规的引用
                            if (rText.contains("[") && rText.contains("]") && !rText.contains("].") && !rText.contains("“]")) {
                                Element rElement = (Element) node.getParent();
                                if (rElement.element("rPr") == null
                                        || rElement.element("rPr").element("vertAlign") == null
                                        || !Objects.equals(rElement.element("rPr").element("vertAlign").attributeValue("val"), "superscript")) {
                                    addComment(xmlDirectory, node.getParent(),
                                            new ArrayList<String>() {{
                                                add("不合规的引用，存在没有右上角标的引用，请修改！");
                                            }}, "待修改批注", "句首");
                                }
                            }
                            text.append(rText);
                        }

                        // 正则表达式匹配
                        System.out.println("正在匹配：" + text.toString());
                        Pattern pattern = Pattern.compile("\\[([\\d,，~、]+)\\]");
                        Matcher matcher = pattern.matcher(text);
                        int j = 1;
                        while (matcher.find()) {
                            quotes.add(
                                    new Quote(i,
                                            j,
                                            matcher.group(1)
                                    ));
                            j++;
                            System.out.println("匹配到引用：" + matcher.group(1));
                        }
                    }

                    // 将匹配到的内容进行处理，同时处理不合规的引用
                    for (Quote quote : quotes) {
                        String quoteStr = quote.getMatchStr();
                        // 如果不是以数字开头和结尾，那么就是不合规的引用
                        if (!quoteStr.matches("^\\d+([,，~]\\d+)*$")) {
                            int times = 1;
                            List<Node> wtElements = pElements.get(quote.getpIndex()).selectNodes("descendant::w:t");
                            for (Node node : wtElements) {
                                if (node.getText().trim().contains("[")) {
                                    if (times == quote.getrOrder()) {
                                        addComment(xmlDirectory, node.getParent(),
                                                new ArrayList<String>() {{
                                                    add("不合规的引用格式，存在非法符号，请修改！");
                                                }}, "待修改批注", "句首");
                                        break;
                                    }
                                    times++;
                                }
                            }
                        } else {
                            // 如果是合规的引用，那么就进行数字的提取
                            String[] nums = quoteStr.split("[,，~]");
                            int[] num = new int[nums.length];
                            for (int i = 0; i < nums.length; i++) {
                                num[i] = Integer.parseInt(nums[i]);
                            }
                            // 如果不是从小到大的顺序，那么就是不合规的引用
                            boolean isOrder = true;
                            for (int i = 0; i < num.length - 1; i++) {
                                if (num[i] > num[i + 1]) {
                                    isOrder = false;
                                    break;
                                }
                            }
                            if (!isOrder) {
                                int times = 1;
                                List<Node> wtElements = pElements.get(quote.getpIndex()).selectNodes("descendant::w:t");
                                for (Node node : wtElements) {
                                    if (node.getText().trim().contains("[")) {
                                        if (times == quote.getrOrder()) {
                                            addComment(xmlDirectory,
                                                    node.getParent(),
                                                    new ArrayList<String>() {{
                                                        add("不合规的引用，同一个引用框，应从小到大进行引用！");
                                                    }}, "待修改批注", "句首");
                                            break;
                                        }
                                        times++;
                                    }
                                }
                            } else {
                                // 如果是从小到大的顺序，那么就是合规的引用，记录该引用所引用的文献有几个，存储到quote的nums中
                                // 注意：如果出现了[1,2,3]这种引用，那么就是3个文献1,2,3;如果出现了[1~3]这种引用，那么也是3个文献1,2,3
                                String[] nums2 = quoteStr.split("[,，]");
                                for (String num2 : nums2) {
                                    if (num2.contains("~")) {
                                        String[] nums3 = num2.split("~");
                                        int start = Integer.parseInt(nums3[0]);
                                        int end = Integer.parseInt(nums3[1]);
                                        for (int k = start; k <= end; k++) {
                                            quote.quoteNums.add(k);
                                        }
                                    } else {
                                        quote.quoteNums.add(Integer.parseInt(num2));
                                    }
                                }
                            }
                        }

                    }
                    // 根据quote的nums，检查是否有无效的引用，例如只有8个参考文献但出现了[9]，或者从未引用过的文献
                    Set<Integer> refSet = new HashSet<>();
                    ArrayList<Integer> refSeq = new ArrayList<>();
                    for (Quote quote : quotes) {
                        for (int num : quote.quoteNums) {
                            if (num > referenceElements.size()) {
                                int times = 1;
                                List<Node> wtElements = pElements.get(quote.getpIndex()).selectNodes("descendant::w:t");
                                for (Node node : wtElements) {
                                    if (node.getText().trim().contains("[")) {
                                        if (times == quote.getrOrder()) {
                                            addComment(xmlDirectory,
                                                    node.getParent(),
                                                    new ArrayList<String>() {{
                                                        add("无效的引用，只有" + referenceElements.size() + "条参考文献，但出现了[" + num + "]。");
                                                    }}, "待修改批注", "句首");

                                            break;
                                        }
                                        times++;
                                    }
                                }
                                break;
                            }
                            refSet.add(num);
                            // 记录引用的顺序，如果已经引用过了，那么就不再记录
                            if (!refSeq.contains(num)) {
                                refSeq.add(num);
                            }

                        } // for (int num : quote.quoteNums)

                    }
                    System.out.println("refSet:" + refSet.toString());
                    System.out.println("refSeq:" + refSeq.toString());

                    for (int i = 1; i <= referenceElements.size(); i++) {
                        if (!refSet.contains(i)) {
                            int finalI = i;
                            addComment(xmlDirectory, referenceElements.get(i - 1),
                                    new ArrayList<String>() {{
                                        add("从未引用过的文献[" + finalI + "]。");
                                    }}, "待修改批注", "整段");
                        }
                    }

                    // 如果文献引用顺序没有从小到大，那么给出建议的顺序更换
                    for (int i = 0; i < refSeq.size() - 1; i++) {
                        if (refSeq.get(i) > refSeq.get(i + 1)) {
                            String recommendSeq = refSeq.toString();
                            addComment(xmlDirectory, pElements.get(startIndex + 1),
                                    new ArrayList<String>() {{
                                        add("正文中对参考文献的引用并未按照从小到大的顺序进行，建议将先引用的文献放在前面，后引用的文献放在后面，即将当前引用顺序调整为" + recommendSeq + "。");
                                    }}, "建议修改批注", "段首");
                        }
                    }

                }


                // 保存document.xml文件
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            }
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 传入代表参考文献的<w:p>标签，检查其内容是否符合规范
     * @param xmlDirectory 解压后的xml文件夹路径
     * @param pElement 代表参考文献的<w:p>标签
     * @param type 参考文献标准的类型，目前只支持1：GB/T 7714-2015
     * */
    private void referenceFormatDetect(String xmlDirectory, Element pElement, int type) {
        if (type == 1) {
            // GB/T 7714-2015
            StringBuilder referenceContent = new StringBuilder();
            for (Node node : pElement.selectNodes("descendant::w:t")) {
                referenceContent.append(node.getText().trim());
            }
            String referenceContentStr = referenceContent.toString().replaceAll(" {2,}", " ");
            // 检测是否有非法的中文字符“，。；：、”等
            if (referenceContentStr.matches(".*[，。；：、].*")) {
                addComment(
                        xmlDirectory,
                        pElement,
                        new ArrayList<String>() {{
                            add("参考文献内容中不应包含中文标点符号[，。；：、]请修改！");
                        }},
                        "待修改批注",
                        "整段");
            } else if (referenceContent.length() > 0 && referenceContentStr.charAt(referenceContent.length() - 1) != '.') {
                addComment(
                        xmlDirectory,
                        pElement,
                        new ArrayList<String>() {{
                            add("参考文献内容应以英文句号结尾，请修改！");
                        }},
                        "待修改批注",
                        "段尾");
            } else {
                String referenceType = ReferencesDetect.getReferenceType(referenceContentStr);
                if (referenceType.equals("未知类型")) {
                    addComment(
                            xmlDirectory,
                            pElement,
                            new ArrayList<String>() {{
                                add("参考文献格式不符合GB/T 7714-2015标准，请修改！");
                            }},
                            "待修改批注",
                            "整段");
                } else {
                    boolean b = isReferenceValid(referenceContentStr);
                    if (!b) {
                        addComment(
                                xmlDirectory,
                                pElement,
                                new ArrayList<String>() {{
                                    add("该条" + referenceType + "格式无法匹配GB/T 7714-2015标准，请修改！");
                                }},
                                "待修改批注",
                                "整段");
                    }
                }
            }

        } else {
            throw new RuntimeException("不支持的参考文献类型！");
        }
    }


    /**
     * TODO:检测论文的绪论和正文部分
     * 这个方法只涉及到标题和段落样式的检测，不涉及图表和公式的检测
     */
    public void detectMainBody(String xmlDirectory, ArrayList<String> sectionOrder) {
        MainBody req = templateInfo.getMainBody();
        Caption captionReq = templateInfo.getCaption();
        int index = sectionOrder.indexOf(MainBody.contentName);

        // 获取标题样式id列表，图注表注样式id
        ArrayList<String> HSList = getHeadingStyleIdList(xmlDirectory);
        String captionStyleId = getCaptionStyleId(xmlDirectory);
        // 开始检测
        // 1. 处理非正常情况，即“绪论和正文”部分内容缺失或冗余
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element rootElement = document.getRootElement();
            List<Node> locationNodes = rootElement.selectNodes("//location[@id='绪论']");
            Element bodyElement = rootElement.element("body");

            if (!(locationNodes.size() == 1)) {
                if (locationNodes.isEmpty()) {
                    addCommentForLackContent(xmlDirectory, bodyElement.element("p"), MainBody.markWord, sectionOrder, index);
                } else if (locationNodes.size() > 1) {
                    addCommentForRepeatContent(xmlDirectory, bodyElement, MainBody.contentName, "start", MainBody.markWord, 1);
                }
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            } else {
                // 2. 存在且唯一，开始检查样式
                int startIndex = -1; // 用于记录“绪论和正文”部分的开始标签的索引
                List<Element> pElements = bodyElement.elements();// 获取body标签下的所有子标签
                for (int i = 0; i < pElements.size(); i++) {
                    Element locationElement = pElements.get(i);
                    if (locationElement.getName().equals("location") && locationElement.attributeValue("id").equals("绪论")) {
                        startIndex = i;
                    }
                    if (startIndex != -1) {
                        break; // 找到了开始标签，结束循环
                    }
                }
                ArrayList<Element> emptyElements = new ArrayList<>(); // 用于记录没有文本内容且不内嵌图片的<w:p>标签
                int picTblCount = 0; // 用于记录图片和表格的数量
                for (int i = startIndex + 1; i < pElements.size(); i++) {
                    Element pElement = pElements.get(i);
                    if (Objects.equals(pElement.getName(), "location")) {
                        break;
                        // 遇到了下一个location标签，结束循环
                    }
                    if (isEmptyParagraph(pElement)) {
                        // 没有文本内容的空段落会存入emptyElements，后续统一删除
                        emptyElements.add(pElement);
                        continue;
                    }
                    if (isTable(pElement)) {
                        picTblCount++;
                        System.out.println("检测到表格");
                        // 是表格，检查上方是否有表注
                        boolean hasCaption = false;
                        int type = 0;
                        int j = i;
                        while (j > startIndex + 1) {
                            j--;
                            Element pElement2 = pElements.get(j);
                            if (isEmptyParagraph(pElement2)) {
                                continue;
                            }
                            if (isCaption(pElement2, captionStyleId)) {
                                type = getCaptionType(pElement2);
                                hasCaption = true;
                            }
                            break;
                        }
                        if (!hasCaption) {
                            addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                add("表格应当有表注，该表格上方没有表注，请检查！");
                            }}, "待修改批注", "段首");
                        } else {
                            if (type != 2) {
                                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                    add("表格上方的表注疑似为图注或其他不明格式，请检查！");
                                }}, "待修改批注", "段首");
                            }
                        }
                        continue;
                    }
                    if (isPicture(pElement)) {
                        picTblCount++;
                        System.out.println("检测到图片");
                        // 是图片，检查下方是否有图注
                        boolean hasCaption = false;
                        int type = 0;
                        int j = i;
                        while (j < pElements.size() - 1) {
                            j++;
                            Element pElement2 = pElements.get(j);
                            if (isEmptyParagraph(pElement2)) {
                                continue;
                            }
                            if (Objects.equals(pElement2.getName(), "location")) {
                                break;
                            }
                            if (isCaption(pElement2, captionStyleId)) {
                                type = getCaptionType(pElement2);
                                hasCaption = true;
                            }
                            break;
                        }
                        if (!hasCaption) {
                            addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                add("图片应当有图注，该图片下方没有图注，请检查！");
                            }}, "待修改批注", "段首");
                        } else {
                            if (type != 1) {
                                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                    add("图片下方的图注疑似为表注或其他不明格式，请检查！");
                                }}, "待修改批注", "段首");
                            }
                        }

                        // 图片检查：检查图片大小是否合适，锁定a:ext属性
                        XPath xPath = DocumentHelper.createXPath("descendant::pic:pic");
                        XPath xPathChart = DocumentHelper.createXPath("descendant::wp:extent");
                        XPath xPathShape = DocumentHelper.createXPath("descendant::v:shape");

                        Map nsMap = new HashMap<String, String>();
                        nsMap.put("a", "http://schemas.openxmlformats.org/drawingml/2006/main");
                        nsMap.put("pic", "http://schemas.openxmlformats.org/drawingml/2006/picture");
                        nsMap.put("uri", "http://schemas.openxmlformats.org/drawingml/2006/picture");
                        nsMap.put("wp", "http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing");
                        nsMap.put("v", "urn:schemas-microsoft-com:vml");


                        xPath.setNamespaceURIs(nsMap);
                        xPathChart.setNamespaceURIs(nsMap);
                        Node node = xPath.selectSingleNode(pElement);
                        if (node == null) {
                            System.out.println("没有找到控制长度的标签，尝试使用wp:extent"); // chart图表控制长度的标签
                            node = xPathChart.selectSingleNode(pElement);
                            if (node == null) {
                                System.out.println("没有找到控制长度的标签，尝试使用v:shape");
                                node = xPathShape.selectSingleNode(pElement);
                                if (node == null) {
                                    System.out.println("没有找到控制长度的标签，跳过图片长度检查");
                                    continue; // 没有找到控制长度的标签，跳过图片长度检查
                                }
                            }
                        }
                        System.out.println("找到了控制长度的标签：" + node.getName());
                        Element extElement = (Element) node;
                        double cx = 0;
                        double cy = 0;
                        if (!Objects.equals(extElement.getName(), "shape")) {
                            if (Objects.equals(extElement.getName(), "pic")) {
                                extElement = extElement.element("spPr").element("xfrm").element("ext");
                            }
                            String cxStr = extElement.attributeValue("cx");
                            String cyStr = extElement.attributeValue("cy");
                            System.out.println("cxStr:" + cxStr);
                            System.out.println("cyStr:" + cyStr);
                            cx = Double.parseDouble(cxStr);
                            cy = Double.parseDouble(cyStr);

                            cx = cx / 360000;
                            cy = cy / 360000;
                            System.out.println("cx:" + cx + "cm");
                            System.out.println("cy:" + cy + "cm");
                        } else {
                            // v:shape标签控制图片大小
                            String style = extElement.attributeValue("style");
                            Pattern patternWidth = Pattern.compile("width:([\\d.]+)pt");
                            Pattern patternHeight = Pattern.compile("height:([\\d.]+)pt");
                            Matcher matcherWidth = patternWidth.matcher(style);
                            Matcher matcherHeight = patternHeight.matcher(style);
                            if (matcherWidth.find() && matcherHeight.find()) {
                                cx = Double.parseDouble(matcherWidth.group(1)) * 0.0353;
                                cy = Double.parseDouble(matcherHeight.group(1)) * 0.0353;
                            }
                        }


                        // 根据captionReq的要求进行大小检查
                        if (cx > captionReq.getPicSzWidthMax() || cy > captionReq.getPicSzHeightMax()) {
                            addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                add("图片大小超出了规定的范围，建议图片宽度不超过" + captionReq.getPicSzWidthMax() + "cm，高度不超过" + captionReq.getPicSzHeightMax() + "cm。");
                            }}, "建议修改批注", "整段");
                        }

                        if (cx < captionReq.getPicSzWidthMin() || cy < captionReq.getPicSzHeightMin()) {
                            addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                add("图片大小小于规定的范围，建议图片宽度不小于" + captionReq.getPicSzWidthMin() + "cm，高度不小于" + captionReq.getPicSzHeightMin() + "cm，以保证图片的清晰度。");
                            }}, "建议修改批注", "整段");
                        }

                        continue;
                    }

                    /* 标题和段落检查，包括图注表注检查 */
                    int j = isHeading(pElement, HSList);
                    if (j == 0) {
                        if (isCaption(pElement, captionStyleId)) {
                            int captionType = getCaptionType(pElement);
                            if (captionType == 1) { // 图注
                                // 检查该图注上方是否有图
                                boolean hasPicture = false;
                                int k = i;
                                while (k > startIndex + 1) {
                                    k--;
                                    Element pElement2 = pElements.get(k);
                                    if (isEmptyParagraph(pElement2)) {
                                        continue;
                                    }
                                    if (isPicture(pElement2)) {
                                        hasPicture = true;
                                    }
                                    break;
                                }
                                if (!hasPicture) {
                                    addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                        add("图注应当放置于对应图片的下方，该图注上方没有图，请检查！");
                                    }}, "待修改批注", "整段");
                                }
                                StandardizeCaption(pElement, captionReq);
                            } else if (captionType == 2) { // 表注
                                // 检查该表注下方是否有表
                                boolean hasTable = false;
                                int k = i;
                                while (k < pElements.size() - 1) {
                                    k++;
                                    Element pElement2 = pElements.get(k);
                                    if (isEmptyParagraph(pElement2)) {
                                        continue;
                                    }
                                    if (Objects.equals(pElement2.getName(), "location")) {
                                        break;
                                    }
                                    if (isTable(pElement2)) {
                                        hasTable = true;
                                    }
                                    break;
                                }
                                if (!hasTable) {
                                    addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                        add("表注应当放置于对应表的下方，该表注上方没有表，请检查！");
                                    }}, "待修改批注", "整段");
                                }
                                StandardizeCaption(pElement, captionReq);
                            } else if (captionType == 0) { // 意义不明的图注表注
                                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                    add("意义不明的说明文字，若是正文段落，请清除文字格式，若是图注表注请指明类型！");
                                }}, "待修改批注", "段尾");

                            }

                        } else {
                            detectParagraphStyle(xmlDirectory, pElement, MainBody.contentName, req.getParagraphRep());
                        }
                    } else {
                        // 删除存在的编号设置，即<w:numPr>标签
                        Element pPrElement = pElement.element("pPr");
                        Element numPrElement = pPrElement.element("numPr");
                        if (numPrElement != null) {
                            pPrElement.remove(numPrElement);
                        }
                        // 检查标题是否有手打前缀编号
                        List<Node> wtElements = pElement.selectNodes("descendant::w:t");
                        StringBuilder text = new StringBuilder();
                        for (Node wtElement: wtElements){
                            text.append(wtElement.getText());
                        }
                        String Str = text.toString();
                        System.out.println("--------------");
                        System.out.println("Str = " + Str);
                        System.out.println("--------------");

                        if (!Str.isEmpty()) {
                            if (Str.matches("\\d+[.、].*")) {
                                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                    add("标题不应有手打前缀编号，请删除！");
                                }}, "待修改批注", "段首");
                            }else if (Str.matches("[一二三四五六七八九十]+[.、].*")) {
                                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                    add("标题不应有手打汉字前缀编号，请删除！");
                                }}, "待修改批注", "段首");
                            }else if(Str.matches("\\([一二三四五六七八九十]\\).*")){
                                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                    add("标题不应有手打汉字前缀编号，请删除！");
                                }}, "待修改批注", "段首");
                            }else if(Str.matches("（[一二三四五六七八九十]）.*")) {
                                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                    add("标题不应有手打汉字前缀编号，请删除！");
                                }}, "待修改批注", "段首");
                            }else if(Str.matches("（\\d+）.*")) {
                                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                                    add("标题不应有手打前缀编号，请删除！");
                                }}, "待修改批注", "段首");
                            }
                        }


                        // 检查标题样式是否正确
                        if (j == 1) {
                            detectHeadingStyle(xmlDirectory, pElement, MainBody.contentName, req.getHeadingRepLevel1());
                        } else if (j == 2) {
                            detectHeadingStyle(xmlDirectory, pElement, MainBody.contentName, req.getHeadingRepLevel2());
                        } else if (j == 3) {
                            detectHeadingStyle(xmlDirectory, pElement, MainBody.contentName, req.getHeadingRepLevel3());
                        }
                    }

                }
                // 删除空段落
                for (Element emptyElement : emptyElements) {
                    emptyElement.detach();
                }

                // 图片和表格数量检查
                if (picTblCount <= captionReq.getRecommendNum()) {
                    int finalPicTblCount = picTblCount;
                    addComment(xmlDirectory,
                            rootElement.element("body"),
                            new ArrayList<String>() {{
                                add("论文全篇的图片和表格数量为" + finalPicTblCount + "，为了提高论文的可读性，建议论文的图片和表格数量不少于" + captionReq.getRecommendNum() + "。");
                            }}, "建议修改批注", "文末");
                }

                // 保存document.xml文件
                OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
                format.setEncoding("UTF-8");
                XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
                xmlwriter.write(document);
                xmlwriter.close();
            }
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查段落样式是否正确，不正确的则修改并添加批注
     * 与detectHeadingStyle类似，同样是对英文样式的检查不会添加批注，直接修改
     * 对于传入的段落，只会检查含有w:t的，没有文本内容的不会检查
     * @param xmlDirectory 解压后的xml文件夹路径
     * @param pElement 待检查的<w:p>标签
     * @param sectionName 本部分的名称
     * @param paragraphRep 段落样式要求
     */
    private void detectParagraphStyle(String xmlDirectory, Element pElement, String sectionName, ParagraphRep
            paragraphRep) {
        List<Element> rElements = pElement.elements("r");
        ArrayList<Integer> wrongRIndex = new ArrayList<>(); // 用于记录不符合要求的<w:r>标签的索引
        ArrayList<ArrayList<String>> commentListList = new ArrayList<>(); // 用于记录批注内容
        ArrayList<String> commentTypeList = new ArrayList<>(); // 用于记录批注类型
        for (int i = 0; i < rElements.size(); i++) {
            Element rElement = rElements.get(i); // w:r
            Element rPrElement = rElement.element("rPr"); // 正常来说一个<w:r>只有一个<w:rPr> 或者没有
            if (rPrElement != null) {
                // 检查字体样式是否正确
                Element rFontsElement = rPrElement.element("rFonts");
                if (rFontsElement == null) { // 没有<w:rFonts>标签，添加标准的<w:rFonts>标签
                    rPrElement.addElement("w:rFonts").addAttribute("w:ascii", paragraphRep.getFontEnglishType()).addAttribute("w:hAnsi", paragraphRep.getFontEnglishType()).addAttribute("w:eastAsia", paragraphRep.getFontType()).addAttribute("w:hint", "eastAsia");
                } else {
                    String fontType = rFontsElement.attributeValue("eastAsia");
                    if (fontType == null) rFontsElement.addAttribute("w:eastAsia", paragraphRep.getFontType());
                    else if (!fontType.equals(paragraphRep.getFontType())) {
                        String wrongFontType = rFontsElement.attributeValue("eastAsia");
                        rFontsElement.attribute("eastAsia").setValue(paragraphRep.getFontType());
                        // 发现了不符合要求的字体样式，添加批注
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("此处段落“" + sectionName + "”的字体样式不符合要求，应为" + paragraphRep.getFontType() + "，但实际为" + wrongFontType + "。已自动修改。");
                        commentListList.add(contentList);
                        commentTypeList.add("自动修改批注");
                        wrongRIndex.add(i); // 记录不符合要求的<w:r>标签的索引
                    }
                    // 其他样式直接修改
                    if (rFontsElement.attribute("ascii") == null) {
                        rFontsElement.addAttribute("w:ascii", paragraphRep.getFontEnglishType());
                    } else {
                        rFontsElement.attribute("ascii").setValue(paragraphRep.getFontEnglishType());
                    }

                    if (rFontsElement.attribute("hAnsi") == null) {
                        rFontsElement.addAttribute("w:hAnsi", paragraphRep.getFontEnglishType());
                    } else {
                        rFontsElement.attribute("hAnsi").setValue(paragraphRep.getFontEnglishType());
                    }

                    if (rFontsElement.attribute("hint") == null) {
                        rFontsElement.addAttribute("w:hint", "eastAsia");
                    } else {
                        rFontsElement.attribute("hint").setValue("eastAsia");

                    }

                }

                // 检查字体大小是否正确
                Element szElement = rPrElement.element("sz");
                if (szElement == null) { // 没有<w:sz>标签，添加标准的<w:sz>标签
                    rPrElement.addElement("w:sz").addAttribute("w:val", paragraphRep.getFontSize());
                } else {
                    String fontSize = szElement.attributeValue("val");
                    if (fontSize == null) {
                        szElement.addAttribute("w:val", paragraphRep.getFontSize());
                    } else if (!fontSize.equals(paragraphRep.getFontSize())) {
                        String wrongFontSize = szElement.attributeValue("val");
                        szElement.attribute("val").setValue(paragraphRep.getFontSize());
                        // 发现了不符合要求的字体大小，添加批注
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("此处段落“" + sectionName + "”的字体大小不符合要求，应为" + StrConverter.fontSizeToReadableString(paragraphRep.getFontSize()) + "，但实际为" + StrConverter.fontSizeToReadableString(wrongFontSize) + "。已自动修改。");
                        commentListList.add(contentList);
                        commentTypeList.add("自动修改批注");
                        wrongRIndex.add(i);
                    }

                    // 检查是否有<w:szCs>标签，没有则添加
                    Element szCsElement = rPrElement.element("szCs");
                    if (szCsElement == null) {
                        rPrElement.addElement("w:szCs").addAttribute("w:val", paragraphRep.getFontSize());
                    } else {
                        if (szCsElement.attributeValue("val") == null) {
                            szCsElement.addAttribute("w:val", paragraphRep.getFontSize());
                        } else {
                            szCsElement.attribute("val").setValue(paragraphRep.getFontSize());
                        }
                    }
                }

                // 检查是否有<w:b>标签，没有不管，有则检测
                Element bElement = rPrElement.element("b");
                if (bElement == null) {
                    if (paragraphRep.getBold() == 1) rPrElement.addElement("w:b");
                    if (paragraphRep.getBold() == 0) rPrElement.addElement("w:b").addAttribute("w:val", "0");
                } else {
                    if (paragraphRep.getBold() == 0 && !Objects.equals(bElement.attributeValue("val"), "0")) {
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("此处段落“" + sectionName + "”字体不应该加粗，已自动修改。");
                        commentListList.add(contentList);
                        commentTypeList.add("自动修改批注");
                        wrongRIndex.add(i); // 记录不符合要求的<w:r>标签的索引
                        rPrElement.remove(bElement);
                    } else if (paragraphRep.getBold() == 1 && Objects.equals(bElement.attributeValue("val"), "0")) {
                        // 该加粗的没加粗
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("此处标题“" + sectionName + "”的字体样式不符合要求，应为加粗，但实际为不加粗。已自动修改。");
                        commentListList.add(contentList);
                        commentTypeList.add("自动修改批注");
                        wrongRIndex.add(i);
                        bElement.remove(bElement.attribute("val")); // 删除<w:b>标签的val属性
                    }
                }

                // 检查<w:i>标签，没有就不管，有则检测
                Element iElement = rPrElement.element("i");
                Element iCsElement = rPrElement.element("iCs");
                if(!(iElement == null)){
                    ArrayList<String> contentList = new ArrayList<>();
                    contentList.add("此处段落“" + sectionName + "”的字体不应斜体，已自动修改。");
                    commentListList.add(contentList);
                    commentTypeList.add("自动修改批注");
                    wrongRIndex.add(i);
                    rPrElement.remove(iElement);
                }
                if(!(iCsElement == null)){
                    rPrElement.remove(iCsElement);
                }

            } else {
                // 没有<w:rPr>标签，添加标准的<w:rPr>标签
                ElementCreator.addStdParagraphRPr(rElement, paragraphRep);
            }
        }
        /* 次优先级：<w:pPr>，关于这个的批注直接在段首添加，不需要加范围 */
        Element pPrElement = pElement.element("pPr");
        if (pPrElement == null) {
            // 没有<w:pPr>标签，添加标准的<w:pPr>标签，要添加到<w:p>标签的首个位置
            pPrElement = DocumentHelper.createElement("w:pPr");
            pElement.elements().add(0, pPrElement);
//            pPrElement = pElement.addElement("w:pPr");
            /*
             <w:ind w:firstLine="" w:firstLineChars="" />
             <w:spacing w:line="" w:lineRule="" /> 若有其他属性则删除其他属性
             */
        }
        // 检查spacing
        Element spacingElement = pPrElement.element("spacing");
        if (spacingElement == null) {
            spacingElement = pPrElement.addElement("w:spacing");
            spacingElement.addAttribute("w:line", paragraphRep.getLine());
            spacingElement.addAttribute("w:lineRule", "auto");
        } else {
            // 检查line
            if (spacingElement.attributeValue("line") == null) {
                spacingElement.addAttribute("w:line", paragraphRep.getLine());
            } else if (!spacingElement.attributeValue("line").equals(paragraphRep.getLine())) {
                String wrongLine = spacingElement.attributeValue("line");
                spacingElement.attribute("line").setValue(paragraphRep.getLine());
                // 发现了不符合要求的行距，添加批注
                ArrayList<String> contentList = new ArrayList<>();
                contentList.add("此处段落“" + sectionName + "”的行距不符合要求，应为" + StrConverter.lineSpacingToReadableString(paragraphRep.getLine()) + "，但实际为" + StrConverter.lineSpacingToReadableString(wrongLine) + "。已自动修改。");
                String commentType = "自动修改批注";
                addComment(xmlDirectory, pElement, contentList, commentType, "段尾");
            }
            // 检查lineRule, afterLines, beforeLines, after, before
            if (spacingElement.attributeValue("lineRule") == null) {
                spacingElement.addAttribute("w:lineRule", "auto");
            } else {
                spacingElement.attribute("lineRule").setValue("auto");
            }

            if (spacingElement.attributeValue("afterLines") != null)
                spacingElement.remove(spacingElement.attribute("afterLines"));
            if (spacingElement.attributeValue("beforeLines") != null)
                spacingElement.remove(spacingElement.attribute("beforeLines"));
            if (spacingElement.attributeValue("after") != null)
                spacingElement.remove(spacingElement.attribute("after"));
            if (spacingElement.attributeValue("before") != null)
                spacingElement.remove(spacingElement.attribute("before"));
        }
        // 检查ind
        Element indElement = pPrElement.element("ind");
        if (indElement == null) {
            indElement = pPrElement.addElement("w:ind");
            indElement.addAttribute("w:firstLineChars", paragraphRep.getIndent());
        } else {
            if (indElement.attributeValue("firstLineChars") == null) {
                indElement.addAttribute("w:firstLineChars", paragraphRep.getIndent());
            } else if (!indElement.attributeValue("firstLineChars").equals(paragraphRep.getIndent())) {
                String wrongIndent = indElement.attributeValue("firstLineChars");
                indElement.attribute("firstLineChars").setValue(paragraphRep.getIndent());
                // 发现了不符合要求的缩进，添加批注
                ArrayList<String> contentList = new ArrayList<>();
                contentList.add("此处段落“" + sectionName + "”的缩进不符合要求，应为" + StrConverter.indentToReadableString(paragraphRep.getIndent()) + "，但实际为" + StrConverter.indentToReadableString(wrongIndent) + "。已自动修改。");
                String commentType = "自动修改批注";
                addComment(xmlDirectory, pElement, contentList, commentType, "段首");
            }

            // 清楚其他的缩进属性left,right
            if (indElement.attributeValue("left") != null)
                indElement.remove(indElement.attribute("left"));
            if (indElement.attributeValue("right") != null)
                indElement.remove(indElement.attribute("right"));
        }

        // 其他格式检查
        Element jcElement = pPrElement.element("jc");
        if (jcElement == null) {
            pPrElement.addElement("w:jc").addAttribute("w:val", "both");
        } else {
            if (jcElement.attributeValue("val") == null) {
                jcElement.addAttribute("w:val", "both");
            } else {
                jcElement.attribute("val").setValue("both");
            }
        }
        // 删除该<w:p>的首个<w:tab>标签，即段前不要有tab
        Element firstRun = pElement.element("r");
        if (firstRun != null) {
            Element firstTab = firstRun.element("tab");
            if (firstTab != null) {
                firstRun.remove(firstTab);
                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                    add("此处“" + sectionName + "”的段前不应该有tab缩进,而应该是字符缩进，已自动调整。");
                }}, "自动修改批注", "段尾");

            }
        }
        /* 最后添加批注（范围批注） */
        // 避免索引混乱，从后往前添加批注
        for (int i = wrongRIndex.size() - 1; i >= 0; i--) {
            int index = wrongRIndex.get(i);
            ArrayList<String> contentList = commentListList.get(i);
            String commentType = commentTypeList.get(i);
            addComment(xmlDirectory, pElement, contentList, commentType, "指定范围", index);
        }


    }

    /**
     * 检查标题样式是否正确，不正确的则修改并添加批注
     * 补充：对于英文的样式要求，考虑到用得少，程序在发现到不符合时会直接修改成符合要求的样式，但不会添加批注。
     * 对于英文出现明显错误才会添加批注，否则直接修改。 TODO:
     * 之后看情况是否改进 TODO: 内容的检测也主要以w:rPr为主
     *      字体格式全部在w:rPr解决：
     *          w:rFonts(fontType,fontEnglishType),
     *          w:sz(fontSize),w:szCs
     *          同时检测 b -> 没有就直接添加标准的
     *      在w:pPr解决
     *          spacing
     *          同时检测ind, jc -> 没有就直接添加标准的
     *      style.xml关联样式解决 TODO: 有时间再说
     *          jc,b,ind
     * @param xmlDirectory 解压后的xml文件夹路径
     * @param pElement 待检查的<w:p>标签
     * @param sectionName 本部分的名称
     * @param headingStyle 标题样式要求
     * */
    public void detectHeadingStyle(String xmlDirectory, Element pElement, String sectionName, HeadingRep
            headingStyle) throws IOException {
        // 检查之前，先记录该标题所有的文本内容，用于后面的目录检测
        StringBuilder sb = new StringBuilder();
        XPath xPath = DocumentHelper.createXPath("descendant::w:t");
        xPath.setNamespaceURIs(new HashMap<String, String>() {{
            put("w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        }});
        List<Node> wtNodes = xPath.selectNodes(pElement);
        for (Node wtNode : wtNodes) {
            sb.append(wtNode.getText());
        }
        outlineList.add(sb.toString().replaceAll(" ", "")); // 去除空格后添加到目录列表中


        /* 优先级最高：含有<w:t>的<w:r>的<w:rPr> */
        List<Element> rElements = pElement.elements("r"); // 只有<w:r>
        ArrayList<Integer> wrongRIndex = new ArrayList<>(); // 用于记录不符合要求的<w:r>标签的索引
        ArrayList<ArrayList<String>> commentListList = new ArrayList<>(); // 用于记录批注内容
        ArrayList<String> commentTypeList = new ArrayList<>(); // 用于记录批注类型

        for (int i = 0; i < rElements.size(); i++) {
            Element rElement = rElements.get(i); // w:r
            Element rPrElement = rElement.element("rPr"); // 正常来说只有一个<w:rPr> 或者没有
            if (rPrElement != null) {
                // 检查字体样式是否正确
                Element rFontsElement = rPrElement.element("rFonts");
                if (rFontsElement == null) { // 没有<w:rFonts>标签，添加标准的<w:rFonts>标签
                    rPrElement.addElement("w:rFonts").addAttribute("w:ascii", headingStyle.getFontType()).addAttribute("w:hAnsi", headingStyle.getFontType()).addAttribute("w:hint", "eastAsia").addAttribute("w:eastAsia", headingStyle.getFontType());
                } else {
                    String fontType = rFontsElement.attributeValue("eastAsia");
                    if (fontType == null) rFontsElement.addAttribute("w:eastAsia", headingStyle.getFontType());
                    else if (!fontType.equals(headingStyle.getFontType())) {
                        String wrongFontType = rFontsElement.attributeValue("eastAsia");
                        rFontsElement.attribute("eastAsia").setValue(headingStyle.getFontType()); // 自动修改字体样式
                        // 发现了不符合要求的字体样式，添加批注
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("此处标题“" + sectionName + "”的字体样式不符合要求，应为" + headingStyle.getFontType() + "，但实际为" + wrongFontType + "。已自动修改。");
                        commentListList.add(contentList);
                        commentTypeList.add("自动修改批注");
                        wrongRIndex.add(i); // 记录不符合要求的<w:r>标签的索引
                    }
                    // 其他样式直接修改
                    if (rFontsElement.attributeValue("ascii") == null) {
                        rFontsElement.addAttribute("w:ascii", headingStyle.getFontEnglishType());
                    } else {
                        rFontsElement.attribute("ascii").setValue(headingStyle.getFontEnglishType());
                    }

                    if (rFontsElement.attributeValue("hAnsi") == null) {
                        rFontsElement.addAttribute("w:hAnsi", headingStyle.getFontEnglishType());
                    } else {
                        rFontsElement.attribute("hAnsi").setValue(headingStyle.getFontEnglishType());
                    }

                    if (rFontsElement.attributeValue("hint") == null) {
                        rFontsElement.addAttribute("w:hint", "eastAsia");
                    } else {
                        rFontsElement.attribute("hint").setValue("eastAsia");
                    }

                }

                // 检查字体大小是否正确
                Element szElement = rPrElement.element("sz");
                if (szElement == null) { // 没有<w:sz>标签，添加标准的<w:sz>标签
                    rPrElement.addElement("w:sz").addAttribute("w:val", headingStyle.getFontSize());
                } else {
                    String fontSize = szElement.attributeValue("val");
                    if (fontSize == null) {
                        szElement.addAttribute("w:val", headingStyle.getFontSize());
                    } else if (!fontSize.equals(headingStyle.getFontSize())) {
                        String wrongFontSize = szElement.attributeValue("val");
                        szElement.attribute("val").setValue(headingStyle.getFontSize()); // 自动修改字体大小
                        // 发现了不符合要求的字体大小，添加批注
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("此处标题“" + sectionName + "”的字体大小不符合要求，应为" + StrConverter.fontSizeToReadableString(headingStyle.getFontSize()) + "，但实际为" + StrConverter.fontSizeToReadableString(wrongFontSize) + "。已自动修改。");
                        commentListList.add(contentList);
                        commentTypeList.add("自动修改批注");
                        wrongRIndex.add(i);
                    }

                    // 检查是否有<w:szCs>标签，没有则添加，直接修改为标准的<w:szCs>标签
                    Element szCsElement = rPrElement.element("szCs");
                    if (szCsElement == null) { // 没有<w:szCs>标签，添加标准的<w:szCs>标签
                        rPrElement.addElement("w:szCs").addAttribute("w:val", headingStyle.getFontSize());
                    } else {
                        if (szCsElement.attributeValue("val") == null) {
                            szCsElement.addAttribute("w:val", headingStyle.getFontSize());
                        } else {
                            szCsElement.attribute("val").setValue(headingStyle.getFontSize());
                        }
                    }
                }

                // 检查<w:b>标签，没有就不管，有则检测
                Element bElement = rPrElement.element("b");
                if (bElement == null) {
                    if (headingStyle.getBold() == 1) rPrElement.addElement("w:b");
                    if (headingStyle.getBold() == 0) rPrElement.addElement("w:b").addAttribute("w:val", "0");
                } else {
                    if (headingStyle.getBold() == 0 && !Objects.equals(bElement.attributeValue("val"), "0")) {
                        // 不该加粗的加粗了
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("此处标题“" + sectionName + "”的字体样式不符合要求，应为不加粗，但实际为加粗。已自动修改。");
                        commentListList.add(contentList);
                        commentTypeList.add("自动修改批注");
                        wrongRIndex.add(i);
                        rPrElement.remove(bElement); // 删除<w:b>标签
                    } else if (headingStyle.getBold() == 1 && Objects.equals(bElement.attributeValue("val"), "0")) {
                        // 该加粗的没加粗
                        ArrayList<String> contentList = new ArrayList<>();
                        contentList.add("此处标题“" + sectionName + "”的字体样式不符合要求，应为加粗，但实际为不加粗。已自动修改。");
                        commentListList.add(contentList);
                        commentTypeList.add("自动修改批注");
                        wrongRIndex.add(i);
                        bElement.remove(bElement.attribute("val")); // 删除<w:b>标签的val属性
                    }
                }

                // 检查<w:i>标签，没有就不管，有则检测
                Element iElement = rPrElement.element("i");
                Element iCsElement = rPrElement.element("iCs");
                if(!(iElement == null)){
                    ArrayList<String> contentList = new ArrayList<>();
                    contentList.add("此处标题“" + sectionName + "”的字体不应斜体，已自动修改。");
                    commentListList.add(contentList);
                    commentTypeList.add("自动修改批注");
                    wrongRIndex.add(i);
                    rPrElement.remove(iElement);
                }
                if(!(iCsElement == null)){
                    rPrElement.remove(iCsElement);
                }
            } else {
                // 没有<w:rPr>标签，添加标准的<w:rPr>标签
                ElementCreator.addStdHeadingRPr(pElement, headingStyle);
            }
        }

        /* 次优先级：<w:pPr>，关于这个的批注直接在段首添加，不需要加范围 */
        Element pPrElement = pElement.element("pPr"); // 正常来说会有一个<w:pPr>，没有的话就添加(一般不会没有)
        if (pPrElement == null) {
            // 没有<w:pPr>标签，添加标准的<w:pPr>标签，要添加到<w:p>标签的首个位置
            pPrElement = DocumentHelper.createElement("w:pPr");
            pElement.elements().add(0, pPrElement);
//            pPrElement = pElement.addElement("w:pPr");
            /*
                <w:ind w:firstLine="" w:firstLineChars="" />
                <w:spacing w:beforeLines="" w:afterLiness="" w:line="" w:lineRule="auto" />
                <w:jc w:val="center" />  居中， left,right,both两端
            */
        }
        // 检查spacing
        Element spacingElement = pPrElement.element("spacing");
        if (spacingElement == null) {
            spacingElement = pPrElement.addElement("w:spacing");
            spacingElement.addAttribute("w:lineRule", "auto");
            spacingElement.addAttribute("w:line", headingStyle.getLine());
            spacingElement.addAttribute("w:afterLines", headingStyle.getAfterLine());
            spacingElement.addAttribute("w:beforeLines", headingStyle.getBeforeLine());
        } else {
            // 检查line
            if (spacingElement.attributeValue("line") == null) {
                spacingElement.addAttribute("w:line", headingStyle.getLine());
            } else if (!spacingElement.attributeValue("line").equals(headingStyle.getLine())) {
                String wrongLine = spacingElement.attributeValue("line");
                spacingElement.attribute("line").setValue(headingStyle.getLine()); // 自动修改行距
                // 发现了不符合要求的行距，添加批注
                ArrayList<String> contentList = new ArrayList<>();
                contentList.add("此处标题“" + sectionName + "”的行距不符合要求，应为" + StrConverter.lineSpacingToReadableString(headingStyle.getLine()) + "，但实际为" + StrConverter.lineSpacingToReadableString(wrongLine) + "。已自动修改。");
                String commentType = "自动修改批注";
                addComment(xmlDirectory, pElement, contentList, commentType, "段尾");
            }
            // 检查行距规则lineRule，这个直接修改成auto
            if (spacingElement.attributeValue("lineRule") == null) {
                spacingElement.addAttribute("w:lineRule", "auto");
            } else {
                spacingElement.attribute("lineRule").setValue("auto");
            }
            // 检查段前行距beforeLines
            if (spacingElement.attributeValue("beforeLines") == null) {
                spacingElement.addAttribute("w:beforeLines", headingStyle.getBeforeLine());
            } else if (!spacingElement.attributeValue("beforeLines").equals(headingStyle.getBeforeLine())) {
                String wrongBeforeLine = spacingElement.attributeValue("beforeLines");
                spacingElement.attribute("beforeLines").setValue(headingStyle.getBeforeLine()); // 自动修改段前行距
                // 发现了不符合要求的段前行距，添加批注
                ArrayList<String> contentList = new ArrayList<>();
                contentList.add("此处标题“" + sectionName + "”的段前行距不符合要求，应为" + StrConverter.beforeAfterLineToReadableString(headingStyle.getBeforeLine()) + "，但实际为" + StrConverter.beforeAfterLineToReadableString(wrongBeforeLine) + "。已自动修改。");
                String commentType = "自动修改批注";
                addComment(xmlDirectory, pElement, contentList, commentType, "段尾");
            }
            // 检查段后行距afterLines
            if (spacingElement.attributeValue("afterLines") == null) {
                spacingElement.addAttribute("w:afterLines", headingStyle.getAfterLine());
            } else if (!spacingElement.attributeValue("afterLines").equals(headingStyle.getAfterLine())) {
                String wrongAfterLine = spacingElement.attributeValue("afterLines");
                spacingElement.attribute("afterLines").setValue(headingStyle.getAfterLine()); // 自动修改段后行距
                // 发现了不符合要求的段后行距，添加批注
                ArrayList<String> contentList = new ArrayList<>();
                contentList.add("此处标题“" + sectionName + "”的段后行距不符合要求，应为" + StrConverter.beforeAfterLineToReadableString(headingStyle.getAfterLine()) + "，但实际为" + StrConverter.fontSizeToReadableString(wrongAfterLine) + "。已自动修改。");
                String commentType = "自动修改批注";
                addComment(xmlDirectory, pElement, contentList, commentType, "段尾");
            }

            // 删除存在的after属性
            if (spacingElement.attributeValue("after") != null)
                spacingElement.remove(spacingElement.attribute("after"));

        }
        // 检查jc
        Element jcElement = pPrElement.element("jc");
        if (jcElement == null) {
            jcElement = pPrElement.addElement("w:jc");
            jcElement.addAttribute("w:val", headingStyle.getJc());
        } else {
            if (jcElement.attributeValue("val") == null) {
                jcElement.addAttribute("w:val", headingStyle.getJc());
            } else if (!jcElement.attributeValue("val").equals(headingStyle.getJc())) {
                String wrongJc = jcElement.attributeValue("val");
                jcElement.attribute("val").setValue(headingStyle.getJc()); // 自动修改对齐方式
                // 发现了不符合要求的对齐方式，添加批注
                ArrayList<String> contentList = new ArrayList<>();
                contentList.add("此处标题“" + sectionName + "”的对齐方式不符合要求，应为" + StrConverter.jcToReadableString(headingStyle.getJc()) + "，但实际为" + StrConverter.jcToReadableString(wrongJc) + "。已自动修改。");
                String commentType = "自动修改批注";
                addComment(xmlDirectory, pElement, contentList, commentType, "段尾");
            }
        }

        // 其他样式检查和修改，主要是去掉一些不该有的东西和设定一些默认的东西 ind, 缩进tab
        // 清空首行缩进
        Element indElement = pPrElement.element("ind");
        if (indElement == null) {
            indElement = pPrElement.addElement("w:ind");
            indElement.addAttribute("w:firstLineChars", "0"); // 清空首行缩进
        } else {
            if (indElement.attribute("firstLineChars") == null) {
                indElement.addAttribute("w:firstLineChars", "0");
            } else {
                indElement.attribute("firstLineChars").setValue("0");
            }
        }
        // 删除该<w:p>的首个<w:tab>标签，即段前不要有tab
        Element firstRun = pElement.element("r");
        if (firstRun != null) {
            Element firstTab = firstRun.element("tab");
            if (firstTab != null) {
                firstRun.remove(firstTab);
                addComment(xmlDirectory, pElement, new ArrayList<String>() {{
                    add("此处标题“" + sectionName + "”的段前不应该用tab缩进，已自动删除。");
                }}, "自动修改批注", "段首");
            }
        }

        /* 最低<w:pStyle> 找关联样式 */
        // TODO: 主要是找关联样式，解决加粗样式

        /* 最后添加批注（范围批注） */
        // 避免索引混乱，从后往前添加批注
        for (int i = wrongRIndex.size() - 1; i >= 0; i--) {
            int index = wrongRIndex.get(i);
            ArrayList<String> contentList = commentListList.get(i);
            String commentType = commentTypeList.get(i);
            addComment(xmlDirectory, pElement, contentList, commentType, "指定范围", index);
        }
    }

    /**
     * 检测页眉页脚页边距是否符合要求
     * 包括页码，奇偶页
     * */
    public void detectPageSetting(String xmlDirectory, DocxInfo docxInfo) {
        SAXReader reader = new SAXReader();
        try {
            // 如果文档没有设置页眉页脚，则提醒设置
            if (!(docxInfo.isHaveFooter() && docxInfo.isHaveHeader())) {
                paperDtcResult = 2;
                Document document = reader.read(xmlDirectory + "/word/document.xml");
                Element root = document.getRootElement();
                if (!docxInfo.isHaveFooter()) {
                    ArrayList<String> contentList = new ArrayList<>();
                    contentList.add("文档没有设置页脚，请设置页脚并添加页码！");
                    addComment(xmlDirectory, root.element("body"), contentList, "待修改批注", "文末");
                } else if (!(docxInfo.isHaveHeader())) {
                    ArrayList<String> contentList = new ArrayList<>();
                    contentList.add("文档没有设置页眉，请设置页眉！");
                    addComment(xmlDirectory, root.element("body"), contentList, "待修改批注", "文末");
                }
                // 保存
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding("UTF-8");
                XMLWriter writer = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/document.xml")), format);
                writer.write(document);
                writer.close();
            }
            // 当文档中有设置页眉页脚时，自动按模板设置为标准模式
            else {
                // 先获取页眉页脚的基础样式id
                Document stylesDocument = reader.read(xmlDirectory + "/word/styles.xml");
                Element stylesRoot = stylesDocument.getRootElement();
                Node node1 = stylesRoot.selectSingleNode("//w:name[@w:val='header']");
                Node node2 = stylesRoot.selectSingleNode("//w:name[@w:val='footer']");
                docxInfo.setHeaderStyleId(((Element) node1.getParent()).attributeValue("styleId"));
                docxInfo.setFooterStyleId(((Element) node2.getParent()).attributeValue("styleId"));
                System.out.println("headerStyleId: " + docxInfo.getHeaderStyleId());
                System.out.println("footerStyleId: " + docxInfo.getFooterStyleId());

                Document document = reader.read(xmlDirectory + "/word/document.xml");
                Element root = document.getRootElement();

                // 获取页眉设置模板信息
                PageSetting rep = templateInfo.getPageSetting();

                // 设置页眉页脚header.xml, footer.xml的样式id
                DocxInfo.setStyleId(xmlDirectory, docxInfo.getHeaderStyleId(), "header1.xml", rep.getHeaderContent1());
                DocxInfo.setStyleId(xmlDirectory, docxInfo.getHeaderStyleId(), "header2.xml", rep.getHeaderContent2());
                DocxInfo.setStyleId(xmlDirectory, docxInfo.getFooterStyleId(), "footer1.xml", null);
                DocxInfo.setStyleId(xmlDirectory, docxInfo.getFooterStyleId(), "footer2.xml", null);

                // 如果文档中没有正文部分即location id=“绪论”不存在，则提醒完善文档后再进行页眉页脚检测
                if (!docxInfo.isHaveMainBody()) {
                    paperDtcResult = 2;
                    ArrayList<String> contentList = new ArrayList<>();
                    contentList.add("文档结构不完整，请完善文档结构后再进行页眉页脚检测！");
                    addComment(xmlDirectory, root.element("body"), contentList, "待修改批注", "文末");
                } else {
                    // 设置第二节（最后一个sectPr）
                    Element sectPr = root.element("body").element("sectPr");
                    // 清空原有的所有设置
                    sectPr.clearContent();
                    // 设置页眉
                    sectPr.addElement("w:headerReference").addAttribute("w:type", "even").addAttribute("r:id", docxInfo.getHeader2Rid());
                    sectPr.addElement("w:headerReference").addAttribute("w:type", "odd").addAttribute("r:id", docxInfo.getHeader2Rid());
                    // 设置页脚
                    if (rep.isOddEvenPage()) {
                        sectPr.addElement("w:footerReference").addAttribute("w:type", "even").addAttribute("r:id", docxInfo.getFooter2Rid());
                        sectPr.addElement("w:footerReference").addAttribute("w:type", "odd").addAttribute("r:id", docxInfo.getFooter1Rid());

                        Document settingsDocument = reader.read(xmlDirectory + "/word/settings.xml");
                        Element settingsRoot = settingsDocument.getRootElement();
                        Element evenAndOdd = settingsRoot.element("evenAndOddHeaders");
                        if (evenAndOdd == null) {
                            settingsRoot.addElement("w:evenAndOddHeaders");
                        }
                        Element mirrorMargin = settingsRoot.element("mirrorMargins");
                        if (mirrorMargin == null) {
                            settingsRoot.addElement("w:mirrorMargins");
                        }

                        // 保存
                        OutputFormat format = OutputFormat.createPrettyPrint();
                        format.setEncoding("UTF-8");
                        XMLWriter writer = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/settings.xml")), format);
                        writer.write(settingsDocument);
                        writer.close();

                    } else {
                        sectPr.addElement("w:footerReference").addAttribute("w:type", "even").addAttribute("r:id", docxInfo.getFooter1Rid());
                        sectPr.addElement("w:footerReference").addAttribute("w:type", "odd").addAttribute("r:id", docxInfo.getFooter1Rid());
                    }

                    // 设置页面大小(固定大小)，页边距(根据模板设置)
                    sectPr.addElement("w:pgSz").addAttribute("w:w", "11906").addAttribute("w:h", "16838");
                    sectPr.addElement("w:pgMar").addAttribute("w:top", rep.getTopMargin()).addAttribute("w:right", rep.getRightMargin()).addAttribute("w:bottom", rep.getBottomMargin()).addAttribute("w:left", rep.getLeftMargin()).addAttribute("w:header", rep.getHeaderMargin()).addAttribute("w:footer", rep.getFooterMargin()).addAttribute("w:gutter", "0");

                    // 页码开始数设置为1
                    sectPr.addElement("w:pgNumType").addAttribute("w:start", "1");
                    // 默认的网格
                    sectPr.addElement("w:cols").addAttribute("w:space", "425");
                    sectPr.addElement("w:docGrid").addAttribute("w:type", "lines").addAttribute("w:linePitch", "312");

                    // 清空除最后一个sectPr之外的所有sectPr
                    List<Node> sectPrList = root.selectNodes("//w:sectPr");
                    for (int i = 0; i < sectPrList.size() - 1; i++) {
                        sectPrList.get(i).detach();
                    }

                    // 设置第一个sectPr，这个节应该在绪论location标签的上一个<p>中
                    List<Element> pElements = root.element("body").elements();
                    for (int i = 0; i < pElements.size(); i++) {
                        Element locationElement = pElements.get(i + 1);
                        if (Objects.equals(locationElement.getName(), "location") && locationElement.attributeValue("id").equals("绪论")) {
                            Element pElement = pElements.get(i);
                            while (!Objects.equals(pElement.getName(), "p")) {
                                i--;
                                pElement = pElements.get(i);
                            }
                            Element sectPr1 = pElement.element("pPr").addElement("w:sectPr");
                            // 设置页眉（第一节，不需要设置页脚）
                            sectPr1.addElement("w:headerReference").addAttribute("w:type", "even").addAttribute("r:id", docxInfo.getHeader1Rid());
                            sectPr1.addElement("w:headerReference").addAttribute("w:type", "odd").addAttribute("r:id", docxInfo.getHeader1Rid());

                            // 设置页面大小(固定大小)，页边距(根据模板设置)，第一节
                            sectPr1.addElement("w:pgSz").addAttribute("w:w", "11906").addAttribute("w:h", "16838");
                            sectPr1.addElement("w:pgMar").addAttribute("w:top", rep.getTopMargin()).addAttribute("w:right", rep.getRightMargin()).addAttribute("w:bottom", rep.getBottomMargin()).addAttribute("w:left", rep.getLeftMargin()).addAttribute("w:header", rep.getHeaderMargin()).addAttribute("w:footer", rep.getFooterMargin()).addAttribute("w:gutter", "0");

                            // 默认的网格
                            sectPr1.addElement("w:cols").addAttribute("w:space", "425");
                            sectPr1.addElement("w:docGrid").addAttribute("w:type", "lines").addAttribute("w:linePitch", "312");
                            break;
                        }
                    }
                    addComment(
                            xmlDirectory, root.element("body"),
                            new ArrayList<String>() {{
                                add("文档页眉页脚页边距设置已自动修改为标准模式。");
                            }},
                            "自动修改批注", "文末");
                }
                // 保存
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding("UTF-8");
                XMLWriter writer = new XMLWriter(Files.newOutputStream(Paths.get(xmlDirectory + "/word/document.xml")), format);
                writer.write(document);
                writer.close();
            }
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对目录进行检测
     * */
    public void detectCatalogue(String xmlDirectory, ArrayList<String> sectionOrder) {
        // 获取论文的“目录”部分的模板要求
        Catalogue req = templateInfo.getCatalogue();

        int CatalogueIndex = sectionOrder.indexOf(Catalogue.contentName);
        if (CatalogueIndex == -1) {
            return;
        }

        // 开始检测
        // 1. 处理非正常的情况，即目录不存在
        String documentPath = xmlDirectory + "/word/document.xml";
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(documentPath);
            Element root = document.getRootElement();
            List<Node> locationNodes = root.selectNodes("//location[@id='目录']");
            Element bodyElement = root.element("body");

            if (!(locationNodes.size() == 1)) {
                if (locationNodes.isEmpty()) {
                    addCommentForLackContent(xmlDirectory, bodyElement.element("p"), Catalogue.contentName, sectionOrder, CatalogueIndex);
                } else if (locationNodes.size() > 1) {
                    addCommentForRepeatContent(xmlDirectory, bodyElement.element("p"), Catalogue.contentName, "start", Catalogue.markWord, 1);
                }
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding("UTF-8");
                XMLWriter writer = new XMLWriter(Files.newOutputStream(Paths.get(documentPath)), format);
                writer.write(document);
                writer.close();
            } else {
                // 2. 处理正常的情况，目录存在且唯一
                int startIndex = -1; // 目录开始的位置
                List<Element> pElements = bodyElement.elements();
                for (int i = 0; i < pElements.size(); i++) {
                    Element locationElement = pElements.get(i);
                    if (Objects.equals(locationElement.getName(), "location") && locationElement.attributeValue("id").equals("目录")) {
                        startIndex = i;
                        break;
                    }
                }
                ArrayList<Element> emptyElements = new ArrayList<>(); // 用于记录没有文本内容的<w:p>标签
                StringBuilder content = new StringBuilder(); // 用于记录目录的文本内容
                for (int i = startIndex + 1; i < pElements.size(); i++) {
                    Element pElement = pElements.get(i);
                    if (Objects.equals(pElement.getName(), "location")) {
                        break;
                        // 说明目录结束
                    }
                    if (!Objects.equals(pElement.getName(), "p")) {
                        continue; // 不是<w:p>标签，跳过
                    }

                    /* 标题检查 */
                    if (i == startIndex + 1) {
                        detectHeadingStyle(xmlDirectory, pElement, Conclusion.contentName, req.getHeadingRep());
                        continue;
                    }
                    /* 段落内容格式检查和空段检查 */
                    if (isEmptyParagraph(pElement)) {
                        // 没有文本内容的空段落会存入emptyElements，后续统一删除
                        emptyElements.add(pElement);
                        continue;
                    }

                    // 读取目录的内容
                    List<Node> tNodes = pElement.selectNodes("descendant::w:t");
                    for (Node node : tNodes) {
                        content.append(node.getText().trim().replaceAll(" ","")); // 去两端空格
                    }
                }
                // 处理空段落
                for (Element emptyElement : emptyElements) {
                    emptyElement.detach();
                }
                // 检查目录的内容是否有缺漏
                Element headingElement = pElements.get(startIndex + 1);
                for (int i = 0; i <= CatalogueIndex; i++) {
                    // 将目录之前出现的标题内容，在outlineList中删除
                    outlineList.remove(sectionOrder.get(i).replaceAll(" ", ""));
                }
                outlineList.remove(paperName.replaceAll(" ", ""));
                outlineList.remove(paperEnglishName.replaceAll(" ", ""));
                for (String outline : outlineList) {
                    if (!content.toString().contains(outline)) {
                        // 目录中没有出现的标题内容，添加批注
                        addComment(xmlDirectory, headingElement, new ArrayList<String>() {{
                            add("目录的内容可能缺少了内容：“" + outline + "”，请检查是否遗漏，可以尝试添加或重新更新目录。");
                        }}, "建议修改批注", "段尾");

                    }
                }

                // 保存
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding("UTF-8");
                XMLWriter writer = new XMLWriter(Files.newOutputStream(Paths.get(documentPath)), format);
                writer.write(document);
                writer.close();


            }


        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }


    }


    /**
     * 开始对论文进行格式检测<br>
     * step1: 解压docx文件并备份<br>
     * step2: 对解压的xml进行预处理<br>
     * ...
     * stepN: 给paperDtcResult, resultDocPath赋值
     */
    public void startDetection() {
        // step1: 确定已经获取了论文模板信息
        if (templateInfo == null){
            init();
        }

        // step1: 解压docx文件并备份，和一些初始化
        this.commentsNum = 1;
        String xmlDirectory = DocxUtils.unZipDocx(docFilePath);
        if (xmlDirectory == null) {
            System.out.println("startDetection: 文件不存在，或导入文件不是docx文件或doc文件，或已经损坏，请重新导入!");
            return;
        }
        ArrayList<String> orderList = getPaperSectionOrder(templateId);
        DocxInfo docxInfo = new DocxInfo();

        // step2: 对解压的xml文件进行预处理，包括清空原有的批注信息，添加默认的批注样式，添加people.xml和comments.xml文件的关联信息
        try {
            PreProcess.paperPreProcess(xmlDirectory, docxInfo);
        } catch (IOException | DocumentException e) {
            System.err.println("当前函数: " + "startDetection" + "，文件预处理出错，错误信息: " + e.getMessage());
        }

        // 清空所有的批注
        try {
            PreProcess.clearAllComments(xmlDirectory);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException(e);
        }

        // step3: 对解压后的xml文件进行格式检测
        // 3.1 读取document.xml文件，识别出论文的各个部分，用特定的标记标签标识每个部分，以便之后分模块检测。即进行分块和内容定位。
        try {
            ContentLocation.contentLocation(xmlDirectory, paperName, paperEnglishName, templateId, docxInfo);
        } catch (DocumentException | IOException e) {
            System.err.println("内容定位出错: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // 3.2 检测论文的各个部分是否符合要求
        // 1. 检测“诚信声明”部分
        detectSOH(xmlDirectory, orderList);

        // 2. 检测“中文摘要”部分
        detectAbstractOfChinese(xmlDirectory, orderList);

        // 3. 检测“英文摘要”部分
        detectAbstractOfEnglish(xmlDirectory, orderList);

        // 4. 检测“结论”部分
        detectConclusion(xmlDirectory, orderList);

        // 5. 检测“致谢”部分
        detectThanks(xmlDirectory, orderList);

        // 6. 检测“绪论”和正文部分
        detectMainBody(xmlDirectory, orderList);

        // 7. 检测“参考文献”部分
        detectReferences(xmlDirectory, orderList);

        // 8. 检测页眉页脚页边距设置
        detectPageSetting(xmlDirectory, docxInfo);

        // 9. 检测目录
        detectCatalogue(xmlDirectory, orderList);


        // 9. 检测“附录”部分


        // 3.3其他要求的检测
        // 1. 参考文献的引用
        // 2. 编号的格式
        // 3. 图表的格式
        // 4. 公式的格式



        // 清理定位标签
        ContentLocation.clearLocationElement(xmlDirectory);

        // 生成概述概述批注，返回表示检测结果的数字
        paperDtcResult = generateSummaryComment(xmlDirectory);

        // 将处理后的xml文件重新打包成docx文件
        String absolutePath = DocxUtils.zipDocx(xmlDirectory, paperDtcResult);
        resultDocxName = Path.of(absolutePath).getFileName().toString();
        System.out.println("处理后的docx：" + resultDocxName);

        // 如果没有问题，则给出pdf
        if (paperDtcResult == 0 || paperDtcResult == 1) {
        resultPDFName = docxToPdf(xmlDirectory, docxEndCommentNum);
        }
    }



    /* 构造函数和基础get,set方法 */

    /**
     * 构造函数
     *
     * @param docFilePath 要处理的文件路径
     * @param paperName   论文名称
     * @param templateId  模板id
     */
    public WordDocFormatDetection(String docFilePath, String paperName, String paperEnglishName, String templateId, String username) {
        this.docFilePath = docFilePath;
        this.paperName = paperName;
        this.templateId = templateId;
        this.paperEnglishName = paperEnglishName;
        this.username = username;
    }


    public String getDocFilePath() {
        return docFilePath;
    }


    public void setDocFilePath(String docFilePath) {
        // 判断文件是否存在
        File file = new File(docFilePath);
        if (!file.exists()) {
            throw new RuntimeException("函数名：setDocFilePath，错误信息：文件不存在");
        }
        this.docFilePath = docFilePath;
    }

    public String getPaperName() {
        return paperName;
    }


    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }


    public String getTemplateId() {
        return templateId;
    }


    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }


    public int getPaperDtcResult() {
        return paperDtcResult;
    }


    public void setPaperDtcResult(int paperDtcResult) {
        this.paperDtcResult = paperDtcResult;
    }


    public String getResultDocxName() {
        return resultDocxName;
    }


    public void setResultDocxName(String resultDocPath) {
        this.resultDocxName = resultDocPath;
    }



    public String getPaperEnglishName() {
        return paperEnglishName;
    }

    public void setPaperEnglishName(String paperEnglishName) {
        this.paperEnglishName = paperEnglishName;
    }


    public Integer getCommentsNum() {
        return commentsNum;
    }


    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResultPDFName() {
        return resultPDFName;
    }

    public void setResultPDFName(String resultPDFName) {
        this.resultPDFName = resultPDFName;
    }

    public String getIsSendToTeacher() {
        return isSendToTeacher;
    }

    public void setIsSendToTeacher(String isSendToTeacher) {
        this.isSendToTeacher = isSendToTeacher;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }
}
