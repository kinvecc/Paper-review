package com.clrvn.service.impl;

import com.clrvn.entity.Paper;
import com.clrvn.repository.PaperRepository;
import com.clrvn.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @className: PaperServiceImpl
 * @author: Clrvn
 */
@Service
public class PaperServiceImpl implements IPaperService {

    @Autowired
    private PaperRepository paperRepository;


    @Override
    public List<Paper> findAll() {
        return paperRepository.findAll();
    }

    /**
     * 查询所有的论文
     *
     * @param pageIndex 从0 开始
     * @return 论文分页
     */
    @Override
    public Page<Paper> findAllByPaperNameContainsOrderByPageViewDesc(Integer pageIndex, String paperName) {
        return paperRepository.findAllByPaperNameContainsOrderByPageViewDesc(PageRequest.of(pageIndex, 5), paperName);
    }

    /**
     * 删除论文
     *
     * @param id 论文id
     */
    @Override
    public int removePaperById(Long id) {
        return paperRepository.removeById(id);
    }

    @Override
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
    public Paper getOneById(Long id) {
        return paperRepository.getOne(id);
    }


}
