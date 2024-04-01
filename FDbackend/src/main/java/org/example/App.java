package org.example;

import jnu.service.xmlprocessor.WordDocFormatDetection;

import static com.zilong.fdbackend.controller.FileController.ROOT_PATH;

/**
 * test
 */
public class App {
    public static void main(String[] args) {
        String docFilePath = ROOT_PATH + "/paperFile/wps测试论文2.docx";
        System.out.println(docFilePath);
        String paperName = "基于OOXML标准的论文检测与校正系统";
        String paperEnglishName = "Paper detection and correction system based on OOXML standard";
        String templateId = "0";
        WordDocFormatDetection wordDocFormatDetection = new WordDocFormatDetection(docFilePath, paperName, paperEnglishName, templateId, "student1");

        wordDocFormatDetection.startDetection();

    }
}
