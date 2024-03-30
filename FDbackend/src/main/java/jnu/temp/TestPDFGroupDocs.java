package jnu.temp;

import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;

public class TestPDFGroupDocs {
    public static void main(String[] args) {

        // 在 Java 中将 Word 文档 (DOC/DOCX) 转换为 PDF
        Converter converter = new Converter("src/main/resources/paperFile/wps测试论文.docx");
        PdfConvertOptions options = new PdfConvertOptions();
        options.setPageNumber(1);
        options.setPagesCount(16);
        converter.convert("src/main/resources/paperFile/测试论文.pdf", options);

    }
}
