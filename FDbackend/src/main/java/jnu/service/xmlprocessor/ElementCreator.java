package jnu.service.xmlprocessor;

import jnu.template.HeadingRep;
import jnu.template.ParagraphRep;
import org.dom4j.CDATA;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.List;

public class ElementCreator {


    private static void initElement(Element pElement, ParagraphRep pRep, boolean isPrefixBold, String prefixFont, String prefixStr, String contentStr) {
        Element pPrElement = pElement.element("pPr");
        // 删除该段落下的所有的w:r
        pElement.selectNodes("descendant::w:r").forEach(Node::detach);
        // 删除w:pPr下所有的w:rFonts，w:b，w:spacing，w:ind，w:jc，w:sz，w:szCs
        pPrElement.selectNodes("descendant::w:rFonts").forEach(Node::detach);
        pPrElement.selectNodes("descendant::w:b").forEach(Node::detach);
        pPrElement.selectNodes("descendant::w:spacing").forEach(Node::detach);
        pPrElement.selectNodes("descendant::w:ind").forEach(Node::detach);
        pPrElement.selectNodes("descendant::w:jc").forEach(Node::detach);
        pPrElement.selectNodes("descendant::w:sz").forEach(Node::detach);
        pPrElement.selectNodes("descendant::w:szCs").forEach(Node::detach);

        // 设置w:pPr
        /* w:spacing，w:ind，w:jc, w:b */

        pPrElement.addElement("w:spacing").addAttribute("w:line", pRep.getLine()).addAttribute("w:lineRule", "auto");
        pPrElement.addElement("w:ind").addAttribute("w:firstLineChars", pRep.getIndent());
        pPrElement.addElement("w:jc").addAttribute("w:val", "both");
        if (pRep.getBold() == 1) pPrElement.addElement("w:b");  // 加粗
        else if (pRep.getBold() == 0) pPrElement.addElement("w:b").addAttribute("w:val", "0");

        // 添加第一个w:r，并设置w:rPr
        /* w:rFonts, w:sz，w:szCs , w:b */
        Element rElement = pElement.addElement("w:r");
        Element rPrElement = rElement.addElement("w:rPr");
        rPrElement.addElement("w:rFonts").addAttribute("w:hint", "eastAsia").addAttribute("w:ascii", pRep.getFontEnglishType()).addAttribute("w:hAnsi", pRep.getFontEnglishType()).addAttribute("w:eastAsia", prefixFont);
        rPrElement.addElement("w:sz").addAttribute("w:val", String.valueOf(pRep.getFontSize()));
        rPrElement.addElement("w:szCs").addAttribute("w:val", String.valueOf(pRep.getFontSize()));
        if (isPrefixBold) rPrElement.addElement("w:b");
        else rPrElement.addElement("w:b").addAttribute("w:val", "0");

        Element wt = rElement.addElement("w:t").addAttribute("xml:space", "preserve");
        if (prefixStr.contains("]")) {
            CDATA cdata = DocumentHelper.createCDATA(prefixStr + " ");
            wt.add(cdata);
        } else {
            wt.addText(prefixStr);
        }

        // 添加第二个w:r，并设置w:rPr
        /* w:rFonts, w:sz，w:szCs */
        Element rElement2 = pElement.addElement("w:r");
        Element rPrElement2 = rElement.addElement("w:rPr");
        rPrElement2.addElement("w:rFonts").addAttribute("w:hint", "eastAsia").addAttribute("w:ascii", pRep.getFontEnglishType()).addAttribute("w:hAnsi", pRep.getFontEnglishType()).addAttribute("w:eastAsia", pRep.getFontType());

        rPrElement2.addElement("w:sz").addAttribute("w:val", String.valueOf(pRep.getFontSize()));
        rPrElement2.addElement("w:szCs").addAttribute("w:val", String.valueOf(pRep.getFontSize()));
        rElement2.addElement("w:t").addText(contentStr);
    }


    /**
     * 转化为标准的中文关键字段落，返回关键字个数
     * */
    static int convertToStdKeywords(Element pElement, ParagraphRep pRep, boolean isPrefixBold, String prefixFont, int maxKeywordsCount) {
        String prefixStr = "[关键词]";
        StringBuilder content = new StringBuilder();
        List<Node> wtNodes = pElement.selectNodes("descendant::w:t");
        for (Node wtNode : wtNodes) {
            content.append(wtNode.getText());
        }
        // 去除前缀词，即去除[]中的内容和[]，并格式化关键词
        String contentStr = content.toString().replaceFirst("\\[.*?\\]", "");


        contentStr = contentStr.trim().replaceAll(" {2}", " ");
        contentStr = contentStr.trim().replaceAll(" {2}", " ");
        contentStr = contentStr.trim().replaceAll(" {2}", " ");
        contentStr = contentStr.trim().replaceAll(" ", "；");
        contentStr = contentStr.trim().replaceAll(";", "；");
        contentStr = contentStr.trim().replaceAll(",", "；");
        contentStr = contentStr.trim().replaceAll("，", "；");
        contentStr = contentStr.trim().replaceAll("。", "；");
        contentStr = contentStr.trim().replaceAll("\\.", "；");
        contentStr = contentStr.trim().replaceAll("、", "；");
        // 如果关键词最后面有分号，去掉
        if (contentStr.charAt(contentStr.length() - 1) == '；') {
            contentStr = contentStr.substring(0, contentStr.length() - 1);
        }

        initElement(pElement, pRep, isPrefixBold, prefixFont, prefixStr, contentStr);

        // 返回关键词个数，以分号为分隔符
        return contentStr.split("；").length + 1;
    }


    /**
     * 转化为标准中文摘要正文段落，返回摘要正文字数
     * */
    static int convertToStdAbstract(Element pElement, ParagraphRep pRep, boolean isPrefixBold, String prefixFont, int maxContentLength) {
        String prefixStr = "[摘  要]";
        StringBuilder content = new StringBuilder();
        List<Node> wtNodes = pElement.selectNodes("descendant::w:t");
        for (Node wtNode : wtNodes) {
            content.append(wtNode.getText().trim());
        }
        // 去除所有空格
        String contentStr = content.toString().replaceAll(" ", "");
        // 去除前缀词摘要
        contentStr = contentStr.replace("[摘要]", "");


        initElement(pElement, pRep, isPrefixBold, prefixFont, prefixStr, contentStr);
        return contentStr.length();
    }

    /**
     * 转化为标准英文关键字段落，返回关键字个数
     * */
    static int convertToStdKeywordsEnglish(Element pElement, ParagraphRep pRep, boolean isPrefixBold, String prefixFont, int maxKeywordsCount) {
        String prefixStr = "Keywords：";
        StringBuilder content = new StringBuilder();
        List<Node> wtNodes = pElement.selectNodes("descendant::w:t");
        for (Node wtNode : wtNodes) {
            content.append(wtNode.getText());
        }
        // 去除前缀词关键词
        String contentStr = content.toString().replace("Keywords：", "");
        contentStr = contentStr.trim().replaceAll(" {2}", " ");
        contentStr = contentStr.trim().replaceAll(" {2}", " ");
        contentStr = contentStr.trim().replaceAll(";", "；");
        contentStr = contentStr.trim().replaceAll(",", "；");
        contentStr = contentStr.trim().replaceAll("。", "；");
        contentStr = contentStr.trim().replaceAll("\\.", "；");
        contentStr = contentStr.trim().replaceAll("、", "；");

        initElement(pElement, pRep, isPrefixBold, prefixFont, prefixStr, contentStr);
        return contentStr.split("；").length + 1;
    }

    /**
     * 转化为标准英文摘要正文段落，返回摘要正文单词数
     * */
    static int convertToStdAbstractEnglish(Element pElement, ParagraphRep pRep, boolean isPrefixBold, String prefixFont, int maxContentLength) {
        String prefixStr = "Abstract：";
        StringBuilder content = new StringBuilder();
        List<Node> wtNodes = pElement.selectNodes("descendant::w:t");
        for (Node wtNode : wtNodes) {
            content.append(wtNode.getText());
        }
        // 去除前缀词摘要
        String contentStr = content.toString().replace("Abstract：", "");
        contentStr = contentStr.trim().replaceAll(" {2}", " ");
        contentStr = contentStr.trim().replaceAll(" {2}", " ");

        initElement(pElement, pRep, isPrefixBold, prefixFont, prefixStr, contentStr);
        return contentStr.split(" ").length;
    }


    /**
     * 按照标准添加标准w:rRr标签，针对标题
     * */
    static void addStdHeadingRPr(Element pElement, HeadingRep headingStyle) {
        Element newPPrElement = pElement.addElement("w:rPr");
        /* <w:rFonts w:hint="eastAsia" w:ascii="" w:hAnsi="" w:eastAsia="" />
         * <w:sz w:val="30" />
           <w:szCs w:val="30" />
           <w:b/> */
        newPPrElement.addAttribute("w:hint", "eastAsia");
        newPPrElement.addAttribute("w:ascii", headingStyle.getFontEnglishType());
        newPPrElement.addAttribute("w:hAnsi", headingStyle.getFontEnglishType());
        newPPrElement.addAttribute("w:eastAsia", headingStyle.getFontType());
        newPPrElement.addElement("w:sz").addAttribute("w:val", String.valueOf(headingStyle.getFontSize()));
        newPPrElement.addElement("w:szCs").addAttribute("w:val", String.valueOf(headingStyle.getFontSize()));
        if (headingStyle.getBold() == 1) newPPrElement.addElement("w:b");
    }

    /**
     * 按照标准添加标准w:rRr标签，针对段落
     * */
    static void addStdParagraphRPr(Element pElement, ParagraphRep paragraphRep) {
        Element newPPrElement = pElement.addElement("w:rPr");
        newPPrElement.addAttribute("w:hint", "eastAsia");
        newPPrElement.addAttribute("w:ascii", paragraphRep.getFontEnglishType());
        newPPrElement.addAttribute("w:hAnsi", paragraphRep.getFontEnglishType());
        newPPrElement.addAttribute("w:eastAsia", paragraphRep.getFontType());
        newPPrElement.addElement("w:sz").addAttribute("w:val", String.valueOf(paragraphRep.getFontSize()));
        newPPrElement.addElement("w:szCs").addAttribute("w:val", String.valueOf(paragraphRep.getFontSize()));
        if (paragraphRep.getBold() == 1) newPPrElement.addElement("w:b");
    }
}
