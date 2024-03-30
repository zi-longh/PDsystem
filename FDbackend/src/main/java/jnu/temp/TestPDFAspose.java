package jnu.temp;


import com.aspose.words.Comment;
import com.aspose.words.Document;
import com.aspose.words.NodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;

public class TestPDFAspose {


    public static void main(String[] args) {

        String sourceFile = "src/main/resources/paperFile/wps测试论文.docx";
        String destFile = "src/main/resources/paperFile/22222.pdf";
        doc2pdf(sourceFile, destFile);


    }

    public static void doc2pdf(String sourceFile, String targetFile) {
        try {
            long old = System.currentTimeMillis();
            FileOutputStream os = new FileOutputStream(targetFile);
            Document doc = new Document(sourceFile);
            doc.save(os, com.aspose.words.SaveFormat.PDF);
            os.close();
            long now = System.currentTimeMillis();
            System.out.println("转化pdf共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

