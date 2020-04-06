package com.clrvn;

import com.clrvn.repository.PaperRepository;
import com.spire.doc.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClrvnApplicationTests {

    @Test
    void contextLoads() {

        //加载Word文档
        Document document = new Document("D:\\陈振华\\Desktop\\中大肿瘤 2019-8-21\\20191114物价系统接口说明.docx");

        //读取内置文档属性
        System.out.println("标题： " + document.getBuiltinDocumentProperties().getTitle());
        System.out.println("主题： " + document.getBuiltinDocumentProperties().getSubject());
        System.out.println("作者： " + document.getBuiltinDocumentProperties().getAuthor());
        System.out.println("单位： " + document.getBuiltinDocumentProperties().getCompany());
        System.out.println("主管： " + document.getBuiltinDocumentProperties().getManager());
        System.out.println("类别： " + document.getBuiltinDocumentProperties().getCategory());
        System.out.println("关键字： " + document.getBuiltinDocumentProperties().getKeywords());
        System.out.println("备注： " + document.getBuiltinDocumentProperties().getComments());
    }

    @Autowired
    private PaperRepository paperRepository;
    @Test
    void deletePaper(){

        paperRepository.deleteById(1);
    }

}
