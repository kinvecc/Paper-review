package com.clrvn.doc;

import com.clrvn.ClrvnApplicationTests;
import com.clrvn.utils.CosineSimilarityUtil;
import com.clrvn.utils.FileUtil;
import com.clrvn.utils.RecheckingUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.collections.SectionCollection;
import com.spire.doc.documents.TextSelection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Clrvn
 * @description 测试doc类型文档
 * @date 2020-03-20 10:35
 */
class DocTest extends ClrvnApplicationTests {

    @Value("${similarity.paragraph}")
    private Double paragraphSimilarity;

    @Test
    void textColor() {
        //加载Word文档
        Document document = new Document("花木兰1.docx");
        //查找所有需要高亮的文本
        /*BodyRegion
        document.findStringInLine()*/

        SectionCollection sections = document.getSections();

//        TextSelection[] textSelections1 = document.findAllString("木兰", true, true);

        TextSelection[] textSelections1 = document.findAllString("阳光", false, true);
        //设置高亮颜色
        for (TextSelection selection : textSelections1) {
            selection.getAsOneRange().getCharacterFormat().setHighlightColor(Color.YELLOW);
        }
        //保存文档
        String fileName = "花木兰" + System.currentTimeMillis() + ".docx";
        document.saveToFile(fileName, FileFormat.Docx);
    }

    @Test
    void bigPage() {
        //加载Word文档
        Document document = new Document("论文测试.doc");
        //查找所有需要高亮的文本
        /*BodyRegion
        document.findStringInLine()*/

        SectionCollection sections = document.getSections();

//        TextSelection[] textSelections1 = document.findAllString("木兰", true, true);

        TextSelection[] textSelections1 = document.findAllString("阳光", false, true);
        //设置高亮颜色
        for (TextSelection selection : textSelections1) {
            selection.getAsOneRange().getCharacterFormat().setHighlightColor(Color.YELLOW);
        }
        //保存文档
        String fileName = "论文测试" + System.currentTimeMillis() + ".docx";
        document.saveToFile(fileName, FileFormat.Docx);
    }

    @Test
    void twoPageCompare() {

        List<String> uploadFile = FileUtil.getListByFile("花木兰1.docx");
        List<String> dataFile = FileUtil.getListByFile("花木兰.docx");

        uploadFile.forEach(str -> System.out.println("upload:" + str));
        dataFile.forEach(str -> System.out.println("data:" + str));
        /*uploadFile.forEach(dataStr -> {
            dataFile.stream().filter(uploadStr -> CosineSimilarityUtil.similarity(dataStr, uploadStr) > paragraphSimilarity);
        });*/

        //论文报告list
        List<String> reportList = RecheckingUtil.comparePaper(uploadFile, dataFile);

        reportList.forEach(System.err::println);
    }

    @Test
    void dirPath() {
       /* ApplicationHome h = new ApplicationHome(getClass());
        String dirPath = h.getSource().getParentFile().toString();
        System.err.println(dirPath);*/

        String projectPath = System.getProperty("user.dir");
        System.err.println(projectPath);

        File upload = new File(projectPath + "/src/main/resources/static/upload");
        if (!upload.exists()) upload.mkdirs();
        System.out.println("upload url:" + upload.getAbsolutePath());
    }

    @Test
    void simpleTest() {
        List<String> strings = Arrays.asList("TB_123", "TB_234", "TB_456");
        strings.stream().filter(tbStr -> !"TB_123".equals(tbStr)).collect(Collectors.toList()).forEach(System.err::println);
    }
}
