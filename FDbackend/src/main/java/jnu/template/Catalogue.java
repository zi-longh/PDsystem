package jnu.template;

/**
 * 课程目录的模板规范，会提供默认要求。
 * 包括目录的标题规范要求，目录的段落规范要求。
 * 这个一般会最后检测，因为目录的生成需要依赖于其他部分的内容。
 * */
public class Catalogue {
    public static final String contentName = "目录"; // 标识本类描述的论文内容的名称。
    public static final String markWord = "目录"; // 目录的标记词，用于在论文中定位目录的位置。
    /* 开始位置需要靠论文标题来定位 */

    private HeadingRep headingRep; // 标题的规范要求

    public static Catalogue getDefaultCatalogue() {
        return new Catalogue(
                new HeadingRep(
                        "宋体",
                        "Times New Roman",
                        "36",
                        "center",
                        "480",
                        "50",
                        "50",
                        1)
        );
    }

    public Catalogue(HeadingRep headingRep) {
        this.headingRep = headingRep;
    }

    public HeadingRep getHeadingRep() {
        return headingRep;
    }

}
