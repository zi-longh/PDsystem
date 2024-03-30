package jnu.template;

public class ParagraphRep {
    private String fontType; // 段落的字体，中文字体
    private String fontEnglishType; // 段落的字体，英文字体
    private String fontSize; // 段落的字号
    private String indent; // 段落的缩进
    private String line; // 段落的行距。 240表示1倍行距。
    private int bold; // 段落是否加粗, 1表示加粗, 0表示不加粗

    public ParagraphRep(String fontType, String fontEnglishType, String fontSize, String indent, String line, int bold) {
        this.fontType = fontType;
        this.fontEnglishType = fontEnglishType;
        this.fontSize = fontSize;
        this.indent = indent;
        this.line = line;
        this.bold = bold;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public String getFontEnglishType() {
        return fontEnglishType;
    }

    public void setFontEnglishType(String fontEnglishType) {
        this.fontEnglishType = fontEnglishType;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getBold() {
        return bold;
    }

    public void setBold(int bold) {
        this.bold = bold;
    }
}
