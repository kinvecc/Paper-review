package com.clrvn.enums;

import lombok.Getter;

/**
 * @description:
 * @className: PaperTypeEnum
 * @author: Clrvn
 * @date: 2019-05-21 9:12
 */
@Getter
public enum PaperTypeEnum {

    /**
     * 计科
     */
    COMPUTER_TECHNOLOGY(1, "计科"),

    /**
     * 软工
     */
    SOFTWARE_ENGINEERING(2, "软工"),

    /**
     * 通信
     */
    COMMUNICATION(3, "通信"),

    /**
     * 电子
     */
    ELECTRONIC(4, "电子"),
    ;

    private Integer typeId;

    private String typeName;

    PaperTypeEnum(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
