package jnu.temp;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test5 {
    public static void main(String[] args) {
        // 测试正则表达式
        String text = "感谢我的[2]XXX老师[1]，谢谢她对我的[3，5~7]悉心指导。她无私的关爱和[6，7]严谨的[6,8]治学态度，将激[9]励我不断[11]的进取，走好以后的道路。其次，还要感谢在这四年的学习中教过我的所有老师们，谢谢他们传授给了我知识。我的同学XXX，在写作的过程中给我提供了一些宝贵的资料和建议，在此一并感谢！";
        Pattern pattern = Pattern.compile("\\[([\\d,，~]+)\\]");
        Matcher matcher = pattern.matcher(text);
        ArrayList<quote> quotes = new ArrayList<>();
        int i = 0;
        int j = 1;
        while (matcher.find()) {
            quotes.add(
                    new quote(i,
                            j,
                            matcher.group(1)
                    ));
            j++;
        }
        for (quote q : quotes) {
            System.out.println(q.getMatchStr());
            System.out.println(q.getrOrder());
        }

    }

}

class quote{
    public int pIndex;
    public int rOrder; // 同一个p中的第几个引用
    public ArrayList<Integer> quoteNum;
    public String matchStr;

    public quote(int pIndex, int rIndex, String matchStr) {
        this.pIndex = pIndex;
        this.rOrder = rIndex;
        this.matchStr = matchStr;
        this.quoteNum = new ArrayList<>();
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
        return quoteNum;
    }

    public void setQuoteNum(ArrayList<Integer> quoteNum) {
        this.quoteNum = quoteNum;
    }

    public String getMatchStr() {
        return matchStr;
    }

    public void setMatchStr(String matchStr) {
        this.matchStr = matchStr;
    }
}
