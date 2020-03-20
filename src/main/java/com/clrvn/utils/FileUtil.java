package com.clrvn.utils;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Clrvn
 * @description 文件读写工具类
 * @className FileUtil
 * @date 2019-04-26 10:36
 */
@SuppressWarnings("all")
public class FileUtil {

    //默认的路径是项目布置的static下面的upload中，当然你也可以换成你本地的D:\下面任意一个文件夹下面
    public static final String UPLOAD_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\upload\\";

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param path 文件路径
     */
    public static List<String> readFileByLines(String path) {
        File file = new File(path);

        List<String> tempList = new ArrayList<>();

        BufferedReader reader = null;
        try {

//			reader = new BufferedReader(new FileReader(file));
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            String tempString = null;

            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                System.err.println(tempString);
                tempList.add(tempString);
            }

            reader.close();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return tempList;
    }

    /**
     * 通过文件返回一个map
     *
     * @param file
     * @return
     */
    public static Map<String, String> getMapByFile(MultipartFile file) {
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
                    wordMap.put("第（" + i + "）段内容:", words);
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
     * 通过文件返回一个map
     *
     * @param file
     * @return
     */
    public static List<String> getListByFile(String path) {
        File file = new File(path);
        //创建一个list对象存放word中的内容
        List<String> wordList = new ArrayList<>();
        try {
            //判断文件格式
            if (path.endsWith(".doc")) {
                InputStream fis = new FileInputStream(file);

                //使用HWPF组件中WordExtractor类从Word文档中提取文本或段落
                WordExtractor wordExtractor = new WordExtractor(fis);
                //获取段落内容
                for (String words : wordExtractor.getParagraphText()) {
                    wordList.add(words);
                }
                fis.close();
            }
            if (path.endsWith(".docx")) {
                //创建一个临时文件
                File uFile = new File("tempFile.docx");
                if (!uFile.exists()) {
                    uFile.createNewFile();
                }
                //复制文件内容
                FileCopyUtils.copy(file, uFile);
                //包含所有POI OOXML文档类的通用功能，打开一个文件包。
                OPCPackage opcPackage = POIXMLDocument.openPackage("tempFile.docx");
                //使用XWPF组件XWPFDocument类获取文档内容
                XWPFDocument document = new XWPFDocument(opcPackage);
                List<XWPFParagraph> paras = document.getParagraphs();

                for (XWPFParagraph paragraph : paras) {
                    String words = paragraph.getText();
                    wordList.add(words);
                }
                uFile.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordList;
    }


    /**
     * 写入文件
     *
     * @param list
     */
    public static void writeFileByLines(List<String> list, String path) {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(path));

            for (String s : list) {

                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String fileName) throws Exception {

        FileOutputStream out = new FileOutputStream(fileName);

        out.write(file);
        out.flush();
        out.close();
    }
}
