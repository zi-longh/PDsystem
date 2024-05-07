package com.zilong.fdbackend.controller;

import cn.hutool.core.io.FileUtil;
import com.zilong.fdbackend.common.Result;
import com.zilong.fdbackend.pojo.DetectRecordPojo;
import com.zilong.fdbackend.pojo.forStr.DetectRecordForStr;
import com.zilong.fdbackend.service.DetectRecordService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jnu.service.xmlprocessor.WordDocFormatDetection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    DetectRecordService detectRecordService;


    public static final String ROOT_PATH = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/files";


    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return Result.error("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 获取到原始的文件名 docx, doc
        // 检查是否存在同名文件，若存在则删除源文件
        File file1 = new File(ROOT_PATH + "/paperFile/" + fileName);
        if (file1.exists()) {
            file1.delete();
        }
        // 检查文件类型，如果不是docx或doc则返回错误
        if (!fileName.endsWith(".docx") && !fileName.endsWith(".doc")) {
            return Result.error("文件类型错误，目前支持传入docx或doc文件");
        }
        File finalFilePath = new File(ROOT_PATH + "/paperFile/" + fileName);
        // 最终存到磁盘的文件对象
        if (!finalFilePath.getParentFile().exists()) {  // 如果父级目录不存在 就得创建
            finalFilePath.getParentFile().mkdirs();
        }
        file.transferTo(finalFilePath);
        // 返回文件的url
        String url = "http://localhost:9090/files/download?fileName=" + fileName;
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("url", url);
        stringStringHashMap.put("filePath", finalFilePath.toString());

        return Result.success(stringStringHashMap);
    }

    /**
     * 文件下载，下载docx文件
     */
    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        File file = new File(ROOT_PATH + "/outputFileRecord/" + fileName);  // 文件在存盘存储的对象
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        FileUtil.writeToStream(file, os);
        os.flush();
        os.close();
    }

    /**
     * 文件下载，下载pdf文件
     * */
    @GetMapping("/downloadPDF")
    public void downloadPDF(String fileName, HttpServletResponse response) throws IOException {
        File file = new File(ROOT_PATH + "/pdf/" + fileName);  // 文件在存盘存储的对象
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        FileUtil.writeToStream(file, os);
        os.flush();
        os.close();
    }

    /**
     * 论文检测，返回检测结果，并添加一条检测记录到数据库
     * */
    @PostMapping("/detect")
    public Result detect(@RequestBody WordDocFormatDetection detector) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        detector.init();
        System.out.println(detector);
        detector.startDetection();
        DetectRecordPojo detectRecordPojo = new DetectRecordPojo();
        if (detector.getPaperDtcResult() == -1) {
            return Result.error("检测失败");
        }
        detectRecordPojo.setUsername(detector.getUsername());
        detectRecordPojo.setTemplateId(detector.getTemplateId());
        detectRecordPojo.setDetectTime(formattedDateTime);
        detectRecordPojo.setPaperName(detector.getPaperName());
        detectRecordPojo.setPaperEnglishName(detector.getPaperEnglishName());
        detectRecordPojo.setStatus(Integer.toString(detector.getPaperDtcResult()));
        detectRecordPojo.setResultFileName(detector.getResultDocxName());
        detectRecordPojo.setResultPDF(detector.getResultPDFName());
        if (detector.getPaperDtcResult() != 2 && Objects.equals(detector.getIsSendToTeacher(), "1")) {
            detectRecordPojo.setIsSendToTeacher("1");
            detectRecordPojo.setTeacherUsername(detector.getTeacherUsername());
        } else {
            detectRecordPojo.setIsSendToTeacher("0");
        }
        detectRecordService.addRecord(detectRecordPojo);
        return Result.success(new DetectRecordForStr(detectRecordPojo));
    }


}

