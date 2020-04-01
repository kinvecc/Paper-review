package com.clrvn;

import com.clrvn.utils.CosineSimilarityUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClrvnApplication {

    public static void main(String[] args) {
        System.err.println("为了初始化字典");
        CosineSimilarityUtil text1 = new CosineSimilarityUtil("我觉的我还行吧，I am icon man");
        CosineSimilarityUtil text2 = new CosineSimilarityUtil("你觉得我还行吗？I am super man");
        System.out.println(text1.similarityRatio(text2.getMap()) + " similarityRatio.");
        CosineSimilarityUtil text3 = new CosineSimilarityUtil("他觉得你还行吧。I am black woman");
        System.out.println(text3.similarityRatio(text2.getMap()) + " similarityRatio.");
        SpringApplication.run(ClrvnApplication.class, args);

        /*//加载Word文档
        Document document = new Document("aaa.doc");
        //查找所有需要高亮的文本

        TextSelection[] textSelections1 = document.findAllString("而", false, false);

        //设置高亮颜色
        for (TextSelection selection : textSelections1) {
            selection.getAsOneRange().getCharacterFormat().setHighlightColor(Color.YELLOW);
        }
        //保存文档
        String fileName = "Output" + UUID.randomUUID().toString().substring(0, 5) + ".docx";
        document.saveToFile(fileName, FileFormat.Docx);

        //读取内置文档属性
        System.out.println("标题： " + document.getBuiltinDocumentProperties().getTitle());
        System.out.println("主题： " + document.getBuiltinDocumentProperties().getSubject());
        System.out.println("作者： " + document.getBuiltinDocumentProperties().getAuthor());
        System.out.println("单位： " + document.getBuiltinDocumentProperties().getCompany());
        System.out.println("主管： " + document.getBuiltinDocumentProperties().getManager());
        System.out.println("类别： " + document.getBuiltinDocumentProperties().getCategory());
        System.out.println("关键字： " + document.getBuiltinDocumentProperties().getKeywords());
        System.out.println("备注： " + document.getBuiltinDocumentProperties().getComments());

        List<String> strings = FileUtil.readFileByLines(fileName);
        System.err.println(strings.remove(0));*/
    }

}
