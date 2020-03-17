package com.clrvn.enums;

import lombok.Getter;

/**
 * @author Clrvn
 */

@Getter
public enum ResultFailureEnum {
    /**
     * 登录失败
     */
    LOGIN_ERROR(1, "登录失败"),

    /**
     * 删除论文失败
     */
    REMOVE_PAPER_FAILURE(2, "删除论文失败"),

    /**
     * 删除论文失败
     */
    ADD_PAPER_FAILURE(3, "添加论文失败"),
    ;

    private Integer code;

    private String msg;

    ResultFailureEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
