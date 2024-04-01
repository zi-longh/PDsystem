package jnu.utils;

import com.aspose.words.Document;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.zilong.fdbackend.controller.FileController.ROOT_PATH;
import static jnu.service.xmlprocessor.CommentOperation.clearComment;

/**
 * 提供针对docx文件的备份、解压、压缩功能
 */
public class DocxUtils {

    /**
     * 备份docx文件到srcFileRecord文件夹
     *
     * @param docxPath docx文件路径
     * @return 返回备份后的文件路径
     */
    public static String backUpDocxToSrcFileRecord(@NotNull Path docxPath) {
        String srcFileName = docxPath.getFileName().toString();
        String newFilePath = ROOT_PATH + "/srcFileRecord/" + srcFileName.substring(0, srcFileName.lastIndexOf(".")) + "_" + LocalDateTime.now().toString().replace(":", "-") + ".docx";
        try {
            if (docxPath.toString().endsWith(".doc")) {
                Document doc = new Document(docxPath.toString());
                doc.save(newFilePath);
                System.out.println("doc文件转换为docx文件成功，新文件路径为：" + newFilePath);
            } else if (docxPath.toString().endsWith(".docx")) {
                Files.copy(docxPath, Paths.get(newFilePath));
            } else {
                System.out.println("文件格式不正确");
                return null;
            }

        } catch (Exception e) {
            System.err.println("文件复制失败: " + e.getMessage());
        }
        System.out.println("文件复制成功，新文件路径为：" + newFilePath);
        return newFilePath;
    }

    /**
     * 备份docx文件并解压<br>
     * step1: 备份docx文件到srcFileRecord文件夹<br>
     * step2: 解压docx文件到xmlProcess文件夹
     *
     * @param docxPath docx文件路径
     * @return 返回解压后的文件夹路径
     */
    public static String unZipDocx(String docxPath) {
        // step1: 复制docx文件到srcFileRecord文件夹
        Path dPath = Paths.get(docxPath);
        if (!Files.exists(dPath)) {
            System.err.println("文件不存在");
            return null;
        }
        String newFilePath;
        // 如果是docx文件，则备份docx文件到srcFileRecord文件夹

        newFilePath = backUpDocxToSrcFileRecord(dPath); // 备份docx文件到srcFileRecord文件夹
        if (newFilePath == null) { // 文件类型有问题
            return null;
        }


        // step2: 解压docx文件到xmlProcess文件夹
        Path path = Paths.get(newFilePath);
        String fileName = path.getFileName().toString().substring(0, path.getFileName().toString().lastIndexOf("."));
        String destDirectory = "files/xmlProcess/" + fileName;
        try {
            ZipUtil.unZipFile(newFilePath, destDirectory);
        } catch (Exception e) {
            System.err.println("解压失败: " + e.getMessage());
        }
        System.out.println("解压成功，解压后的文件夹路径为：" + destDirectory);
        return destDirectory;
    }

    /**
     * <p>
     * 将处理好的xml文件压缩成docx文件，保存到outputFileRecord文件夹，并根据检测状态码添加标志，0表示检测通过，1表示待修改。
     * 同时将xml文件夹删除。
     * </p>
     *
     * @param targetDocxDirectory 要压缩的目录路径，通常是解压后的文件夹路径（即unZipDocx方法的返回值）
     * @param paperDtcResult                标志检测状态码
     * @return 返回压缩后的docx文件路径
     */
    public static String zipDocx(String targetDocxDirectory, int paperDtcResult) {
        Path path = Paths.get(targetDocxDirectory);
        String fileName = path.getFileName().toString();

        String status;
        if (paperDtcResult == 0) {
            status = "(检测通过)";
        } else if (paperDtcResult == 1) {
            status = "(检测通过但有修改建议)";
        } else {
            status = "(待修改)";
        }
        String filePath = ROOT_PATH + "/outputFileRecord/" + status + fileName + ".docx";
        ZipUtil.zipDirectory(targetDocxDirectory, filePath);
        System.out.println("压缩成功，压缩后的文件路径为：" + filePath);

        // 删除xml文件夹，TODO:测试完成后取消注释
/*        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            System.err.println("文件夹删除失败: " + e.getMessage());
        }*/

        return filePath;
    }

    /**
     * 获取当前时间，并且格式化为字符串
     * 具体格式如右："yyyy-MM-dd'T'HH:mm:ss'Z'"
     * */
    public static String getNowTime() {
        /* 具体格式如右："yyyy-MM-dd'T'HH:mm:ss'Z'" */
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return dateTime.format(formatter);
    }

    /**
     * 生成PDF文件
     * 将docx文件转换为pdf文件，且不包含批注
     * 先把docx文件的批注删除，然后再转换为pdf文件
     * */
    public static String docxToPdf(String xmlDirectory, int docxEndCommentNum) {
        String fileName = Paths.get(xmlDirectory).getFileName().toString();
        clearComment(xmlDirectory, docxEndCommentNum);  //删除批注
        // 将清楚批注后的xml文件压缩为docx文件，再将docx文件转换为pdf文件，最后删除docx文件
        String docxPath = ROOT_PATH + "/pdf/" + fileName + ".docx";
        ZipUtil.zipDirectory(xmlDirectory, docxPath);
        String pdfPath = ROOT_PATH + "/pdf/" + fileName + ".pdf";
        long old = System.currentTimeMillis();
        try {
            FileOutputStream os = new FileOutputStream(pdfPath);
            Document doc = new Document(docxPath);
            doc.save(os, com.aspose.words.SaveFormat.PDF);
            os.close();
            long now = System.currentTimeMillis();
            System.out.println("转化pdf共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
            Files.deleteIfExists(Paths.get(docxPath));  //删除docx文件
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("转换pdf文件失败");
        }
        return fileName + ".pdf";
    }


}
