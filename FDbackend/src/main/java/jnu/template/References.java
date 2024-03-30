package jnu.template;

/**
 * 论文"参考文献"的模板规范，会提供默认要求。
 * 包括参考文献的序号规范要求，参考文献的段落规范要求。
 * */
public class References {
    public static final String contentName = "参考文献"; // 标识本类描述的论文内容的名称。
    public static final String markWord = "参考文献"; // 参考文献的标记词，用于在论文中定位参考文献的位置。

    private HeadingRep headingRep; // 参考文献的标题规范要求
    private ParagraphRep paragraphRep; // 参考文献的段落规范要求
    private int recommendedMinCount; // 参考文献推荐的最少条数。

    /**
     * 工厂方法，提供默认的参考文献的规范要求。
     * */
    public static References getDefaultInstance() {
        return new References(
                8,
                new HeadingRep(
                        "宋体",
                        "Times New Roman",
                        "36",
                        "center",
                        "480",
                        "50",
                        "50",
                        1),
                new ParagraphRep(
                        "宋体",
                        "Times New Roman",
                        "28",
                        "0",
                        "360",
                        0)
        );
    }

    public References(int recommendedMinCount, HeadingRep headingRep, ParagraphRep paragraphRep) {
        this.recommendedMinCount = recommendedMinCount;
        this.headingRep = headingRep;
        this.paragraphRep = paragraphRep;
    }

    public HeadingRep getHeadingRep() {
        return headingRep;
    }

    public void setHeadingRep(HeadingRep headingRep) {
        this.headingRep = headingRep;
    }

    public ParagraphRep getParagraphRep() {
        return paragraphRep;
    }

    public void setParagraphRep(ParagraphRep paragraphRep) {
        this.paragraphRep = paragraphRep;
    }

    public int getRecommendedMinCount() {
        return recommendedMinCount;
    }

    public void setRecommendedMinCount(int recommendedMinCount) {
        this.recommendedMinCount = recommendedMinCount;
    }
}
