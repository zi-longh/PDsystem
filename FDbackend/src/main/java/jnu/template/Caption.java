package jnu.template;

/**
 * 图注表注
 * 同时包括图片大小的要求
 * 关于表注和图注的检测，是在检测正文内容时进行的。
 * */
public class Caption {
    private String fontName; // 字体名称
    private String fontEnglishName; // 字体英文名称
    private String fontSize; // 字体大小
    private double picSzWidthMin; // 图片宽度最小值，以cm为单位
    private double picSzWidthMax; // 图片宽度最大值
    private double picSzHeightMin; // 图片高度最小值
    private double picSzHeightMax; // 图片高度最大值
    private int recommendNum; // 推荐的图注表注的数量，这个数量是最小值，可以多于这个数量。

    /* a4纸：297 mm × 210 mm*/


    /**
     * 工厂方法，提供默认的图注表注规范要求。
     * */
    public static Caption getDefaultInstance() {
        return new Caption(
                "宋体",
                "Times New Roman",
                "21",
                1.0,
                17.0,
                1.0,
                26.0,
                2
        );
    }

    public Caption(String fontName, String fontEnglishName, String fontSize, double picSzWidthMin, double picSzWidthMax, double picSzHeightMin, double picSzHeightMax, int recommendNum) {
        this.fontName = fontName;
        this.fontEnglishName = fontEnglishName;
        this.fontSize = fontSize;
        this.picSzWidthMin = picSzWidthMin;
        this.picSzWidthMax = picSzWidthMax;
        this.picSzHeightMin = picSzHeightMin;
        this.picSzHeightMax = picSzHeightMax;
        this.recommendNum = recommendNum;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public double getPicSzWidthMin() {
        return picSzWidthMin;
    }

    public void setPicSzWidthMin(double picSzWidthMin) {
        this.picSzWidthMin = picSzWidthMin;
    }

    public double getPicSzWidthMax() {
        return picSzWidthMax;
    }

    public void setPicSzWidthMax(double picSzWidthMax) {
        this.picSzWidthMax = picSzWidthMax;
    }

    public double getPicSzHeightMin() {
        return picSzHeightMin;
    }

    public void setPicSzHeightMin(double picSzHeightMin) {
        this.picSzHeightMin = picSzHeightMin;
    }

    public double getPicSzHeightMax() {
        return picSzHeightMax;
    }

    public void setPicSzHeightMax(double picSzHeightMax) {
        this.picSzHeightMax = picSzHeightMax;
    }

    public int getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(int recommendNum) {
        this.recommendNum = recommendNum;
    }

    public String getFontEnglishName() {
        return fontEnglishName;
    }

    public void setFontEnglishName(String fontEnglishName) {
        this.fontEnglishName = fontEnglishName;
    }
}
