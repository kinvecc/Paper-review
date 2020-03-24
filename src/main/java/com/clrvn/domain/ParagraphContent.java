package com.clrvn.domain;

import lombok.Data;

/**
 * @author Clrvn
 * @description
 * @date 2020-03-24 14:32
 */
@Data
public class ParagraphContent{

    private String text;

    public ParagraphContent(String text){
        this.text = text;
    }

}
