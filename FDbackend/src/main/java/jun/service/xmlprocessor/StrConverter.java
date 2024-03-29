package jnu.service.xmlprocessor;

public class StrConverter {
    /**
     * 将特定属性值转为容易阅读的字符串。
     * 将控制字体大小的属性值转化为字号。属性值即为实际磅值乘以2。
     * */
    public static String fontSizeToReadableString(String fontSize) {
        String readableString;
        int fontSizeInt = Integer.parseInt(fontSize);
        if (fontSizeInt == 84) {
            // 42磅
            readableString = "“初号”";
        } else if (fontSizeInt == 72) {
            // 36磅
            readableString = "“小初”";
        } else if (fontSizeInt == 52) {
            // 26磅
            readableString = "“一号”";
        } else if (fontSizeInt == 48) {
            // 24磅
            readableString = "“小一”";
        } else if (fontSizeInt == 44) {
            // 22磅
            readableString = "“二号”";
        } else if (fontSizeInt == 36) {
            // 18磅
            readableString = "“小二”";
        } else if (fontSizeInt == 32) {
            // 16磅
            readableString = "“三号”";
        } else if (fontSizeInt == 30) {
            // 15磅
            readableString = "“小三”";
        } else if (fontSizeInt == 28) {
            // 14磅
            readableString = "“四号”";
        } else if (fontSizeInt == 24) {
            // 12磅
            readableString = "“小四”";
        } else if (fontSizeInt == 21) {
            // 10.5磅
            readableString = "“五号”";
        } else if (fontSizeInt == 18) {
            // 9磅
            readableString = "“小五”";
        } else if (fontSizeInt == 15) {
            // 7.5磅
            readableString = "“六号”";
        } else if (fontSizeInt == 13) {
            // 6.5磅
            readableString = "“小六”";
        } else if (fontSizeInt == 11) {
            // 5.5磅
            readableString = "“七号”";
        } else if (fontSizeInt == 10) {
            // 5磅
            readableString = "“八号”";
        } else {
            int fontSizeIntDiv2 = fontSizeInt / 2;
            readableString = "“" + fontSizeIntDiv2 + "磅" + "”";
        }
        return readableString;
    }

    /**
     * 将特定属性值转为容易阅读的字符串。
     * 将控制段落行距的属性值转化为行距。1倍行距值为240，1.5倍行距值为360，2倍行距值为480，2.5倍行距值为600，3倍行距值为720。
     * */
    public static String lineSpacingToReadableString(String lineSpacing) {
        String readableString;
        int lineSpacingInt = Integer.parseInt(lineSpacing);
        if (lineSpacingInt == 240) {
            readableString = "“1倍行距”";
        } else if (lineSpacingInt == 360) {
            readableString = "“1.5倍行距”";
        } else if (lineSpacingInt == 480) {
            readableString = "“2倍行距”";
        } else if (lineSpacingInt == 600) {
            readableString = "“2.5倍行距”";
        } else if (lineSpacingInt == 720) {
            readableString = "“3倍行距”";
        } else {
            double lineSpacingIntDiv240 = lineSpacingInt / 240.0;
            // 保留一位小数
            String lineSpacingIntDiv240Str = String.format("%.1f", lineSpacingIntDiv240);
            readableString = "“" + lineSpacingIntDiv240Str + "倍行距”";
        }
        return readableString;
    }

    /**
     * 将段前距和段后距的属性值转化为容易阅读的字符串。
     * 50为0.5行，100为1行，150为1.5行，200为2行，240为2.5行，300为3行。
     * */
    public static String beforeAfterLineToReadableString(String beforeAfterSpacing) {
        String readableString;
        int beforeAfterSpacingInt = Integer.parseInt(beforeAfterSpacing);
        if (beforeAfterSpacingInt == 50) {
            readableString = "“0.5行”";
        } else if (beforeAfterSpacingInt == 100) {
            readableString = "“1行”";
        } else if (beforeAfterSpacingInt == 150) {
            readableString = "“1.5行”";
        } else if (beforeAfterSpacingInt == 200) {
            readableString = "“2行”";
        } else if (beforeAfterSpacingInt == 240) {
            readableString = "“2.5行”";
        } else if (beforeAfterSpacingInt == 300) {
            readableString = "“3行”";
        } else {
            double beforeAfterSpacingIntDiv50 = beforeAfterSpacingInt / 50.0;
            // 保留一位小数
            String beforeAfterSpacingIntDiv50Str = String.format("%.1f", beforeAfterSpacingIntDiv50);
            readableString = "“" + beforeAfterSpacingIntDiv50Str + "行”";
        }
        return readableString;
    }

    /**
     * 将段落对齐方式的属性值转化为容易阅读的字符串。
     * left为左对齐，center为居中，right为右对齐，both为两端对齐。
     */
    public static String jcToReadableString(String paragraphAlignment) {
        String readableString;
        if (paragraphAlignment.equals("left")) {
            readableString = "“左对齐”";
        } else if (paragraphAlignment.equals("center")) {
            readableString = "“居中”";
        } else if (paragraphAlignment.equals("right")) {
            readableString = "“右对齐”";
        } else if (paragraphAlignment.equals("both")) {
            readableString = "“两端对齐”";
        } else {
            readableString = "“未知对齐方式" + paragraphAlignment + "”";
        }
        return readableString;
    }

    /**
     * 将段落缩进的属性值转化为容易阅读的字符串。
     * 100为1字符，200为2字符，300为3字符。
     */
    public static String indentToReadableString(String indent) {
        String readableString;
        int indentInt = Integer.parseInt(indent);
        if (indentInt == 100) {
            readableString = "“1字符”";
        } else if (indentInt == 200) {
            readableString = "“2字符”";
        } else if (indentInt == 300) {
            readableString = "“3字符”";
        } else {
            double indentIntDiv100 = indentInt / 100.0;
            // 保留一位小数
            String indentIntDiv100Str = String.format("%.1f", indentIntDiv100);
            readableString = "“" + indentIntDiv100Str + "字符”";
        }
        return readableString;
    }
}
