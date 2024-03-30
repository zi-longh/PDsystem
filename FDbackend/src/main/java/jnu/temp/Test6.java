package jnu.temp;

import static jnu.service.xmlprocessor.ReferencesDetect.getReferenceType;
import static jnu.service.xmlprocessor.ReferencesDetect.isReferenceValid;

public class Test6 {
    public static void main(String[] args) {
        // 测试正则表达式
        String referenceContentStr = "胡江婧,曹双双.基于JAVA的课程设计管理系统的设计与开发[J].科技创新与应用,2019(16):80-82.";
        boolean b = referenceContentStr.matches(".*[，。；：、].*");
        System.out.println(b);
        String referenceType = getReferenceType(referenceContentStr);
        System.out.println(referenceType);
        boolean isvalid = isReferenceValid(referenceContentStr);
        System.out.println("isvalid = " + isvalid);


    }
}
