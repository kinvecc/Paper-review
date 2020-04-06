package com.clrvn.service.impl;

import com.alibaba.fastjson.JSON;
import com.clrvn.entity.Paper;
import com.clrvn.repository.PaperRepository;
import com.clrvn.service.IPaperService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @className: PaperServiceImpl
 * @author: Clrvn
 */
@Service
public class PaperServiceImpl implements IPaperService {


    /**
     * 实例es客户端连接对象
     */
    @Autowired
    private RestHighLevelClient highLevelClient;
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
    public Page<Paper> findByOrderByPageViewDesc(Integer pageIndex) {
//        Sort sort = new Sort(Sort.Direction.ASC, "seqNum");
        return paperRepository.findByOrderByPageViewDesc(PageRequest.of(pageIndex, 5));
    }

    @Override
    public Page<Paper> findByEs(Integer pageIndex, String type, String str) throws IOException {

        //构建es请求
        SearchRequest searchRequest = new SearchRequest("es_paper");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.matchQuery(type, str));
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);

        List<Integer> paperIdList = new ArrayList<>();
        SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            paperIdList.add(Integer.valueOf(hit.getSourceAsMap().get("id").toString()));
        }
        System.err.println(JSON.toJSONString(hits));
        paperIdList.forEach(System.out::println);
        return findAllByIdIsInOrderByPageViewDesc(pageIndex, paperIdList);
    }

    /**
     * 查询所有论文通过id
     */
    @Override
    public Page<Paper> findAllByIdIsInOrderByPageViewDesc(Integer pageIndex, List<Integer> ids) {
        return paperRepository.findAllByIdIsInOrderByPageViewDesc(PageRequest.of(pageIndex, 5), ids);
    }

    /**
     * 删除论文
     *
     * @param id 论文id
     */
    @Override
    public void removePaperById(Integer id) {
        paperRepository.deleteById(id);
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
    public Paper getOneById(Integer id) {
        return paperRepository.getOne(id);
    }

    /**
     * 热度+1
     */
    @Override
    public void addPageView(Integer id) {
        Paper paper = paperRepository.getOne(id);
        paper.setPageView(paper.getPageView() + 1);
        paperRepository.saveAndFlush(paper);
    }


}
