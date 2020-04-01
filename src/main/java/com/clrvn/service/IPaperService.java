package com.clrvn.service;

import com.clrvn.entity.Paper;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @description:
 * @className:IPaperService
 * @author: Clrvn
 */
public interface IPaperService {

    List<Paper> findAll();
    /**
     * 查询所有的论文
     *
     * @param pageIndex 从0 开始
     * @return 论文分页
     */
    Page<Paper> findAllByPaperNameContainsOrderByPageViewDesc(Integer pageIndex, String paperName);
    /**
     * 删除论文
     *
     * @param id 论文id
     * @return
     */
    int removePaperById(Long id);

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
    Paper getOneById(Long id);

}
