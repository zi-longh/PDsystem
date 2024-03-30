package jnu.temp;


import org.jodconverter.JodConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeManager;
import org.jodconverter.office.OfficeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TestPDFFree {
    private static final Logger log = LoggerFactory.getLogger(TestPDFFree.class);
    public static boolean office2PDF(String sourceFile, String destFile) {
        OfficeManager officeManager
                = LocalOfficeManager.builder().install()
                .officeHome("C:\\Program Files (x86)\\OpenOffice 4")
                .build();
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                log.info("找不到源文件");
                return false;// 找不到源文件, 则返回-1
            }
            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            officeManager.start(); // Start the office process
            JodConverter.convert(new File(sourceFile)).to(outputFile).execute();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            OfficeUtils.stopQuietly(officeManager); // Stop the office process
        }
        return true;
    }
    public static void main(String[] args) {
        String sourceFile = "src/main/resources/paperFile/wps测试论文.docx";
        String destFile = "src/main/resources/paperFile/222.pdf";
        office2PDF(sourceFile, destFile);
    }



}
