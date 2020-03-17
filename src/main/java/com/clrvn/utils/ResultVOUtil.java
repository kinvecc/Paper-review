package com.clrvn.utils;


import com.clrvn.enums.ResultFailureEnum;
import com.clrvn.vo.ResultVO;

public class ResultVOUtil {

    public static <T> ResultVO success(T object) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(200);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO failure(ResultFailureEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(500);
        resultVO.setMsg(resultEnum.getMsg());
        return resultVO;
    }
}
