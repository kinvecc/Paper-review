package com.clrvn.service;

import com.clrvn.entity.Paper;
import org.springframework.data.domain.Page;

import java.io.IOException;
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
    Page<Paper> findByOrderByPageViewDesc(Integer pageIndex);

    /**
     * 通过es查询
     * @param pageIndex 页码
     * @param type 查询类型（字段）
     * @param str 查询内容
     * @return 返回结果
     */
    Page<Paper> findByEs(Integer pageIndex, String type, String str) throws IOException;

    /**
     * 查询所有论文通过id
     */
    Page<Paper> findAllByIdIsInOrderByPageViewDesc(Integer pageIndex, List<Integer> ids);

    /**
     * 删除论文
     *
     * @param id 论文id
     * @return
     */
    void removePaperById(Integer id);

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

    void addPageView(Integer id);
}
