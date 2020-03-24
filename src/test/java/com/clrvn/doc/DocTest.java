package com.clrvn.doc;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.clrvn.ClrvnApplicationTests;
import com.clrvn.domain.DocumentContent;
import com.clrvn.domain.ParagraphContent;
import com.clrvn.domain.SectionContent;
import com.clrvn.utils.CosineSimilarityUtil;
import com.clrvn.utils.FileUtil;
import com.clrvn.utils.RecheckingUtil;
import com.clrvn.utils.SegmentationAlgorithmUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.collections.SectionCollection;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TextSelection;
import com.spire.doc.fields.TextRange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Clrvn
 * @description 测试doc类型文档
 * @date 2020-03-20 10:35
 */
class DocTest extends ClrvnApplicationTests {

    @Value("${similarity.paragraph}")
    private Double paragraphSimilarity;

    public static void main(String[] args) {
        //得到结果之后，再编辑文档
        Document uploadDoc = new Document("test.docx");

        int i = 0;
        for (Object o : uploadDoc.getSections()) {
            Section section = (Section) o;
            i++;
            int j = 0;
            for (Object obj : section.getParagraphs()) {
                Paragraph paragraph = (Paragraph) obj;
                String text = paragraph.getText();
                j++;
                System.err.println(i + "节" + j + "段落内容：" + text);
            }
            System.err.println(i + "节有" + j + "段");
        }

        System.err.println("文档共有：" + i + "节");
    }

    @Test
    void signText() {


    }


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

    @Test
    void paperReview() {

        List<String> data1File = FileUtil.getListByFile("花木兰1.docx");
        List<String> data2File = FileUtil.getListByFile("花木兰2.docx");
        List<String> uploadFile = FileUtil.getListByFile("花木兰.docx");

        Map<Integer, Map<String, List<String>>> result = new HashMap<>(uploadFile.size());

        //先和1在和2（其实就是做循环）
        for (int i = 0; i < uploadFile.size(); i++) {
            String uploadStr = uploadFile.get(i);
            for (String data1Str : data1File) {
                if (CosineSimilarityUtil.similarity(uploadStr, data1Str) > paragraphSimilarity) {
                    Map<String, List<String>> stringMap = result.get(i);
                    if (MapUtil.isNotEmpty(stringMap)) {
                        List<String> strings = stringMap.get(uploadStr);
                        if (CollUtil.isEmpty(strings)) {
                            strings = new ArrayList<>();
                        }
                        strings.add(data1Str);
                    } else {
                        stringMap = new HashMap<>();

                        List<String> strings = new ArrayList<>();
                        strings.add(data1Str);
                        stringMap.put(uploadStr, strings);
                    }
                    result.put(i, stringMap);
                }
            }

            for (String data2Str : data2File) {
                if (CosineSimilarityUtil.similarity(uploadStr, data2Str) > paragraphSimilarity) {
                    Map<String, List<String>> stringMap = result.get(i);
                    if (MapUtil.isNotEmpty(stringMap)) {
                        List<String> strings = stringMap.get(uploadStr);
                        if (CollUtil.isEmpty(strings)) {
                            strings = new ArrayList<>();
                        }
                        strings.add(data2Str);
                    } else {
                        stringMap = new HashMap<>();

                        List<String> strings = new ArrayList<>();
                        strings.add(data2Str);
                        stringMap.put(uploadStr, strings);
                    }
                    result.put(i, stringMap);
                }
            }

        }
        System.err.println(JSON.toJSONString(result));

        //得到结果之后，再编辑文档
        Document uploadDoc = new Document("花木兰.docx");

        int i = 0;
        for (Object o : uploadDoc.getSections()) {
            SectionCollection section = (SectionCollection) o;
            i++;
            System.err.println(i);
        }

        System.err.println(i);

    }

    @Test
    void editDoc() {
        //得到结果之后，再编辑文档
        Document uploadDoc = new Document("花木兰.docx");
        TextRange[] textRanges = uploadDoc.getSections().get(0).getParagraphs().get(0).find("木兰", false, false)
                .getRanges();
        for (TextRange textRange : textRanges) {
            textRange.getCharacterFormat().setHighlightColor(Color.YELLOW);
        }

        uploadDoc.saveToFile("编辑" + System.currentTimeMillis() + ".docx", FileFormat.Docx);
    }

    @Test
    void DocumentContentTest() {

        Document uploadDoc = new Document("哈哈.docx");

        DocumentContent documentContent = new DocumentContent(uploadDoc);

        documentContent.signText(0, 0, Collections.singletonList("千鸟飞山头ABC跟他一起"), Color.yellow);

        documentContent.getDocument().saveToFile("哈哈" + System.currentTimeMillis() + ".docx", FileFormat.Docx);
    }

    @Test
    void compareTwoDocument() {
        Document uploadDoc = new Document("花木兰.docx");
        Document data1Doc = new Document("花木兰1.docx");
        Document data2Doc = new Document("花木兰2.docx");

        DocumentContent uploadContent = new DocumentContent(uploadDoc);
        DocumentContent data1Content = new DocumentContent(data1Doc);
        DocumentContent data2Content = new DocumentContent(data2Doc);

        List<DocumentContent> dataContentList = new ArrayList<>(Arrays.asList(data1Content, data2Content));

        Map<int[], List<String>> result = new HashMap<>();

        List<SectionContent> sectionContents = uploadContent.getSectionContents();
        for (int i = 0, sectionContentsSize = sectionContents.size(); i < sectionContentsSize; i++) {
            SectionContent sectionContent = sectionContents.get(i);
            List<ParagraphContent> paragraphs = sectionContent.getParagraphs();
            for (int j = 0, paragraphsSize = paragraphs.size(); j < paragraphsSize; j++) {
                ParagraphContent paragraphContent = paragraphs.get(j);
                String text = paragraphContent.getText();
                int sectionNum = i;
                int paragraphNum = j;
                dataContentList.forEach(dataContent -> {
                    dataContent.getSectionContents().forEach(sectionContent1 -> {
                        sectionContent1.getParagraphs().forEach(paragraph -> {
                            //如果相似度大于配置里面段落的相似度
                            String otherText = paragraph.getText();
                            if (CosineSimilarityUtil.similarity(text, otherText) > paragraphSimilarity) {
                                int[] key = new int[2];
                                key[0] = sectionNum;
                                key[1] = paragraphNum;

                                List<String> values = result.get(key);

                                //如果上传的那一行数据还是第一次匹配，所以values为空
                                if (CollUtil.isEmpty(values)) {
                                    values = new ArrayList<>();
                                    //添加进去
                                    values.add(otherText);
                                    result.put(key, values);
                                }else{
                                    values.add(otherText);
                                }
                            }
                        });
                    });
                });

            }
        }

        System.err.println(JSON.toJSONString(result));
        //标色
        for (Map.Entry<int[], List<String>> entry : result.entrySet()) {
            int[] key = entry.getKey();
            uploadContent.signText(key[0], key[1], entry.getValue(), Color.yellow);
        }

        uploadContent.getDocument().saveToFile("完结"+System.currentTimeMillis()+".docx", FileFormat.Docx);

        int size = result.keySet().size();
        int paragraphNum = uploadContent.getParagraphNum();
        double ratio = (double)size /paragraphNum;
        System.out.println("查重率：" + ratio);
    }


    @Test
    public void intersection(){

//        String str1 = "臣本布衣，躬耕于南阳";
//        String str2 = "我本靓仔，躬耕在咸阳";
        String str1 = "万里赴戎机百，军事气传金析，将军自菲薄，壮士十年归。";
        String str2 = "万里赴戎机，关山度若飞。朔气传金析，寒光照铁衣。将军百战死，壮士十年归。";
        List<String> str1List = SegmentationAlgorithmUtil.segmentationAlgorithm(str1);
        List<String> str2List = SegmentationAlgorithmUtil.segmentationAlgorithm(str2);

        str1List.forEach(System.out::println);
        str2List.forEach(System.err::println);
        Collection<String> intersection = CollUtil.intersection(str1List, str2List);

        CollUtil.distinct(intersection).forEach(System.out::println);
    }
}
