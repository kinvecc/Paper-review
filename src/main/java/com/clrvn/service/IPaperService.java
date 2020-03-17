package com.clrvn.service;

import com.clrvn.entity.Paper;
import org.springframework.data.domain.Page;

/**
 * @description:
 * @className:IPaperService
 * @author: Clrvn
 * @date: 2019-05-20 23:07
 */
public interface IPaperService {

    /**
     * 查询所有的论文
     *
     * @param userId    用户id
     * @param pageIndex 从0 开始
     * @return 论文分页
     */
    Page<Paper> findAllByUserIdAndSystem(Integer pageIndex, Integer userId, Boolean isSystem);

    Page<Paper> findAllBySystem(Integer pageIndex, Boolean isSystem);

    /**
     * 删除论文
     *
     * @param id 论文id
     * @return
     */
    int removePaperById(Integer id);

    /**
     * 新增论文
     *
     * @param paper 新增的
     * @return 返回刚新增的
     */
    Paper savePaper(Paper paper);

    /**
     * 查询论文
     *
     * @param id 论文id
     * @return 论文对象
     */
    Paper getOneById(Integer id);

    /**
     * 查询系统论文
     *
     * @param paperType
     * @return
     */
    Paper getSystemPaperByType(Integer paperType);
}
