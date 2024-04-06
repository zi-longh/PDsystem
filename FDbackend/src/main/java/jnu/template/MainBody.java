package jnu.template;

/**
 * 论文"绪论和正文"的模板规范，会提供默认要求。
 * 包括引言的标题规范要求，引言的段落规范要求。
 * */
public class MainBody {
    public static final String contentName = "绪论和正文"; // 标识本类描述的论文内容的名称。
    public static final String markWord = "绪论"; // 引言的标记词，用于在论文中定位引言的位置。
    // 引言和正文的结束位置依赖搜寻到的下一个location标签

    private HeadingRep headingRepLevel1; // 一级标题规范要求
    private HeadingRep headingRepLevel2; // 二级标题规范要求
    private HeadingRep headingRepLevel3; // 三级标题规范要求
    private ParagraphRep paragraphRep; // 正文规范要求

    

    /**
     * 工厂方法，用于创建一个默认的IntroductionAndMainBody对象。
     * */
    public static MainBody getDefaultInstance() {
        return new MainBody(
                new HeadingRep(
                        "宋体",
                        "Times New Roman",
                        "36",
                        "left",
                        "480",
                        "50",
                        "50",
                        1),
                new HeadingRep(
                        "宋体",
                        "Times New Roman",
                        "28",
                        "left",
                        "360",
                        "30",
                        "30",
                        1),
                new HeadingRep(
                        "宋体",
                        "Times New Roman",
                        "28",
                        "left",
                        "240",
                        "0",
                        "0",
                        1),
                new ParagraphRep(
                        "宋体",
                        "Times New Roman",
                        "28",
                        "200",
                        "240",
                        0
                )
        );

    }

    public MainBody(HeadingRep headingRepLevel1, HeadingRep headingRepLevel2, HeadingRep headingRepLevel3, ParagraphRep paragraphRep) {
        this.headingRepLevel1 = headingRepLevel1;
        this.headingRepLevel2 = headingRepLevel2;
        this.headingRepLevel3 = headingRepLevel3;
        this.paragraphRep = paragraphRep;
    }

    public HeadingRep getHeadingRepLevel1() {
        return headingRepLevel1;
    }

    public void setHeadingRepLevel1(HeadingRep headingRepLevel1) {
        this.headingRepLevel1 = headingRepLevel1;
    }

    public HeadingRep getHeadingRepLevel2() {
        return headingRepLevel2;
    }

    public void setHeadingRepLevel2(HeadingRep headingRepLevel2) {
        this.headingRepLevel2 = headingRepLevel2;
    }

    public HeadingRep getHeadingRepLevel3() {
        return headingRepLevel3;
    }

    public void setHeadingRepLevel3(HeadingRep headingRepLevel3) {
        this.headingRepLevel3 = headingRepLevel3;
    }

    public ParagraphRep getParagraphRep() {
        return paragraphRep;
    }

    public void setParagraphRep(ParagraphRep paragraphRep) {
        this.paragraphRep = paragraphRep;
    }
}
