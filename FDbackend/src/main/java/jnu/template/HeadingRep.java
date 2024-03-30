package jnu.template;

/**
 * 描述各种标题的规范要求。
 * */
public class HeadingRep {
    private String fontType; // 标题的字体
    private String fontEnglishType; // 标题的英文字体
    private String fontSize; // 标题的字号
    private String jc; // 标题的对齐方式
    private String line; // 标题的行距。 240表示1倍行距。
    private String beforeLine; // 标题的段前距。100表示1倍行距。
    private String afterLine; // 标题的段后距。100表示1倍行距。
    private Integer bold; // 标题是否加粗, 1表示加粗, 0表示不加粗

    /**
     * 工厂方法，提供默认的标题规范要求。
     * */
    public static HeadingRep getDefaultInstance() {
        String fontType = "宋体";
        String fontEnglishType = "Times New Roman";
        String fontSize = "小三";
        String jc = "center";
        String line = "240";
        String beforeLine = "100";
        String afterLine = "100";
        Integer bold = 1;
        return new HeadingRep(fontType, fontEnglishType, fontSize, jc, line, beforeLine, afterLine, bold);
    }

    public HeadingRep(String fontType, String fontEnglishType, String fontSize, String jc, String line, String beforeLine, String afterLine, Integer bold) {
        this.fontType = fontType;
        this.fontEnglishType = fontEnglishType;
        this.fontSize = fontSize;
        this.jc = jc;
        this.line = line;
        this.beforeLine = beforeLine;
        this.afterLine = afterLine;
        this.bold = bold;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getBeforeLine() {
        return beforeLine;
    }

    public void setBeforeLine(String beforeLine) {
        this.beforeLine = beforeLine;
    }

    public String getAfterLine() {
        return afterLine;
    }

    public void setAfterLine(String afterLine) {
        this.afterLine = afterLine;
    }

    public Integer getBold() {
        return bold;
    }

    public void setBold(Integer bold) {
        this.bold = bold;
    }

    public String getFontEnglishType() {
        return fontEnglishType;
    }

    public void setFontEnglishType(String fontEnglishType) {
        this.fontEnglishType = fontEnglishType;
    }

}
