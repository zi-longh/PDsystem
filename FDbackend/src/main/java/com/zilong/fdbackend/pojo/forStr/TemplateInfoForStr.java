package com.zilong.fdbackend.pojo.forStr;


import com.zilong.fdbackend.pojo.TemplateInfoPojo;
import jnu.service.xmlprocessor.StrConverter;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


/**
 * <p>
 * 用于展示模板信息的类，将模板信息转换成容易阅读的字符串形式
 * </p>
 *
 * @author zilong
 * @since 2024-03-29
 */
@Getter
@Setter
public class TemplateInfoForStr {


    /**
     * Flag由int改为String
     * Seq由int改为String
     * Bold由int根据1,0改为是，否
     * FontSz使用转换器StrConverter.fontSizeToReadableString转换
     * jc使用转换器StrConverter.jcToReadableString转换
     * AfterLine,BeforeLine使用转换器StrConverter.beforeAfterLineToReadableString转换
     * Line使用转换器StrConverter.lineSpacingToReadableString转换
     * ind使用转换器StrConverter.indentToReadableString转换
     *
     * */
    public TemplateInfoForStr(TemplateInfoPojo templateInfoPojo) {

        this.templateId = templateInfoPojo.getTemplateId();
        /* 页面设置 */
        this.pagesettingHeadercontent1 = templateInfoPojo.getPagesettingHeadercontent1();
        this.pagesettingHeadercontent2 = Objects.equals(templateInfoPojo.getPagesettingHeadercontent2(), "defaultPaperName") ? "[你的论文标题]" : templateInfoPojo.getPagesettingHeadercontent2();
        this.pagesettingTopmargin = StrConverter.marginToReadableString(templateInfoPojo.getPagesettingTopmargin());
        this.pagesettingBottommargin = StrConverter.marginToReadableString(templateInfoPojo.getPagesettingBottommargin());
        this.pagesettingLeftmargin = StrConverter.marginToReadableString(templateInfoPojo.getPagesettingLeftmargin());
        this.pagesettingRightmargin = StrConverter.marginToReadableString(templateInfoPojo.getPagesettingRightmargin());
        this.pagesettingHeadermargin = StrConverter.marginToReadableString(templateInfoPojo.getPagesettingHeadermargin());
        this.pagesettingFootermargin = StrConverter.marginToReadableString(templateInfoPojo.getPagesettingFootermargin());
        this.pagesettingOddevenpage = templateInfoPojo.getPagesettingOddevenpage() == 1 ? "是" : "否";

        /* 图注表注 */
        this.captionFontname = templateInfoPojo.getCaptionFontname();
        this.captionFontenglishname = templateInfoPojo.getCaptionFontenglishname();
        this.captionFontsize = StrConverter.fontSizeToReadableString(templateInfoPojo.getCaptionFontsize());
        this.captionPicszheightmax = "不大于" + templateInfoPojo.getCaptionPicszheightmax() + "cm";
        this.captionPicszheightmin = "不小于" + templateInfoPojo.getCaptionPicszheightmin() + "cm";
        this.captionPicszwidthmax = "不大于" + templateInfoPojo.getCaptionPicszwidthmax() + "cm";
        this.captionPicszwidthmin = "不小于" + templateInfoPojo.getCaptionPicszwidthmin() + "cm";
        this.captionRecommendnum = "图表数量不少于" + templateInfoPojo.getCaptionRecommendnum() + "个";


        /* 诚信说明 */
        this.sohFlag = Integer.toString(templateInfoPojo.getSohFlag());
        this.sohSeq = Integer.toString(templateInfoPojo.getSohSeq());
        this.sohContent = templateInfoPojo.getSohContent();
        this.sohHFontType = templateInfoPojo.getSohHFontType();
        this.sohHFontEnglishType = templateInfoPojo.getSohHFontEnglishType();
        this.sohHFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getSohHFontSz());
        this.sohHJc = StrConverter.jcToReadableString(templateInfoPojo.getSohHJc());
        this.sohHBold = templateInfoPojo.getSohHBold() == 1 ? "是" : "无";
        this.sohHAfterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getSohHAfterline());
        this.sohHBeforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getSohHBeforeline());
        this.sohHLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getSohHLine());
        this.sohPFontType = templateInfoPojo.getSohPFontType();
        this.sohPFontTypeEnglish = templateInfoPojo.getSohPFontTypeEnglish();
        this.sohPFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getSohPFontSz());
        this.sohPInd = StrConverter.indentToReadableString(templateInfoPojo.getSohPInd());
        this.sohPLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getSohPLine());
        this.sohPBold = templateInfoPojo.getSohPBold() == 1 ? "是" : "无";

        /* 中文摘要 */
        this.aocFlag = Integer.toString(templateInfoPojo.getAocFlag());
        this.aocSeq = Integer.toString(templateInfoPojo.getAocSeq());
        this.aocPrefixfont = templateInfoPojo.getAocPrefixfont();
        this.aocIsprefixbold = templateInfoPojo.getAocIsprefixbold() == 1 ? "是" : "无";
        this.aocRecommendedmaxcontentlength = "不多于" + templateInfoPojo.getAocRecommendedmaxcontentlength() + "字";
        this.aocRecommendedmincontentlength = "不少于" + templateInfoPojo.getAocRecommendedmincontentlength() + "字";
        this.aocRecommendedmaxkeywordscount = "不多于" + templateInfoPojo.getAocRecommendedmaxkeywordscount() + "个";
        this.aocRecommendedminkeywordscount = "不少于" + templateInfoPojo.getAocRecommendedminkeywordscount() + "个";
        this.aocHFontType = templateInfoPojo.getAocHFontType();
        this.aocHFontEnglishType = templateInfoPojo.getAocHFontEnglishType();
        this.aocHFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getAocHFontSz());
        this.aocHJc = StrConverter.jcToReadableString(templateInfoPojo.getAocHJc());
        this.aocHBold = templateInfoPojo.getAocHBold() == 1 ? "是" : "无";
        this.aocHAfterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getAocHAfterline());
        this.aocHBeforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getAocHBeforeline());
        this.aocHLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getAocHLine());
        this.aocPFontType = templateInfoPojo.getAocPFontType();
        this.aocPFontTypeEnglish = templateInfoPojo.getAocPFontTypeEnglish();
        this.aocPFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getAocPFontSz());
        this.aocPInd = StrConverter.indentToReadableString(templateInfoPojo.getAocPInd());
        this.aocPLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getAocPLine());
        this.aocPBold = templateInfoPojo.getAocPBold() == 1 ? "是" : "无";

        /* 英文摘要 */
        this.aoeFlag = Integer.toString(templateInfoPojo.getAoeFlag());
        this.aoeSeq = Integer.toString(templateInfoPojo.getAoeSeq());
        this.aoePrefixfont = templateInfoPojo.getAoePrefixfont();
        this.aoeIsprefixbold = templateInfoPojo.getAoeIsprefixbold() == 1 ? "是" : "无";
        this.aoeRecommendedmaxcontentlength = "不多于" + templateInfoPojo.getAoeRecommendedmaxcontentlength() + "字";
        this.aoeRecommendedmincontentlength = "不少于" + templateInfoPojo.getAoeRecommendedmincontentlength() + "字";
        this.aoeRecommendedmaxkeywordscount = "不多于" + templateInfoPojo.getAoeRecommendedmaxkeywordscount() + "个";
        this.aoeRecommendedminkeywordscount = "不少于" + templateInfoPojo.getAoeRecommendedminkeywordscount() + "个";
        this.aoeHFontType = templateInfoPojo.getAoeHFontType();
        this.aoeHFontEnglishType = templateInfoPojo.getAoeHFontEnglishType();
        this.aoeHFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getAoeHFontSz());
        this.aoeHJc = StrConverter.jcToReadableString(templateInfoPojo.getAoeHJc());
        this.aoeHBold = templateInfoPojo.getAoeHBold() == 1 ? "是" : "无";
        this.aoeHAfterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getAoeHAfterline());
        this.aoeHBeforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getAoeHBeforeline());
        this.aoeHLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getAoeHLine());
        this.aoePFontType = templateInfoPojo.getAoePFontType();
        this.aoePFontTypeEnglish = templateInfoPojo.getAoePFontTypeEnglish();
        this.aoePFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getAoePFontSz());
        this.aoePInd = StrConverter.indentToReadableString(templateInfoPojo.getAoePInd());
        this.aoePLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getAoePLine());
        this.aoePBold = templateInfoPojo.getAoePBold() == 1 ? "是" : "无";

        /* 目录 */
        this.catalogueFlag = Integer.toString(templateInfoPojo.getCatalogueFlag());
        this.catalogueSeq = Integer.toString(templateInfoPojo.getCatalogueSeq());
        this.catalogueHFontType = templateInfoPojo.getCatalogueHFontType();
        this.catalogueHFontEnglishType = templateInfoPojo.getCatalogueHFontEnglishType();
        this.catalogueHFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getCatalogueHFontSz());
        this.catalogueHJc = StrConverter.jcToReadableString(templateInfoPojo.getCatalogueHJc());
        this.catalogueHBold = templateInfoPojo.getCatalogueHBold() == 1 ? "是" : "无";
        this.catalogueHAfterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getCatalogueHAfterline());
        this.catalogueHBeforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getCatalogueHBeforeline());
        this.catalogueHLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getCatalogueHLine());

        /* 绪论和正文 */
        this.mainbodyFlag = Integer.toString(templateInfoPojo.getMainbodyFlag());
        this.mainbodySeq = Integer.toString(templateInfoPojo.getMainbodySeq());
        this.mainbodyH1FontType = templateInfoPojo.getMainbodyH1FontType();
        this.mainbodyH1FontEnglishType = templateInfoPojo.getMainbodyH1FontEnglishType();
        this.mainbodyH1FontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getMainbodyH1FontSz());
        this.mainbodyH1Jc = StrConverter.jcToReadableString(templateInfoPojo.getMainbodyH1Jc());
        this.mainbodyH1Bold = templateInfoPojo.getMainbodyH1Bold() == 1 ? "是" : "无";
        this.mainbodyH1Afterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getMainbodyH1Afterline());
        this.mainbodyH1Beforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getMainbodyH1Beforeline());
        this.mainbodyH1Line = StrConverter.lineSpacingToReadableString(templateInfoPojo.getMainbodyH1Line());
        this.mainbodyH2FontType = templateInfoPojo.getMainbodyH2FontType();
        this.mainbodyH2FontEnglishType = templateInfoPojo.getMainbodyH2FontEnglishType();
        this.mainbodyH2FontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getMainbodyH2FontSz());
        this.mainbodyH2Jc = StrConverter.jcToReadableString(templateInfoPojo.getMainbodyH2Jc());
        this.mainbodyH2Bold = templateInfoPojo.getMainbodyH2Bold() == 1 ? "是" : "无";
        this.mainbodyH2Afterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getMainbodyH2Afterline());
        this.mainbodyH2Beforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getMainbodyH2Beforeline());
        this.mainbodyH2Line = StrConverter.lineSpacingToReadableString(templateInfoPojo.getMainbodyH2Line());
        this.mainbodyH3FontType = templateInfoPojo.getMainbodyH3FontType();
        this.mainbodyH3FontEnglishType = templateInfoPojo.getMainbodyH3FontEnglishType();
        this.mainbodyH3FontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getMainbodyH3FontSz());
        this.mainbodyH3Jc = StrConverter.jcToReadableString(templateInfoPojo.getMainbodyH3Jc());
        this.mainbodyH3Bold = templateInfoPojo.getMainbodyH3Bold() == 1 ? "是" : "无";
        this.mainbodyH3Afterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getMainbodyH3Afterline());
        this.mainbodyH3Beforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getMainbodyH3Beforeline());
        this.mainbodyH3Line = StrConverter.lineSpacingToReadableString(templateInfoPojo.getMainbodyH3Line());
        this.mainbodyPFontType = templateInfoPojo.getMainbodyPFontType();
        this.mainbodyPFontTypeEnglish = templateInfoPojo.getMainbodyPFontTypeEnglish();
        this.mainbodyPFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getMainbodyPFontSz());
        this.mainbodyPInd = StrConverter.indentToReadableString(templateInfoPojo.getMainbodyPInd());
        this.mainbodyPLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getMainbodyPLine());
        this.mainbodyPBold = templateInfoPojo.getMainbodyPBold() == 1 ? "是" : "无";

        /* 结论 */
        this.conclusionFlag = Integer.toString(templateInfoPojo.getConclusionFlag());
        this.conclusionSeq = Integer.toString(templateInfoPojo.getConclusionSeq());
        this.conclusionHFontType = templateInfoPojo.getConclusionHFontType();
        this.conclusionHFontEnglishType = templateInfoPojo.getConclusionHFontEnglishType();
        this.conclusionHFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getConclusionHFontSz());
        this.conclusionHJc = StrConverter.jcToReadableString(templateInfoPojo.getConclusionHJc());
        this.conclusionHBold = templateInfoPojo.getConclusionHBold() == 1 ? "是" : "无";
        this.conclusionHAfterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getConclusionHAfterline());
        this.conclusionHBeforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getConclusionHBeforeline());
        this.conclusionHLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getConclusionHLine());
        this.conclusionPFontType = templateInfoPojo.getConclusionPFontType();
        this.conclusionPFontTypeEnglish = templateInfoPojo.getConclusionPFontTypeEnglish();
        this.conclusionPFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getConclusionPFontSz());
        this.conclusionPInd = StrConverter.indentToReadableString(templateInfoPojo.getConclusionPInd());
        this.conclusionPLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getConclusionPLine());
        this.conclusionPBold = templateInfoPojo.getConclusionPBold() == 1 ? "是" : "无";

        /* 致谢 */
        this.thanksFlag = Integer.toString(templateInfoPojo.getThanksFlag());
        this.thanksSeq = Integer.toString(templateInfoPojo.getThanksSeq());
        this.thanksHFontType = templateInfoPojo.getThanksHFontType();
        this.thanksHFontEnglishType = templateInfoPojo.getThanksHFontEnglishType();
        this.thanksHFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getThanksHFontSz());
        this.thanksHJc = StrConverter.jcToReadableString(templateInfoPojo.getThanksHJc());
        this.thanksHBold = templateInfoPojo.getThanksHBold() == 1 ? "是" : "无";
        this.thanksHAfterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getThanksHAfterline());
        this.thanksHBeforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getThanksHBeforeline());
        this.thanksHLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getThanksHLine());
        this.thanksPFontType = templateInfoPojo.getThanksPFontType();
        this.thanksPFontTypeEnglish = templateInfoPojo.getThanksPFontTypeEnglish();
        this.thanksPFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getThanksPFontSz());
        this.thanksPInd = StrConverter.indentToReadableString(templateInfoPojo.getThanksPInd());
        this.thanksPLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getThanksPLine());
        this.thanksPBold = templateInfoPojo.getThanksPBold() == 1 ? "是" : "无";

        /* 参考文献 */
        this.referencesFlag = Integer.toString(templateInfoPojo.getReferencesFlag());
        this.referencesSeq = Integer.toString(templateInfoPojo.getReferencesSeq());
        this.referencesHFontType = templateInfoPojo.getReferencesHFontType();
        this.referencesHFontEnglishType = templateInfoPojo.getReferencesHFontEnglishType();
        this.referencesHFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getReferencesHFontSz());
        this.referencesHJc = StrConverter.jcToReadableString(templateInfoPojo.getReferencesHJc());
        this.referencesHBold = templateInfoPojo.getReferencesHBold() == 1 ? "是" : "无";
        this.referencesHAfterline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getReferencesHAfterline());
        this.referencesHBeforeline = StrConverter.beforeAfterLineToReadableString(templateInfoPojo.getReferencesHBeforeline());
        this.referencesHLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getReferencesHLine());
        this.referencesPFontType = templateInfoPojo.getReferencesPFontType();
        this.referencesPFontTypeEnglish = templateInfoPojo.getReferencesPFontTypeEnglish();
        this.referencesPFontSz = StrConverter.fontSizeToReadableString(templateInfoPojo.getReferencesPFontSz());
        this.referencesPInd = StrConverter.indentToReadableString(templateInfoPojo.getReferencesPInd());
        this.referencesPLine = StrConverter.lineSpacingToReadableString(templateInfoPojo.getReferencesPLine());
        this.referencesPBold = templateInfoPojo.getReferencesPBold() == 1 ? "是" : "否";


    }

    /**
     * 绑定的模板id
     */
    private String templateId;

    /**
     * “诚信声明”标志位，0无，1有
     */
    private String sohFlag;

    /**
     * 次序，默认为1
     */
    private String sohSeq;

    /**
     * 诚信说明内容
     */
    private String sohContent;

    /**
     * "诚信声明"标题字体类别
     */
    private String sohHFontType;

    /**
     * 西文字体类别
     */
    private String sohHFontEnglishType;

    /**
     * 字体大小
     */
    private String sohHFontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String sohHJc;

    /**
     * 字体加粗，0无，1有
     */
    private String sohHBold;

    /**
     * 段后间隔，100为1行
     */
    private String sohHAfterline;

    /**
     * 段前间隔，100为1行
     */
    private String sohHBeforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String sohHLine;

    /**
     * "诚信声明"段落字体类别
     */
    private String sohPFontType;

    /**
     * 西文字体类别
     */
    private String sohPFontTypeEnglish;

    /**
     * 字体大小
     */
    private String sohPFontSz;

    /**
     * 首行缩进
     */
    private String sohPInd;

    /**
     * 段间距
     */
    private String sohPLine;

    /**
     * 段落正文是否加粗，0无1有
     */
    private String sohPBold;

    /**
     * 中文摘要是否有
     */
    private String aocFlag;

    /**
     * 中文摘要次序
     */
    private String aocSeq;

    /**
     * 前置词字体
     */
    private String aocPrefixfont;

    /**
     * 前置词是否加粗
     */
    private String aocIsprefixbold;

    /**
     * 摘要的推荐最大字数
     */
    private String aocRecommendedmaxcontentlength;

    /**
     * 摘要的推荐最小字数
     */
    private String aocRecommendedmincontentlength;

    /**
     * 关键词的推荐最大个数
     */
    private String aocRecommendedmaxkeywordscount;

    /**
     * 关键词推荐的最小个数
     */
    private String aocRecommendedminkeywordscount;

    /**
     * 标题字体类别
     */
    private String aocHFontType;

    /**
     * 西文字体类别
     */
    private String aocHFontEnglishType;

    /**
     * 字体大小
     */
    private String aocHFontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String aocHJc;

    /**
     * 字体加粗，0无，1有
     */
    private String aocHBold;

    /**
     * 段后间隔，100为1行
     */
    private String aocHAfterline;

    /**
     * 段前间隔，100为1行
     */
    private String aocHBeforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String aocHLine;

    /**
     * 段落字体类别
     */
    private String aocPFontType;

    /**
     * 西文字体类别
     */
    private String aocPFontTypeEnglish;

    /**
     * 字体大小
     */
    private String aocPFontSz;

    /**
     * 首行缩进
     */
    private String aocPInd;

    /**
     * 段间距
     */
    private String aocPLine;

    /**
     * 段落正文是否加粗，0无1有
     */
    private String aocPBold;

    /**
     * 英文摘要是否有
     */
    private String aoeFlag;

    /**
     * 英文摘要次序
     */
    private String aoeSeq;

    /**
     * 前置词字体
     */
    private String aoePrefixfont;

    /**
     * 前置词是否加粗
     */
    private String aoeIsprefixbold;

    /**
     * 摘要的推荐最大字数
     */
    private String aoeRecommendedmaxcontentlength;

    /**
     * 摘要的推荐最小字数
     */
    private String aoeRecommendedmincontentlength;

    /**
     * 关键词的推荐最大个数
     */
    private String aoeRecommendedmaxkeywordscount;

    /**
     * 关键词推荐的最小个数
     */
    private String aoeRecommendedminkeywordscount;

    /**
     * 标题字体类别
     */
    private String aoeHFontType;

    /**
     * 西文字体类别
     */
    private String aoeHFontEnglishType;

    /**
     * 字体大小
     */
    private String aoeHFontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String aoeHJc;

    /**
     * 字体加粗，0无，1有
     */
    private String aoeHBold;

    /**
     * 段后间隔，100为1行
     */
    private String aoeHAfterline;

    /**
     * 段前间隔，100为1行
     */
    private String aoeHBeforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String aoeHLine;

    /**
     * 段落字体类别
     */
    private String aoePFontType;

    /**
     * 西文字体类别
     */
    private String aoePFontTypeEnglish;

    /**
     * 字体大小
     */
    private String aoePFontSz;

    /**
     * 首行缩进
     */
    private String aoePInd;

    /**
     * 段间距
     */
    private String aoePLine;

    /**
     * 段落正文是否加粗，0无1有
     */
    private String aoePBold;

    /**
     * 目录是否有
     */
    private String catalogueFlag;

    /**
     * 目录次序
     */
    private String catalogueSeq;

    /**
     * 标题字体类别
     */
    private String catalogueHFontType;

    /**
     * 西文字体类别
     */
    private String catalogueHFontEnglishType;

    /**
     * 字体大小
     */
    private String catalogueHFontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String catalogueHJc;

    /**
     * 字体加粗，0无，1有
     */
    private String catalogueHBold;

    /**
     * 段后间隔，100为1行
     */
    private String catalogueHAfterline;

    /**
     * 段前间隔，100为1行
     */
    private String catalogueHBeforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String catalogueHLine;

    /**
     * 绪论和正文是否有
     */
    private String mainbodyFlag;

    /**
     * 绪论和正文次序
     */
    private String mainbodySeq;

    /**
     * 标题字体类别
     */
    private String mainbodyH1FontType;

    /**
     * 西文字体类别
     */
    private String mainbodyH1FontEnglishType;

    /**
     * 字体大小
     */
    private String mainbodyH1FontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String mainbodyH1Jc;

    /**
     * 字体加粗，0无，1有
     */
    private String mainbodyH1Bold;

    /**
     * 段后间隔，100为1行
     */
    private String mainbodyH1Afterline;

    /**
     * 段前间隔，100为1行
     */
    private String mainbodyH1Beforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String mainbodyH1Line;

    /**
     * 标题字体类别
     */
    private String mainbodyH2FontType;

    /**
     * 西文字体类别
     */
    private String mainbodyH2FontEnglishType;

    /**
     * 字体大小
     */
    private String mainbodyH2FontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String mainbodyH2Jc;

    /**
     * 字体加粗，0无，1有
     */
    private String mainbodyH2Bold;

    /**
     * 段后间隔，100为1行
     */
    private String mainbodyH2Afterline;

    /**
     * 段前间隔，100为1行
     */
    private String mainbodyH2Beforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String mainbodyH2Line;

    /**
     * 标题字体类别
     */
    private String mainbodyH3FontType;

    /**
     * 西文字体类别
     */
    private String mainbodyH3FontEnglishType;

    /**
     * 字体大小
     */
    private String mainbodyH3FontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String mainbodyH3Jc;

    /**
     * 字体加粗，0无，1有
     */
    private String mainbodyH3Bold;

    /**
     * 段后间隔，100为1行
     */
    private String mainbodyH3Afterline;

    /**
     * 段前间隔，100为1行
     */
    private String mainbodyH3Beforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String mainbodyH3Line;

    /**
     * 段落字体类别
     */
    private String mainbodyPFontType;

    /**
     * 西文字体类别
     */
    private String mainbodyPFontTypeEnglish;

    /**
     * 字体大小
     */
    private String mainbodyPFontSz;

    /**
     * 首行缩进
     */
    private String mainbodyPInd;

    /**
     * 段间距
     */
    private String mainbodyPLine;

    /**
     * 段落正文是否加粗，0无1有
     */
    private String mainbodyPBold;

    /**
     * 结论是否有
     */
    private String conclusionFlag;

    /**
     * 结论次序
     */
    private String conclusionSeq;

    /**
     * 推荐的结论最大字数
     */
    private String conclusionRecommendedmaxcontentlength;

    /**
     * 标题字体类别
     */
    private String conclusionHFontType;

    /**
     * 西文字体类别
     */
    private String conclusionHFontEnglishType;

    /**
     * 字体大小
     */
    private String conclusionHFontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String conclusionHJc;

    /**
     * 字体加粗，0无，1有
     */
    private String conclusionHBold;

    /**
     * 段后间隔，100为1行
     */
    private String conclusionHAfterline;

    /**
     * 段前间隔，100为1行
     */
    private String conclusionHBeforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String conclusionHLine;

    /**
     * 段落字体类别
     */
    private String conclusionPFontType;

    /**
     * 西文字体类别
     */
    private String conclusionPFontTypeEnglish;

    /**
     * 字体大小
     */
    private String conclusionPFontSz;

    /**
     * 首行缩进
     */
    private String conclusionPInd;

    /**
     * 段间距
     */
    private String conclusionPLine;

    /**
     * 段落正文是否加粗，0无1有
     */
    private String conclusionPBold;

    /**
     * 致谢是否有
     */
    private String thanksFlag;

    /**
     * 致谢次序
     */
    private String thanksSeq;

    /**
     * 推荐的致谢最大字数
     */
    private String thanksRecommendedmaxcontentlength;

    /**
     * 标题字体类别
     */
    private String thanksHFontType;

    /**
     * 西文字体类别
     */
    private String thanksHFontEnglishType;

    /**
     * 字体大小
     */
    private String thanksHFontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String thanksHJc;

    /**
     * 字体加粗，0无，1有
     */
    private String thanksHBold;

    /**
     * 段后间隔，100为1行
     */
    private String thanksHAfterline;

    /**
     * 段前间隔，100为1行
     */
    private String thanksHBeforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String thanksHLine;

    /**
     * 段落字体类别
     */
    private String thanksPFontType;

    /**
     * 西文字体类别
     */
    private String thanksPFontTypeEnglish;

    /**
     * 字体大小
     */
    private String thanksPFontSz;

    /**
     * 首行缩进
     */
    private String thanksPInd;

    /**
     * 段间距
     */
    private String thanksPLine;

    /**
     * 段落正文是否加粗，0无1有
     */
    private String thanksPBold;

    /**
     * 参考文献是否有
     */
    private String referencesFlag;

    /**
     * 参考文献次序
     */
    private String referencesSeq;

    /**
     * 推荐的参考文献最少条数
     */
    private String referencesRecommendedmincount;

    /**
     * 标题字体类别
     */
    private String referencesHFontType;

    /**
     * 西文字体类别
     */
    private String referencesHFontEnglishType;

    /**
     * 字体大小
     */
    private String referencesHFontSz;

    /**
     * 对齐方式left，right，center，"jc" 表示 "justification"
     */
    private String referencesHJc;

    /**
     * 字体加粗，0无，1有
     */
    private String referencesHBold;

    /**
     * 段后间隔，100为1行
     */
    private String referencesHAfterline;

    /**
     * 段前间隔，100为1行
     */
    private String referencesHBeforeline;

    /**
     * 段间距，240为1行，lineRule需要设置为auto
     */
    private String referencesHLine;

    /**
     * 段落字体类别
     */
    private String referencesPFontType;

    /**
     * 西文字体类别
     */
    private String referencesPFontTypeEnglish;

    /**
     * 字体大小
     */
    private String referencesPFontSz;

    /**
     * 首行缩进
     */
    private String referencesPInd;

    /**
     * 段间距
     */
    private String referencesPLine;

    /**
     * 段落正文是否加粗，0无1有
     */
    private String referencesPBold;

    /**
     * 图表注释字体
     */
    private String captionFontname;

    /**
     * 图表注释英文字体
     */
    private String captionFontenglishname;

    /**
     * 图表注释字号
     */
    private String captionFontsize;

    /**
     * 图片最小宽度
     */
    private String captionPicszwidthmin;

    /**
     * 图片最大宽度
     */
    private String captionPicszwidthmax;

    /**
     * 图片最小高度
     */
    private String captionPicszheightmin;

    /**
     * 图片最大高度
     */
    private String captionPicszheightmax;

    /**
     * 推荐的图表数量，这个数量是最小值，可以多于这个数量。
     */
    private String captionRecommendnum;

    /**
     * 页眉内容1
     */
    private String pagesettingHeadercontent1;

    /**
     * 页眉内容2
     */
    private String pagesettingHeadercontent2;

    /**
     * 页上边距
     */
    private String pagesettingTopmargin;

    /**
     * 页下边距
     */
    private String pagesettingBottommargin;

    /**
     * 页左边距
     */
    private String pagesettingLeftmargin;

    /**
     * 页右边距
     */
    private String pagesettingRightmargin;

    /**
     * 页眉边距
     */
    private String pagesettingHeadermargin;

    /**
     * 页脚边距
     */
    private String pagesettingFootermargin;

    /**
     * 奇偶页设置，0无，1有
     */
    private String pagesettingOddevenpage;

}









