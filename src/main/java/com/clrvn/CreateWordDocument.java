package com.clrvn;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ParagraphStyle;
import com.spire.doc.documents.TextSelection;

import java.awt.*;
import java.util.UUID;

public class CreateWordDocument {
    public static void main(String[] args) {
        //创建Word文档
        Document document = new Document();

        //添加一个section
        Section section = document.addSection();

        //添加三个段落至section
        Paragraph para1 = section.addParagraph();
        para1.appendText("滕王阁序");

        Paragraph para2 = section.addParagraph();
        para2.appendText("豫章故郡，洪都新府。星分翼轸，地接衡庐。襟三江而带五湖，控蛮荆而引瓯越。" +
                "物华天宝，龙光射牛斗之墟；人杰地灵，徐孺下陈蕃之榻。雄州雾列，俊采星驰。台隍枕夷夏之交，宾主尽东南之美。" +
                "都督阎公之雅望，棨戟遥临；宇文新州之懿范，襜帷暂驻。十旬休假，胜友如云；千里逢迎，高朋满座。" +
                "腾蛟起凤，孟学士之词宗；紫电青霜，王将军之武库。家君作宰，路出名区；童子何知，躬逢胜饯。");

        Paragraph para3 = section.addParagraph();
        para3.appendText("时维九月，序属三秋。潦水尽而寒潭清，烟光凝而暮山紫。俨骖騑于上路，访风景于崇阿；临帝子之长洲，得天人之旧馆。" +
                "层峦耸翠，上出重霄；飞阁流丹，下临无地。鹤汀凫渚，穷岛屿之萦回；桂殿兰宫，即冈峦之体势。");

        //将第一段作为标题，设置标题格式
        ParagraphStyle style1 = new ParagraphStyle(document);
        style1.setName("titleStyle");
        style1.getCharacterFormat().setBold(true);
        style1.getCharacterFormat().setTextColor(Color.BLUE);
        style1.getCharacterFormat().setFontName("宋体");
        style1.getCharacterFormat().setFontSize(12f);
        document.getStyles().add(style1);
        para1.applyStyle("titleStyle");

        //设置其余两个段落的格式
        ParagraphStyle style2 = new ParagraphStyle(document);
        style2.setName("paraStyle");
        style2.getCharacterFormat().setFontName("宋体");
        style2.getCharacterFormat().setFontSize(11f);
        document.getStyles().add(style2);
        para2.applyStyle("paraStyle");
        para3.applyStyle("paraStyle");

        //设置第一个段落的对齐方式
        para1.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);

        //设置第二段和第三段的段首缩进
        para2.getFormat().setFirstLineIndent(25f);
        para3.getFormat().setFirstLineIndent(25f);

        //设置第一段和第二段的段后间距
        para1.getFormat().setAfterSpacing(15f);
        para2.getFormat().setAfterSpacing(10f);

        //查找所有需要高亮的文本

        TextSelection[] textSelections1 = document.findAllString("而", false, false);

        //设置高亮颜色

        for (TextSelection selection : textSelections1) {

            selection.getAsOneRange().getCharacterFormat().setHighlightColor(Color.YELLOW);

        }


        //保存文档
        document.saveToFile("Output" + UUID.randomUUID().toString().substring(0, 5) + ".docx", FileFormat.Docx);
    }
}