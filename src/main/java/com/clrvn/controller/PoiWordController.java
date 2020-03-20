package com.clrvn.controller;

import com.clrvn.utils.FileUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Clrvn
 * @description
 * @className PoiWordController
 * @date 2019-05-16 14:39
 */
@RestController
@RequestMapping("/poiWord")
public class PoiWordController {

    /**
     * 按段落解析一个word文档
     *
     * @param file
     * @return
     * @throws Exception
     */
//  @ApiOperation(value = "解析word文档", notes = "按段落解析word文档")
    @PostMapping(value = "upload")
    public Map<String, String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String textFileName = file.getOriginalFilename();
        //创建一个map对象存放word中的内容
        Map<String, String> wordMap = new LinkedHashMap<>();
        try {
            //判断文件格式
            assert textFileName != null;
            if (textFileName.endsWith(".doc")) {
                InputStream fis = file.getInputStream();
                //使用HWPF组件中WordExtractor类从Word文档中提取文本或段落
                WordExtractor wordExtractor = new WordExtractor(fis);
                int i = 1;
                //获取段落内容
                for (String words : wordExtractor.getParagraphText()) {
                    System.out.println(words);
                    wordMap.put("DOC文档，第（" + i + "）段内容", words);
                    i++;
                }
                fis.close();
            }
            if (textFileName.endsWith(".docx")) {
                //创建一个临时文件
                File uFile = new File("tempFile.docx");
                if (!uFile.exists()) {
                    uFile.createNewFile();
                }
                //复制文件内容
                FileCopyUtils.copy(file.getBytes(), uFile);
                //包含所有POI OOXML文档类的通用功能，打开一个文件包。
                OPCPackage opcPackage = POIXMLDocument.openPackage("tempFile.docx");
                //使用XWPF组件XWPFDocument类获取文档内容
                XWPFDocument document = new XWPFDocument(opcPackage);
                List<XWPFParagraph> paras = document.getParagraphs();
                int i = 1;
                for (XWPFParagraph paragraph : paras) {
                    String words = paragraph.getText();
                    System.out.println(words);
                    wordMap.put("DOCX文档，第（" + i + "）段内容", words);
                    i++;
                }
                uFile.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(wordMap);
        return wordMap;
    }

    /**
     * 处理文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping(value = "/uploadPaper")
    public String uploadPaper(@RequestParam("file") MultipartFile file,
                              HttpServletRequest request) {

        String contentType = file.getContentType();

        String fileName = file.getOriginalFilename();
        //System.out.println("fileName-->" + fileName);
        //System.out.println("getContentType-->" + contentType);
        //String filePath = request.getSession().getServletContext().getRealPath("upload/");
        try {
            FileUtil.uploadFile(file.getBytes(), fileName);
        } catch (Exception e) {
        }
        //返回json
        return "upload success";
    }

    @PostMapping(value = "test")
    public String testUploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        Document document = new Document(file.getInputStream());
        document.saveToFile(FileUtil.UPLOAD_PATH + "上传" + System.currentTimeMillis() + ".docx", FileFormat.Docx);
        return "yes";
    }
}
