package jnu.service.xmlprocessor;

import jnu.template.Caption;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 进行格式检测的工具类
 */
public class Utils {


    /**
     * 修改段落为标准的图注表注格式
     * */
    public static void StandardizeCaption(Element pElement, Caption req) {
        // 清空原有的所有字体属性，包括rFonts, sz, szCs, b
        pElement.selectNodes("descendant::w:rFonts").forEach(Node::detach);
        pElement.selectNodes("descendant::w:sz").forEach(Node::detach);
        pElement.selectNodes("descendant::w:szCs").forEach(Node::detach);
        pElement.selectNodes("descendant::w:b").forEach(Node::detach);
        pElement.selectNodes("descendant::w:i").forEach(Node::detach);
        pElement.selectNodes("descendant::w:iCs").forEach(Node::detach);

        // 添加标准的字体属性
        Element rprElement = pElement.element("pPr").element("rPr");
        if (rprElement == null) {
            rprElement = pElement.element("pPr").addElement("w:rPr");
        }
        rprElement.addElement("w:rFonts").addAttribute("w:ascii", req.getFontEnglishName()).addAttribute("w:eastAsia", req.getFontName());
        rprElement.addElement("w:sz").addAttribute("w:val", req.getFontSize());
        rprElement.addElement("w:szCs").addAttribute("w:val", req.getFontSize());

        Element pPrElement = pElement.element("pPr");
        if (pPrElement == null) {
            pPrElement = pElement.addElement("pPr");
        }
        if (pPrElement.element("jc") == null) {
            pPrElement.addElement("w:jc").addAttribute("w:val", "center");
        }else{
            Attribute jcAttr = pPrElement.element("jc").attribute("val");
            jcAttr.setValue("center");
        }

    }

    /**
     * 获取标题的caption style id，用于检测图片和表格的图注表注
     * */
    public static String getCaptionStyleId(String xmlDirectory) {
        String captionStyleId = null;
        SAXReader reader = new SAXReader();
        String styleXmlPath = xmlDirectory + "/word/styles.xml";
        try {
            Document document = reader.read(styleXmlPath);
            Element rootElement = document.getRootElement();
            Node node = rootElement.selectSingleNode("//w:name[@w:val='caption']");
            if (node == null) {
                System.out.println("getCaptionStyleId: caption style not found");
            } else {
                Element element = (Element) node;
                Element parent = element.getParent();
                captionStyleId = parent.attributeValue("styleId");
                System.out.println("读取到：caption style id: " + captionStyleId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return captionStyleId;
    }


    /**
     * 检测是否是图片
     * */
    public static boolean isPicture(Element pElement) {
        HashMap nsMap = new HashMap();
        String defaultNamespace = pElement.getNamespaceURI();
        nsMap.put("w", defaultNamespace);
        nsMap.put("pic", "http://schemas.openxmlformats.org/drawingml/2006/picture");
        nsMap.put("c", "http://schemas.openxmlformats.org/drawingml/2006/chart");
        nsMap.put("v","urn:schemas-microsoft-com:vml");

        XPath xpathDrawing = DocumentHelper.createXPath("descendant::w:drawing");
        xpathDrawing.setNamespaceURIs(nsMap);
        XPath xpathObject = DocumentHelper.createXPath("descendant::w:object");
        XPath xpathGraphicData = DocumentHelper.createXPath("descendant::pic:pic");
        xpathGraphicData.setNamespaceURIs(nsMap);
        XPath xpathChart = DocumentHelper.createXPath("descendant::c:chart");
        xpathChart.setNamespaceURIs(nsMap);
        XPath xpathVShape = DocumentHelper.createXPath("descendant::v:shape");
        xpathVShape.setNamespaceURIs(nsMap);

        boolean ret = false;
        if (!xpathDrawing.selectNodes(pElement).isEmpty()) { // 含有drawing标签
            // 含有graphicData标签
            if (!xpathGraphicData.selectNodes(pElement).isEmpty()) {
                ret = true;
            }
            // 含有chart标签
            if (!xpathChart.selectNodes(pElement).isEmpty()) {
                ret = true;
            }
        }

        // 含有object标签则肯定不是图片
        if(!xpathObject.selectNodes(pElement).isEmpty()){
            ret = false;
            return ret;
        }

        // 如果是doc转化过来的docx，则图片的标签为v:shape，且id含有“图片”
        if (!xpathVShape.selectNodes(pElement).isEmpty()) {
            List<Node> vShapeList = xpathVShape.selectNodes(pElement);
            for (Node vShape : vShapeList) {
                Element vShapeElement = (Element) vShape;
                String id = vShapeElement.attributeValue("id");
                if (!id.contains("文本框")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * 检测是否是表格
     * */
    public static boolean isTable(Element pElement) {
        boolean ret = false;
        if (Objects.equals(pElement.getName(), "tbl")) {
            ret = true;
        }
        return ret;
    }

    /**
     * 检测是否是空段落，需要满足以下条件：
     * 1. 不含有任何文本
     * 2. 没有插入图片和文本框
     * 3. 不含有sectPr标签
     * 4. 不是表格
     * 5. 不含有w:pict标签
     * */
    public static boolean isEmptyParagraph(Element pElement) {
        HashMap nsMap = new HashMap();
        String defaultNamespace = pElement.getNamespaceURI();
        nsMap.put("w", defaultNamespace);
        XPath xpathText = DocumentHelper.createXPath("descendant::w:t");
        xpathText.setNamespaceURIs(nsMap);
        XPath xpathDrawing = DocumentHelper.createXPath("descendant::w:drawing");
        xpathDrawing.setNamespaceURIs(nsMap);
        XPath xpathSectPr = DocumentHelper.createXPath("descendant::w:sectPr");
        xpathSectPr.setNamespaceURIs(nsMap);
        XPath xpathPict = DocumentHelper.createXPath("descendant::w:pict"); // 出现在word2010中即从doc转化过来的docx中
        xpathPict.setNamespaceURIs(nsMap);


        boolean ret = true;
        if (!Objects.equals(pElement.getName(), "p")) {
            ret = false;
        }
        if (!xpathText.selectNodes(pElement).isEmpty()) { // 含有文本
            ret = false;
        }
        if (!xpathDrawing.selectNodes(pElement).isEmpty()) { // 含有图片或文本框
            ret = false;
        }
        if (!xpathSectPr.selectNodes(pElement).isEmpty()) { // 含有sectPr标签
            ret = false;
        }
        if (isTable(pElement)) { // 是表格
            ret = false;
        }
        if (!xpathPict.selectNodes(pElement).isEmpty()) { // 含有w:pict标签
            ret = false;
        }
        return ret;
    }

    /**
     * 获取标题的style id，用于检测多级标题
     * */
    public static ArrayList<String> getHeadingStyleIdList(String xmlDirectory) {
        ArrayList<String> headingStyleIdList = new ArrayList<>();
        SAXReader reader = new SAXReader();
        String styleXmlPath = xmlDirectory + "/word/styles.xml";
        try {
            Document document = reader.read(styleXmlPath);
            Element rootElement = document.getRootElement();
            Node node1 = rootElement.selectSingleNode("//w:name[@w:val='heading 1']");
            Node node2 = rootElement.selectSingleNode("//w:name[@w:val='heading 2']");
            Node node3 = rootElement.selectSingleNode("//w:name[@w:val='heading 3']");
            if (node1 == null) {
                throw new Exception("getHeadingStyleIdList: heading 1 style not found");
            } else {
                Element element = (Element) node1;
                Element parent = element.getParent();
                String styleId = parent.attributeValue("styleId");
                headingStyleIdList.add(styleId);
                System.out.println("读取到：heading 1 style id: " + styleId);
            }
            if (node2 == null) {
                throw new Exception("getHeadingStyleIdList: heading 2 style not found");
            } else {
                Element element = (Element) node2;
                Element parent = element.getParent();
                String styleId = parent.attributeValue("styleId");
                headingStyleIdList.add(styleId);
                System.out.println("读取到：heading 2 style id: " + styleId);
            }
            if (node3 == null) {
                throw new Exception("getHeadingStyleIdList: heading 3 style not found");
            } else {
                Element element = (Element) node3;
                Element parent = element.getParent();
                String styleId = parent.attributeValue("styleId");
                headingStyleIdList.add(styleId);
                System.out.println("读取到：heading 3 style id: " + styleId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headingStyleIdList;
    }


    /**
     * 判断传入的段落是几级标题。
     * 0表示不是标题，1表示一级标题，2表示二级标题，3表示三级标题。
     * */
    public static int isHeading(Element pElement, ArrayList<String> headingStyleIdList) {
        Element pPrElement = pElement.element("pPr");
        if (pPrElement == null) {
            return 0;
        }
        Element pStyleElement = pPrElement.element("pStyle");
        if (pStyleElement == null) {
            return 0;
        }
        String pStyleValue = pStyleElement.attributeValue("val");
        System.out.println("该段落的style值: " + pStyleValue);
        if (Objects.equals(pStyleValue, headingStyleIdList.get(0))) {
            return 1;
        } else if (Objects.equals(pStyleValue, headingStyleIdList.get(1))) {
            return 2;
        } else if (Objects.equals(pStyleValue, headingStyleIdList.get(2))) {
            return 3;
        } else {
            return 0;
        }
    }


    /**
     * 判断传入的段落是否是图注或表注。
     * */
    public static boolean isCaption(Element pElement, String captionStyleId) {
        Element pPrElement = pElement.element("pPr");
        if (captionStyleId == null) {
            return false;
        }
        if (pPrElement == null) {
            return false;
        }
        Element pStyleElement = pPrElement.element("pStyle");
        if (pStyleElement == null) {
            return false;
        }
        String pStyleValue = pStyleElement.attributeValue("val");
        if (Objects.equals(pStyleValue, captionStyleId)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断传入caption的类型，是图注还是表注，还是非法类型。
     * */
    public static int getCaptionType(Element pElement) {

        XPath xpath = DocumentHelper.createXPath("descendant::w:t");
        HashMap nsMap = new HashMap();
        String defaultNamespace = pElement.getNamespaceURI();
        nsMap.put("w", defaultNamespace);
        xpath.setNamespaceURIs(nsMap);

        List<Node> tElementList = xpath.selectNodes(pElement);
        if (tElementList.isEmpty()) {
            return 0;
        }
        StringBuilder text = new StringBuilder();

        for (Node tElement : tElementList) {
            text.append(tElement.getText());
        }

        String tValue = text.toString();
        if (tValue.contains("图")) {
            return 1;
        } else if (tValue.contains("表")) {
            return 2;
        } else {
            return 0;
        }
    }


    /**
     * 根据论文模板id获取论文内容各部分的顺序要求。
     * TODO: 从数据库中获取论文模板id对应的论文内容各部分的顺序要求，暂时按默认顺序返回
     * @return 论文内容各部分的顺序要求
     */
    public static ArrayList<String> getPaperSectionOrder(String templateId) {
        // TODO: 暂时写死，以下是默认顺序
        ArrayList<String> paperSectionOrder = new ArrayList<>();
        paperSectionOrder.add("诚信声明");
        paperSectionOrder.add("中文摘要");
        paperSectionOrder.add("英文摘要");
        paperSectionOrder.add("目录");
        paperSectionOrder.add("绪论和正文");
        paperSectionOrder.add("结论");
        paperSectionOrder.add("参考文献");
        paperSectionOrder.add("附录");
        paperSectionOrder.add("致谢");
        return paperSectionOrder;
    }


}



