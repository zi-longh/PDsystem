package com.zilong.fdbackend;

import jnu.service.xmlprocessor.TemplateInfo;
import jnu.service.xmlprocessor.WordDocFormatDetection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.zilong.fdbackend.controller.FileController.ROOT_PATH;

@SpringBootApplication
public class FDbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FDbackendApplication.class, args);

        String docFilePath = ROOT_PATH + "/paperFile/wps测试论文2.docx";
        System.out.println(docFilePath);
        String paperName = "基于OOXML标准的论文检测与校正系统";
        String paperEnglishName = "Paper detection and correction system based on OOXML standard";
        String templateId = "0";
        WordDocFormatDetection wordDocFormatDetection = new WordDocFormatDetection(docFilePath, paperName, paperEnglishName, templateId, "student1");
        wordDocFormatDetection.startDetection();

    }

}
