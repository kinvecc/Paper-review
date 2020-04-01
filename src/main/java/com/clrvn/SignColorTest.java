package com.clrvn;

import com.clrvn.utils.FileUtil;
import com.spire.doc.BodyRegion;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.TextSelection;

import java.awt.*;
import java.util.List;
import java.util.UUID;

/**
 * @author Clrvn
 * @description 高亮doc文档测试
 */
public class SignColorTest {

    public static void main(String[] args) {
        //加载Word文档
        Document document = new Document("花木兰.docx");
        //查找所有需要高亮的文本
        /*BodyRegion
        document.findStringInLine()*/

        TextSelection[] textSelections1 = document.findAllString("木兰", true, true);

        //设置高亮颜色
        for (TextSelection selection : textSelections1) {
            selection.getAsOneRange().getCharacterFormat().setHighlightColor(Color.YELLOW);
        }
        //保存文档
        String fileName = "花木兰" + UUID.randomUUID().toString().substring(0, 5) + ".docx";
        document.saveToFile(fileName, FileFormat.Docx);

    }
}