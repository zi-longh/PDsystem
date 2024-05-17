package jnu.service.xmlprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 检测论文中参考文献的引用是否符合规范要求
 */
public class ReferencesDetect {
    // 存储正则表达式的map，匹配标准为国标GB/T 7714-2015
    public static Map<String, String> stdRegexMap = new HashMap<>();

    static {
        stdRegexMap.put("图书类", "(.+?)\\. *(.+?)\\[M\\]\\. *(.*?)\\.*(.+?):* *(.+?), *(\\d{4})(.*?)\\.");
        stdRegexMap.put("论文集和会议录", "(.+?)\\. *(.+?)\\[C\\]. *(.+?):* *(.+?), *\\d{4}(.*?)\\.");
        stdRegexMap.put("报告", "(.+?)\\. *(.+?)\\[R\\]\\. *(.+?): *(.+?), *\\d{4}(.*?)\\.");
        stdRegexMap.put("学位论文", "(.+?)\\. *(.+?)\\[D\\]\\. *(.+?):* *(.+?), *\\d{4}(.*?)\\.");
        stdRegexMap.put("专利文献", "(.+?)\\. *(.+?): *(.+?)\\[P\\]\\. *\\d{4}-\\d{2}-\\d{2}\\.");
        stdRegexMap.put("标准文献", "(.+?)\\. *(.+?): *(.+?)\\[((S)|(S/OL))\\]\\. *(.+?): *(.+?), *\\d{4}(.*?)\\.");
        stdRegexMap.put("期刊文献(包括期刊中析出的文献)", "(.+?)\\. *(.+?)\\[J\\]\\. *(.+?), *\\d{4}(.*?)\\.");
        stdRegexMap.put("报纸文献", "(.+?)\\. *(.+?)\\[N\\]\\. *(.+?), *\\d{4}-\\d{2}-\\d{2}(.*?)\\.");
        stdRegexMap.put("专著和会议集中析出的文献", "(.+?)\\. *(.+?)\\[(M|C)\\]//(.+?)\\. *(.+?), *\\d{4}(.*?)\\.");
        stdRegexMap.put("电子资源文献", "(.+?)\\. *(.+?)\\[((DB/OL)|(DB/MT)|(DB/CD)|(M/CD)|(CP/DK)|(J/OL)|(EB/OL))\\]\\. *(.+?)\\.*(.+?) *http(.*)\\.");
    }


    public static String getReferenceType(String reference) {
        if (reference.matches(".*\\[M\\].*")) {
            return "图书类文献";
        }
        if (reference.matches(".*\\[C\\].*")) {
            return "论文集和会议录文献";
        }
        if (reference.matches(".*\\[R\\].*")) {
            return "报告文献";
        }
        if (reference.matches(".*\\[D\\].*")) {
            return "学位论文";
        }
        if (reference.matches(".*\\[P\\].*")) {
            return "专利文献";
        }
        if (reference.matches(".*\\[((S)|(S/OL))\\].*")) {
            return "标准文献";
        }
        if (reference.matches(".*\\[J\\].*")) {
            return "期刊文献(包括期刊中析出的文献)";
        }
        if (reference.matches(".*\\[N\\].*")) {
            return "报纸文献";
        }
        if (reference.matches(".*\\[(M|C)\\].*")) {
            return "专著和会议集中析出的文献";
        }
        if (reference.matches(".*\\[((DB/OL)|(DB/MT)|(DB/CD)|(M/CD)|(CP/DK)|(J/OL)|(EB/OL))\\].*")) {
            return "电子资源文献";
        }
        return "未知类型";
    }


    public static boolean isReferenceValid(String reference) {
        for (Map.Entry<String, String> entry : stdRegexMap.entrySet()) {
            if (reference.matches(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

}

/**
 * 用于存储文献引用的位置信息
 * */
class Quote{
    public int pIndex;
    public int rOrder; // 同一个p中的第几个引用
    public ArrayList<Integer> quoteNums;
    public String matchStr;

    public Quote(int pIndex, int rIndex, String matchStr) {
        this.pIndex = pIndex;
        this.rOrder = rIndex;
        this.matchStr = matchStr;
        this.quoteNums = new ArrayList<>();
    }

    public int getpIndex() {
        return pIndex;
    }

    public void setpIndex(int pIndex) {
        this.pIndex = pIndex;
    }

    public int getrOrder() {
        return rOrder;
    }

    public void setrOrder(int rOrder) {
        this.rOrder = rOrder;
    }

    public ArrayList<Integer> getQuoteNum() {
        return quoteNums;
    }

    public void setQuoteNums(int[] quoteNum) {
        for (int i : quoteNum) {
            this.quoteNums.add(i);
        }
    }

    public String getMatchStr() {
        return matchStr;
    }

    public void setMatchStr(String matchStr) {
        this.matchStr = matchStr;
    }
}
