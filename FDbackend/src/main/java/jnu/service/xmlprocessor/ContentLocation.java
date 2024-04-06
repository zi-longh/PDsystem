package jnu.service.xmlprocessor;

import jnu.template.*;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ContentLocation {
    /**
     * 创建标识元素，用于标识论文的各个部分的开始和结束.
     * 主要用于方法：contentLocation
     * @param nameValue 属性name的值, 可以通过对应类的静态变量获取
     * @param place 标识位置 start表示开始，end表示结束
     * */
    public static Element createLocationElement(String nameValue, String place) {
        Element locationElement = DocumentHelper.createElement("location");
        locationElement.addAttribute("id", nameValue);
        locationElement.addAttribute("place", place);
        return locationElement;
    }

    /**
     * [测试完成]
     * 清空location元素，用于清空论文的各个部分的开始和结束的标识. 在论文完成检测后调用。
     * @param xmlDirectory 解压后的xml文件夹路径
     * */
    public static void clearLocationElement(String xmlDirectory) {
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(documentXmlPath);
            Element bodyElement = document.getRootElement().element("body");
            List<Element> pElements = bodyElement.elements();
            for (Element element : pElements) {
                if (element.getName().equals("location")) {
                    bodyElement.remove(element);
                }
            }
            // 保存document.xml文件
            OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
            format.setEncoding("UTF-8");
            XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
            xmlwriter.write(document);
            xmlwriter.close();
            System.out.println("函数名：clearLocationElement，清空location元素成功。");
        } catch (
                DocumentException | IOException e) {
            /*         System.err.println("当前函数：clearLocationElement，清空location元素出现异常，异常信息如下：" + e.getMessage());*/
            e.printStackTrace();
        }
    }

    /**
     * TODO:等待测试[完成部分测试]
     * 识别论文的各个部分，用特定的标记标签标识每个部分，以便之后分模块检测。即进行分块和内容定位。
     * 一个部分按规定只能出现一次，但本方法不做唯一性检查，都会添加上标签，具体处理交给后续的检测方法。
     * @param xmlDirectory 解压后的xml文件夹路径
     * */
    public static void contentLocation(String xmlDirectory, String paperName, String paperEnglishName, String templateId, DocxInfo docxInfo) throws DocumentException, IOException {
        /*
        自定义的标记标签，用于标识每个部分的开始和结束。格式如下：
         <location name="诚信声明" place="start"></location>
        一些部分只能定位到开始。
        */
        // 读取document.xml文件
        String documentXmlPath = xmlDirectory + "/word/document.xml";
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(documentXmlPath);
        Element rootElement = document.getRootElement();
        List<Element> bodyElements = rootElement.elements().get(0).elements(); // 获取body标签下的所有子标签
        int index = 0; // 用于记录当前处理的bodyElements的索引

        // 遍历所有<p>等级的标签，通过内容分析确定每个部分的范围，在特定位置插入新的标签
        ArrayList<Element> waitToInsertElements = new ArrayList<>(); // 用于记录需要插入的标签
        ArrayList<Integer> waitToInsertIndex = new ArrayList<>(); // 用于记录需要插入的标签的索引
        for (Element element : bodyElements) {
            // 记录<w:p>标签中所有的文本内容，即<w:p>标签所有子标签<w:t>标签内容的拼接
            StringBuilder text = new StringBuilder();
            if (element.getName().equals("p")) {
                // 忽略非<p>标签
                // 获取当前节点下的所有后辈<w:t>标签
                List<Node> nodes = element.selectNodes("descendant::w:t");
                for (Node node : nodes) {
                    text.append(node.getText().trim());
                }
                String pText = text.toString();
                // 检查当前<p>标签的文本内容中是否包含某个部分的关键词，如果包含则在当前<p>标签前后插入自定义的标记标签
                /* 诚信声明部分
                 * 有且只有"诚信声明"四个字的<p>才能作为开始
                 * 同时包含 毕业论文作者签名 和 签名日期 的<p>才能作为结束 */
                if (pText.replaceAll(" ", "").equals(StatementOfHonesty.markWord)) {
                    Element locationElement = createLocationElement(StatementOfHonesty.contentName, "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    // 记录需要插入的标签的索引, 标识插入到当前位置，index+1则表示插入到当前位置的后面
                    System.out.println("已插入<location id=诚信声明 place=start>");
                } else if (pText.contains(StatementOfHonesty.authorSign) && pText.contains(StatementOfHonesty.dateSign)) {
                    Element locationElement = createLocationElement(StatementOfHonesty.contentName, "end");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index + 1);
                    System.out.println("已插入<location id=诚信声明 place=end>");
                }
                /* 中文摘要 */
                else if (pText.equals(paperName)) {
                    Element locationElement = createLocationElement("中文摘要", "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=中文摘要 place=start>");
                } else if (pText.replaceAll(" ","").contains(AbstractOfChinese.markWord1.replaceAll(" ",""))) {
                    Element locationElement = createLocationElement("中文摘要", "abstract");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=中文摘要 place=abstract>");
                } else if (pText.replaceAll(" ","").contains(AbstractOfChinese.markWord2)) {
                    Element locationElement = createLocationElement("中文摘要", "end");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index + 1);
                    System.out.println("已插入<location id=中文摘要 place=end>");
                }

                /* 英文摘要 */
                else if (pText.equals(paperEnglishName)) {
                    Element locationElement = createLocationElement("英文摘要", "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=英文摘要 place=start>");
                } else if (pText.contains(AbstractOfEnglish.markWord1) || pText.contains("Keywords:")) {
                    Element locationElement = createLocationElement("英文摘要", "abstract");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=英文摘要 place=abstract>");
                } else if (pText.contains(AbstractOfEnglish.markWord2) || pText.contains("Abstract:")) {
                    Element locationElement = createLocationElement("英文摘要", "end");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index + 1);
                    System.out.println("已插入<location id=英文摘要 place=end>");
                }

                /* 结论 */
                else if (pText.equals(Conclusion.markWord)) {
                    Element locationElement = createLocationElement("结论", "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=结论 place=start>");
                }
                /* 致谢 */
                else if (pText.equals(Thanks.markWord)) {
                    Element locationElement = createLocationElement("致谢", "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=致谢 place=start>");
                }
                /* 参考文献 */
                else if (pText.equals(References.markWord)) {
                    Element locationElement = createLocationElement("参考文献", "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=参考文献 place=start>");
                }
                /* 绪论
                 *  与正文相连 */
                else if (pText.equals(MainBody.markWord)) {
                    Element locationElement = createLocationElement("绪论", "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=绪论 place=start>");
                    docxInfo.setHaveMainBody(true);
                }


                /* 以下是其他部分，TODO:等待具体完善 */
                /* 目录 */
                else if (pText.replaceAll(" ", "").equals(Catalogue.markWord)) {
                    Element locationElement = createLocationElement("目录", "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=目录 place=start>");
                }
                /* 附录：TODO: 暂时不考虑 */
                else if (pText.equals(Appendix.markWord)) {
                    Element locationElement = createLocationElement("附录", "start");
                    waitToInsertElements.add(locationElement);
                    waitToInsertIndex.add(index);
                    System.out.println("已插入<location id=附录 place=start>");
                }
            }
            text.setLength(0); // 清空StringBuilder
            index++;
        }
        // 插入标签，从后往前插入，避免索引错乱
        for (int i = waitToInsertElements.size() - 1; i >= 0; i--) {
            bodyElements.add(waitToInsertIndex.get(i), waitToInsertElements.get(i));
        }
        System.out.println("已插入" + waitToInsertElements.size() + "个标签，用于标识论文的各个部分的开始和结束。");
        // 最后在文件的末尾倒数第二个位置添加一个标识，用于标识论文的结束。即最后一个sectPr标签的前面
        Element endElement = createLocationElement("end", "end");
        bodyElements.add(bodyElements.size() - 1, endElement);
        System.out.println("已插入<location id=end place=end>");

        // 保存document.xml文件
        OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码
        format.setEncoding("UTF-8");
        XMLWriter xmlwriter = new XMLWriter(Files.newOutputStream(Paths.get(documentXmlPath)), format);
        xmlwriter.write(document);
        xmlwriter.close();
    }
}
