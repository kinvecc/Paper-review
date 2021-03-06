package com.clrvn.domain;

import cn.hutool.core.collection.CollUtil;
import com.clrvn.utils.SegmentationAlgorithmUtil;
import com.spire.doc.Document;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Clrvn
 * @description 文档内容对象
 * @date 2020-03-24 9:17
 */
@Data
public class DocumentContent {

    private List<SectionContent> sectionContents;

    private Document document;

    public DocumentContent(Document document) {

        this.document = document;

        if (this.sectionContents == null) {
            sectionContents = new ArrayList<>();
        }

        document.getSections().forEach(ele -> {

            Section section = (Section) ele;
            SectionContent sectionContent = new SectionContent();

            List<ParagraphContent> paragraphContents = new ArrayList<>();
            section.getParagraphs().forEach(obj -> {

                Paragraph paragraph = (Paragraph) obj;
                ParagraphContent paragraphContent = new ParagraphContent(paragraph.getText());
                paragraphContents.add(paragraphContent);
            });

            sectionContent.setParagraphs(paragraphContents);

            sectionContents.add(sectionContent);
        });
    }

    /**
     * 标记文本
     *
     * @param otherText 其他的文本
     * @param color     颜色
     */
    public List<String> signText(int sectionNum, int paragraphNum, List<String> otherText, Color color) {

        Paragraph paragraph = this.document.getSections().get(sectionNum).getParagraphs().get(paragraphNum);

        String text = paragraph.getText();
        System.err.println("元数据是：" + text);
        List<String> stringList = compare(text, otherText);
        stringList.forEach(str -> {
            System.err.println("要找的是：" + str);
            try {
                paragraph.find(str, false, false).getAsOneRange().getCharacterFormat().setHighlightColor(color);
            } catch (Exception ignored) {
            }
        });
        return stringList;
    }

    /**
     * 字符串1和字符串2比较，返回字符串1含有字符串2的字符集合
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 字符串1含有字符串2的字符集合
     */
    private List<String> compare(String str1, List<String> str2) {

        List<String> str1List = SegmentationAlgorithmUtil.segmentationAlgorithm(str1);
        List<String> str2List = new ArrayList<>();

        str2.forEach(str -> {
            //这一步是分词，然后汇总
            str2List.addAll(SegmentationAlgorithmUtil.segmentationAlgorithm(str));
        });

        return ((List<String>) CollUtil.intersection(str1List, CollUtil.distinct(str2List)));
    }

    /**
     * 获取总段落数量
     */
    public int getParagraphNum() {
        return sectionContents.stream().mapToInt(ele -> ele.getParagraphs().size()).sum();
    }

    /**
     * 获取内容
     */
    public String getContent() {
        StringBuilder stringBuilder = new StringBuilder();
        this.sectionContents.forEach(sectionContent -> {
            sectionContent.getParagraphs().forEach(paragraphContent -> {
                stringBuilder.append(paragraphContent.getText());
            });
        });
        return stringBuilder.toString();
    }
}

