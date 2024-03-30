package jnu.template;

/**
 * 论文页眉、页脚、页边距等规范要求。
 * 页码位置，奇偶页
 * */
public class PageSetting {

    private String headerContent1; // 页眉内容
    private String headerContent2; // 页眉内容2，在数据库中默认可以输入为空 或 defaultPaperName 表示用论文标题代替
    private String topMargin; // 页上边距, 1cm = 567
    private String bottomMargin; // 页下边距
    private String leftMargin; // 页左边距
    private String rightMargin; // 页右边距
    private int oddEvenPage; // 奇偶页，true表示奇偶页不同，false表示奇偶页相同。奇数页的页码位置在右，偶数页的页码位置在左。
    private String headerMargin; // 页眉边距
    private String footerMargin; // 页脚边距


    /**
     * 工厂方法，提供默认的页眉、页脚、页边距等规范要求。
     * */
    public static PageSetting getDefaultInstance() {
        return new PageSetting(
                "暨南大学本科毕业设计（论文）",
                "基于OOXML标准的论文检测与校正系统",
                "1418",
                "1418",
                "1701",
                "1134",
                1,
                "851",
                "992"
        );
    }

    public PageSetting(String headerContent1, String headerContent2, String topMargin, String bottomMargin, String leftMargin, String rightMargin, int oddEvenPage, String headerMargin, String footerMargin) {
        this.headerContent1 = headerContent1;
        this.headerContent2 = headerContent2;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.oddEvenPage = oddEvenPage;
        this.headerMargin = headerMargin;
        this.footerMargin = footerMargin;
    }

    public String getHeaderContent1() {
        return headerContent1;
    }

    public void setHeaderContent1(String headerContent1) {
        this.headerContent1 = headerContent1;
    }

    public String getHeaderContent2() {
        return headerContent2;
    }

    public void setHeaderContent2(String headerContent2) {
        this.headerContent2 = headerContent2;
    }


    public String getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(String topMargin) {
        this.topMargin = topMargin;
    }

    public String getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(String bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public String getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(String leftMargin) {
        this.leftMargin = leftMargin;
    }

    public String getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(String rightMargin) {
        this.rightMargin = rightMargin;
    }

    public boolean isOddEvenPage() {
        return oddEvenPage == 1;
    }

    public void setOddEvenPage(int oddEvenPage) {
        this.oddEvenPage = oddEvenPage;
    }

    public String getHeaderMargin() {
        return headerMargin;
    }

    public void setHeaderMargin(String headerMargin) {
        this.headerMargin = headerMargin;
    }

    public String getFooterMargin() {
        return footerMargin;
    }

    public void setFooterMargin(String footerMargin) {
        this.footerMargin = footerMargin;
    }
}
