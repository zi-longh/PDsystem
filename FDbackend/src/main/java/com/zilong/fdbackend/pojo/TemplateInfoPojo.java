package com.zilong.fdbackend.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zilong
 * @since 2024-03-29
 */
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("template_info")
public class TemplateInfoPojo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 绑定的模板id
     */
    @TableId(value = "template_id")
    private String templateId;

    /**
     * “诚信声明”标志位，0无，1有
     */
    private Integer sohFlag;

    /**
     * 次序，默认为1
     */
    private Integer sohSeq;

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
    private Integer sohHBold;

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
    private Integer sohPBold;

    /**
     * 中文摘要是否有
     */
    private Integer aocFlag;

    /**
     * 中文摘要次序
     */
    private Integer aocSeq;

    /**
     * 前置词字体
     */
    private String aocPrefixfont;

    /**
     * 前置词是否加粗
     */
    private Integer aocIsprefixbold;

    /**
     * 摘要的推荐最大字数
     */
    private Integer aocRecommendedmaxcontentlength;

    /**
     * 摘要的推荐最小字数
     */
    private Integer aocRecommendedmincontentlength;

    /**
     * 关键词的推荐最大个数
     */
    private Integer aocRecommendedmaxkeywordscount;

    /**
     * 关键词推荐的最小个数
     */
    private Integer aocRecommendedminkeywordscount;

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
    private Integer aocHBold;

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
    private Integer aocPBold;

    /**
     * 英文摘要是否有
     */
    private Integer aoeFlag;

    /**
     * 英文摘要次序
     */
    private Integer aoeSeq;

    /**
     * 前置词字体
     */
    private String aoePrefixfont;

    /**
     * 前置词是否加粗
     */
    private Integer aoeIsprefixbold;

    /**
     * 摘要的推荐最大字数
     */
    private Integer aoeRecommendedmaxcontentlength;

    /**
     * 摘要的推荐最小字数
     */
    private Integer aoeRecommendedmincontentlength;

    /**
     * 关键词的推荐最大个数
     */
    private Integer aoeRecommendedmaxkeywordscount;

    /**
     * 关键词推荐的最小个数
     */
    private Integer aoeRecommendedminkeywordscount;

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
    private Integer aoeHBold;

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
    private Integer aoePBold;

    /**
     * 目录是否有
     */
    private Integer catalogueFlag;

    /**
     * 目录次序
     */
    private Integer catalogueSeq;

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
    private Integer catalogueHBold;

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
    private Integer mainbodyFlag;

    /**
     * 绪论和正文次序
     */
    private Integer mainbodySeq;

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
    private Integer mainbodyH1Bold;

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
    private Integer mainbodyH2Bold;

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
    private Integer mainbodyH3Bold;

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
    private Integer mainbodyPBold;

    /**
     * 结论是否有
     */
    private Integer conclusionFlag;

    /**
     * 结论次序
     */
    private Integer conclusionSeq;

    /**
     * 推荐的结论最大字数
     */
    private Integer conclusionRecommendedmaxcontentlength;

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
    private Integer conclusionHBold;

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
    private Integer conclusionPBold;

    /**
     * 致谢是否有
     */
    private Integer thanksFlag;

    /**
     * 致谢次序
     */
    private Integer thanksSeq;

    /**
     * 推荐的致谢最大字数
     */
    private Integer thanksRecommendedmaxcontentlength;

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
    private Integer thanksHBold;

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
    private Integer thanksPBold;

    /**
     * 参考文献是否有
     */
    private Integer referencesFlag;

    /**
     * 参考文献次序
     */
    private Integer referencesSeq;

    /**
     * 推荐的参考文献最少条数
     */
    private Integer referencesRecommendedmincount;

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
    private Integer referencesHBold;

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
    private Integer referencesPBold;

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
    private Double captionPicszwidthmin;

    /**
     * 图片最大宽度
     */
    private Double captionPicszwidthmax;

    /**
     * 图片最小高度
     */
    private Double captionPicszheightmin;

    /**
     * 图片最大高度
     */
    private Double captionPicszheightmax;

    /**
     * 推荐的图表数量，这个数量是最小值，可以多于这个数量。
     */
    private Integer captionRecommendnum;

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
    private Integer pagesettingOddevenpage;

    @Override
    public String toString() {
        return "TemplateInfo{" +
                "templateId = " + templateId +
                ", sohFlag = " + sohFlag +
                ", sohSeq = " + sohSeq +
                ", sohContent = " + sohContent +
                ", sohHFontType = " + sohHFontType +
                ", sohHFontEnglishType = " + sohHFontEnglishType +
                ", sohHFontSz = " + sohHFontSz +
                ", sohHJc = " + sohHJc +
                ", sohHBold = " + sohHBold +
                ", sohHAfterline = " + sohHAfterline +
                ", sohHBeforeline = " + sohHBeforeline +
                ", sohHLine = " + sohHLine +
                ", sohPFontType = " + sohPFontType +
                ", sohPFontTypeEnglish = " + sohPFontTypeEnglish +
                ", sohPFontSz = " + sohPFontSz +
                ", sohPInd = " + sohPInd +
                ", sohPLine = " + sohPLine +
                ", sohPBold = " + sohPBold +
                ", aocFlag = " + aocFlag +
                ", aocSeq = " + aocSeq +
                ", aocPrefixfont = " + aocPrefixfont +
                ", aocIsprefixbold = " + aocIsprefixbold +
                ", aocRecommendedmaxcontentlength = " + aocRecommendedmaxcontentlength +
                ", aocRecommendedmincontentlength = " + aocRecommendedmincontentlength +
                ", aocRecommendedmaxkeywordscount = " + aocRecommendedmaxkeywordscount +
                ", aocRecommendedminkeywordscount = " + aocRecommendedminkeywordscount +
                ", aocHFontType = " + aocHFontType +
                ", aocHFontEnglishType = " + aocHFontEnglishType +
                ", aocHFontSz = " + aocHFontSz +
                ", aocHJc = " + aocHJc +
                ", aocHBold = " + aocHBold +
                ", aocHAfterline = " + aocHAfterline +
                ", aocHBeforeline = " + aocHBeforeline +
                ", aocHLine = " + aocHLine +
                ", aocPFontType = " + aocPFontType +
                ", aocPFontTypeEnglish = " + aocPFontTypeEnglish +
                ", aocPFontSz = " + aocPFontSz +
                ", aocPInd = " + aocPInd +
                ", aocPLine = " + aocPLine +
                ", aocPBold = " + aocPBold +
                ", aoeFlag = " + aoeFlag +
                ", aoeSeq = " + aoeSeq +
                ", aoePrefixfont = " + aoePrefixfont +
                ", aoeIsprefixbold = " + aoeIsprefixbold +
                ", aoeRecommendedmaxcontentlength = " + aoeRecommendedmaxcontentlength +
                ", aoeRecommendedmincontentlength = " + aoeRecommendedmincontentlength +
                ", aoeRecommendedmaxkeywordscount = " + aoeRecommendedmaxkeywordscount +
                ", aoeRecommendedminkeywordscount = " + aoeRecommendedminkeywordscount +
                ", aoeHFontType = " + aoeHFontType +
                ", aoeHFontEnglishType = " + aoeHFontEnglishType +
                ", aoeHFontSz = " + aoeHFontSz +
                ", aoeHJc = " + aoeHJc +
                ", aoeHBold = " + aoeHBold +
                ", aoeHAfterline = " + aoeHAfterline +
                ", aoeHBeforeline = " + aoeHBeforeline +
                ", aoeHLine = " + aoeHLine +
                ", aoePFontType = " + aoePFontType +
                ", aoePFontTypeEnglish = " + aoePFontTypeEnglish +
                ", aoePFontSz = " + aoePFontSz +
                ", aoePInd = " + aoePInd +
                ", aoePLine = " + aoePLine +
                ", aoePBold = " + aoePBold +
                ", catalogueFlag = " + catalogueFlag +
                ", catalogueSeq = " + catalogueSeq +
                ", catalogueHFontType = " + catalogueHFontType +
                ", catalogueHFontEnglishType = " + catalogueHFontEnglishType +
                ", catalogueHFontSz = " + catalogueHFontSz +
                ", catalogueHJc = " + catalogueHJc +
                ", catalogueHBold = " + catalogueHBold +
                ", catalogueHAfterline = " + catalogueHAfterline +
                ", catalogueHBeforeline = " + catalogueHBeforeline +
                ", catalogueHLine = " + catalogueHLine +
                ", mainbodyFlag = " + mainbodyFlag +
                ", mainbodySeq = " + mainbodySeq +
                ", mainbodyH1FontType = " + mainbodyH1FontType +
                ", mainbodyH1FontEnglishType = " + mainbodyH1FontEnglishType +
                ", mainbodyH1FontSz = " + mainbodyH1FontSz +
                ", mainbodyH1Jc = " + mainbodyH1Jc +
                ", mainbodyH1Bold = " + mainbodyH1Bold +
                ", mainbodyH1Afterline = " + mainbodyH1Afterline +
                ", mainbodyH1Beforeline = " + mainbodyH1Beforeline +
                ", mainbodyH1Line = " + mainbodyH1Line +
                ", mainbodyH2FontType = " + mainbodyH2FontType +
                ", mainbodyH2FontEnglishType = " + mainbodyH2FontEnglishType +
                ", mainbodyH2FontSz = " + mainbodyH2FontSz +
                ", mainbodyH2Jc = " + mainbodyH2Jc +
                ", mainbodyH2Bold = " + mainbodyH2Bold +
                ", mainbodyH2Afterline = " + mainbodyH2Afterline +
                ", mainbodyH2Beforeline = " + mainbodyH2Beforeline +
                ", mainbodyH2Line = " + mainbodyH2Line +
                ", mainbodyH3FontType = " + mainbodyH3FontType +
                ", mainbodyH3FontEnglishType = " + mainbodyH3FontEnglishType +
                ", mainbodyH3FontSz = " + mainbodyH3FontSz +
                ", mainbodyH3Jc = " + mainbodyH3Jc +
                ", mainbodyH3Bold = " + mainbodyH3Bold +
                ", mainbodyH3Afterline = " + mainbodyH3Afterline +
                ", mainbodyH3Beforeline = " + mainbodyH3Beforeline +
                ", mainbodyH3Line = " + mainbodyH3Line +
                ", mainbodyPFontType = " + mainbodyPFontType +
                ", mainbodyPFontTypeEnglish = " + mainbodyPFontTypeEnglish +
                ", mainbodyPFontSz = " + mainbodyPFontSz +
                ", mainbodyPInd = " + mainbodyPInd +
                ", mainbodyPLine = " + mainbodyPLine +
                ", mainbodyPBold = " + mainbodyPBold +
                ", conclusionFlag = " + conclusionFlag +
                ", conclusionSeq = " + conclusionSeq +
                ", conclusionRecommendedmaxcontentlength = " + conclusionRecommendedmaxcontentlength +
                ", conclusionHFontType = " + conclusionHFontType +
                ", conclusionHFontEnglishType = " + conclusionHFontEnglishType +
                ", conclusionHFontSz = " + conclusionHFontSz +
                ", conclusionHJc = " + conclusionHJc +
                ", conclusionHBold = " + conclusionHBold +
                ", conclusionHAfterline = " + conclusionHAfterline +
                ", conclusionHBeforeline = " + conclusionHBeforeline +
                ", conclusionHLine = " + conclusionHLine +
                ", conclusionPFontType = " + conclusionPFontType +
                ", conclusionPFontTypeEnglish = " + conclusionPFontTypeEnglish +
                ", conclusionPFontSz = " + conclusionPFontSz +
                ", conclusionPInd = " + conclusionPInd +
                ", conclusionPLine = " + conclusionPLine +
                ", conclusionPBold = " + conclusionPBold +
                ", thanksFlag = " + thanksFlag +
                ", thanksSeq = " + thanksSeq +
                ", thanksRecommendedmaxcontentlength = " + thanksRecommendedmaxcontentlength +
                ", thanksHFontType = " + thanksHFontType +
                ", thanksHFontEnglishType = " + thanksHFontEnglishType +
                ", thanksHFontSz = " + thanksHFontSz +
                ", thanksHJc = " + thanksHJc +
                ", thanksHBold = " + thanksHBold +
                ", thanksHAfterline = " + thanksHAfterline +
                ", thanksHBeforeline = " + thanksHBeforeline +
                ", thanksHLine = " + thanksHLine +
                ", thanksPFontType = " + thanksPFontType +
                ", thanksPFontTypeEnglish = " + thanksPFontTypeEnglish +
                ", thanksPFontSz = " + thanksPFontSz +
                ", thanksPInd = " + thanksPInd +
                ", thanksPLine = " + thanksPLine +
                ", thanksPBold = " + thanksPBold +
                ", referencesFlag = " + referencesFlag +
                ", referencesSeq = " + referencesSeq +
                ", referencesRecommendedmincount = " + referencesRecommendedmincount +
                ", referencesHFontType = " + referencesHFontType +
                ", referencesHFontEnglishType = " + referencesHFontEnglishType +
                ", referencesHFontSz = " + referencesHFontSz +
                ", referencesHJc = " + referencesHJc +
                ", referencesHBold = " + referencesHBold +
                ", referencesHAfterline = " + referencesHAfterline +
                ", referencesHBeforeline = " + referencesHBeforeline +
                ", referencesHLine = " + referencesHLine +
                ", referencesPFontType = " + referencesPFontType +
                ", referencesPFontTypeEnglish = " + referencesPFontTypeEnglish +
                ", referencesPFontSz = " + referencesPFontSz +
                ", referencesPInd = " + referencesPInd +
                ", referencesPLine = " + referencesPLine +
                ", referencesPBold = " + referencesPBold +
                ", captionFontname = " + captionFontname +
                ", captionFontenglishname = " + captionFontenglishname +
                ", captionFontsize = " + captionFontsize +
                ", captionPicszwidthmin = " + captionPicszwidthmin +
                ", captionPicszwidthmax = " + captionPicszwidthmax +
                ", captionPicszheightmin = " + captionPicszheightmin +
                ", captionPicszheightmax = " + captionPicszheightmax +
                ", captionRecommendnum = " + captionRecommendnum +
                ", pagesettingHeadercontent1 = " + pagesettingHeadercontent1 +
                ", pagesettingHeadercontent2 = " + pagesettingHeadercontent2 +
                ", pagesettingTopmargin = " + pagesettingTopmargin +
                ", pagesettingBottommargin = " + pagesettingBottommargin +
                ", pagesettingLeftmargin = " + pagesettingLeftmargin +
                ", pagesettingRightmargin = " + pagesettingRightmargin +
                ", pagesettingHeadermargin = " + pagesettingHeadermargin +
                ", pagesettingFootermargin = " + pagesettingFootermargin +
                ", pagesettingOddevenpage = " + pagesettingOddevenpage +
                "}";
    }
}
