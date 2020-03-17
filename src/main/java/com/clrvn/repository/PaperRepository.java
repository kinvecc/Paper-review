package com.clrvn.repository;

import com.clrvn.entity.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Clrvn
 * @description
 * @className PaperRepository
 * @date 2019-05-20 23:01
 */
@Repository
@SuppressWarnings("all")
public interface PaperRepository extends JpaRepository<Paper, Integer> {

    Page<Paper> findAllByUserIdAndIsSystemOrderByCreateTimeDesc(Pageable pageable, Integer userId, Boolean isSystem);

    Page<Paper> findAllByIsSystemOrderByCreateTimeDesc(Pageable pageable, Boolean isSystem);

    int removeById(Integer id);

    List<Paper> findAllByPaperType(Integer paperType);
}
