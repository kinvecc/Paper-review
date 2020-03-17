package com.clrvn.service.impl;

import com.clrvn.entity.Paper;
import com.clrvn.repository.PaperRepository;
import com.clrvn.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @className: PaperServiceImpl
 * @author: Clrvn
 * @date: 2019-05-20 23:07
 */
@Service
public class PaperServiceImpl implements IPaperService {

    @Autowired
    private PaperRepository paperRepository;

    /**
     * 查询所有的论文
     *
     * @param userId    用户id
     * @param pageIndex 从0 开始
     * @return 论文分页
     */
    @Override
    public Page<Paper> findAllByUserIdAndSystem(Integer pageIndex, Integer userId, Boolean isSystem) {
        return paperRepository.findAllByUserIdAndIsSystemOrderByCreateTimeDesc(PageRequest.of(pageIndex, 3), userId, isSystem);
    }

    @Override
    public Page<Paper> findAllBySystem(Integer pageIndex, Boolean isSystem) {
        return paperRepository.findAllByIsSystemOrderByCreateTimeDesc(PageRequest.of(pageIndex, 3), isSystem);
    }

    /**
     * 删除论文
     *
     * @param id 论文id
     * @return
     */
    @Override
    @Transactional
    public int removePaperById(Integer id) {
        return paperRepository.removeById(id);
    }

    @Override
    @Transactional
    public Paper savePaper(Paper paper) {
        return paperRepository.saveAndFlush(paper);
    }

    /**
     * 查询论文
     *
     * @param id 论文id
     * @return 论文对象
     */
    @Override
    public Paper getOneById(Integer id) {
        return paperRepository.getOne(id);
    }

    /**
     * 查询系统论文
     *
     * @param paperType
     * @return
     */
    @Override
    public Paper getSystemPaperByType(Integer paperType) {
        List<Paper> allByPaperType = paperRepository.findAllByPaperType(paperType);
        return allByPaperType == null ? null : allByPaperType.get(0);
    }

}
