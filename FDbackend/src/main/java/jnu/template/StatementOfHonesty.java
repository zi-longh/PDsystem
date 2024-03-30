package jnu.template;

/**
 * 论文"诚信声明"的模板规范，会提供默认要求。
 */
public class StatementOfHonesty {
    public static final String contentName = "诚信声明"; // 标识本类描述的论文内容的名称。
    public static final String markWord = "诚信声明"; // 诚信说明的标记词，用于在论文中定位诚信说明的位置。
    public static final String authorSign = "毕业论文作者签名：";
    public static final String dateSign = "签名日期：";
    private Integer sequence; // 定义在论文中出现的次序。1表示在论文的第一部分，2表示在论文的第二部分，以此类推。
    private String content; // 诚信说明的内容。

    private HeadingRep headingRep; // 诚信说明的标题规范要求。
    private ParagraphRep paragraphRep; // 诚信说明的段落规范要求。

    /**
     * 工厂方法，提供默认的诚信说明的内容。
     * */
    public static StatementOfHonesty getDefaultInstance() {
        return new StatementOfHonesty(
                1,
                "我声明，所呈交的毕业论文是本人在老师指导下进行的研究工作及取得的研究成果。据我查证，除了文中特别加以标注和致谢的地方外，论文中不包含其他人已经发表或撰写过的研究成果，也不包含为获得其他教育机构的学位或证书而使用过的材料。我承诺，论文中的所有内容均真实、可信。",
                new HeadingRep(
                        "楷体_GB2312",
                        "Times New Roman",
                        "44",
                        "center",
                        "240",
                        "100",
                        "200",
                        1),
                new ParagraphRep(
                        "楷体_GB2312",
                        "Times New Roman",
                        "30",
                        "200",
                        "360",
                        0)
        );
    }

    // 构造函数
    public StatementOfHonesty(Integer sequence, String content, HeadingRep headingRep, ParagraphRep paragraphRep) {
        this.sequence = sequence;
        this.content = content;
        this.headingRep = headingRep;
        this.paragraphRep = paragraphRep;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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


}
