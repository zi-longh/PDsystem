package jnu.template;

/**
 * 论文"中文摘要"的模板规范，会提供默认要求。
 * 包括摘要上方的标题规范要求，摘要的段落规范要求，摘要下方的关键词规范要求。
 * */
public class AbstractOfChinese {
    public static final String contentName = "中文摘要"; // 标识本类描述的论文内容的名称。
    public static final String markWord1 = "[摘 要]"; // 标记摘要位置
    public static final String markWord2 = "[关键词]"; // 标记关键词的位置。
    private String prefixFont; // 前置词的字体，即“摘要”和“关键词”两个字的字体。
    private int isPrefixBold; // 前置词是否加粗。
    private int recommendedMaxContentLength; // 摘要的推荐最大字数。

    private int recommendedMinContentLength; // 摘要的推荐最小字数。
    private int recommendedMaxKeywordsCount; // 关键词的推荐最大个数。

    private int recommendedMinKeywordsCount; // 关键词的推荐最小个数。
    private HeadingRep headingRep; // 标题规范要求（中文摘要上方的标题规范要求）
    private ParagraphRep paragraphRep; // 摘要的段落规范要求

    /**
     * 工厂方法，提供默认的中文摘要的规范要求。
     * */
    public static AbstractOfChinese getDefaultInstance() {
        return new AbstractOfChinese(
                "宋体",
                1,
                300,
                5,
                new HeadingRep(
                        "宋体",
                        "Times New Roman",
                        "36",
                        "center",
                        "240",
                        "100",
                        "200",
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

    public AbstractOfChinese (String prefixFont, int isPrefixBold, int recommendedMaxContentLength, int recommendedMaxKeywordsCount, HeadingRep headingRep, ParagraphRep paragraphRep) {
        this.prefixFont = prefixFont;
        this.isPrefixBold = isPrefixBold;
        this.recommendedMaxContentLength = recommendedMaxContentLength;
        this.recommendedMaxKeywordsCount = recommendedMaxKeywordsCount;
        this.headingRep = headingRep;
        this.paragraphRep = paragraphRep;
    }

    public String getPrefixFont() {
        return prefixFont;
    }

    public void setPrefixFont(String prefixFont) {
        this.prefixFont = prefixFont;
    }

    public boolean isPrefixBold() {
        return isPrefixBold == 1;
    }

    public void setPrefixBold(int prefixBold) {
        isPrefixBold = prefixBold;
    }

    public int getRecommendedMaxContentLength() {
        return recommendedMaxContentLength;
    }

    public void setRecommendedMaxContentLength(int recommendedMaxContentLength) {
        this.recommendedMaxContentLength = recommendedMaxContentLength;
    }

    public int getRecommendedMaxKeywordsCount() {
        return recommendedMaxKeywordsCount;
    }

    public void setRecommendedMaxKeywordsCount(int recommendedMaxKeywordsCount) {
        this.recommendedMaxKeywordsCount = recommendedMaxKeywordsCount;
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

    public int getIsPrefixBold() {
        return isPrefixBold;
    }

    public void setIsPrefixBold(int isPrefixBold) {
        this.isPrefixBold = isPrefixBold;
    }

    public int getRecommendedMinContentLength() {
        return recommendedMinContentLength;
    }

    public void setRecommendedMinContentLength(int recommendedMinContentLength) {
        this.recommendedMinContentLength = recommendedMinContentLength;
    }

    public int getRecommendedMinKeywordsCount() {
        return recommendedMinKeywordsCount;
    }

    public void setRecommendedMinKeywordsCount(int recommendedMinKeywordsCount) {
        this.recommendedMinKeywordsCount = recommendedMinKeywordsCount;
    }
}
