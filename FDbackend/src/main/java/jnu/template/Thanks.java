package jnu.template;

/**
 * 论文"致谢"的模板规范，会提供默认要求。
 * 包括致谢的标题规范要求，致谢的段落规范要求。
 */
public class Thanks {
    public static final String contentName = "致谢"; // 标识本类描述的论文内容的名称。
    public static final String markWord = "致谢"; // 致谢的标记词，用于在论文中定位致谢的开始位置。
    /* 致谢的结束位置依赖搜寻到的下一个location标签 */
    private HeadingRep headingRep; // 标题规范要求（致谢上方的标题规范要求）
    private ParagraphRep paragraphRep; // 致谢的段落规范要求
    private int recommendedMaxContentLength; // 致谢的推荐最大字数。为0表示不限制。

    /**
     * 工厂方法，提供默认的致谢的规范要求。
     * */
    public static Thanks getDefaultInstance() {
        return new Thanks(
                630,
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
                        "200",
                        "240",
                        0)
        );
    }

    public Thanks(int recommendedMaxContentLength, HeadingRep headingRep, ParagraphRep paragraphRep) {
        this.recommendedMaxContentLength = recommendedMaxContentLength;
        this.headingRep = headingRep;
        this.paragraphRep = paragraphRep;
    }

    public Thanks(String recommendedMaxContentLength, HeadingRep headingRep, ParagraphRep paragraphRep) {
        if (recommendedMaxContentLength.equals("不限制")) {
            this.recommendedMaxContentLength = 0;
        } else if (recommendedMaxContentLength.equals("一页")) {
            this.recommendedMaxContentLength = 630;
        } else if (recommendedMaxContentLength.equals("两页")) {
            this.recommendedMaxContentLength = 1260;
        } else {
            // 无效的参数
            this.recommendedMaxContentLength = 0;
            throw new IllegalArgumentException("Thanks: 无效的参数");
        }
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

    public int getRecommendedMaxContentLength() {
        return recommendedMaxContentLength;
    }

    public void setRecommendedMaxContentLength(int recommendedMaxContentLength) {
        this.recommendedMaxContentLength = recommendedMaxContentLength;
    }
}
