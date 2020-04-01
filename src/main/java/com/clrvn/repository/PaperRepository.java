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
 */
@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {

    int removeById(Long id);

    Page<Paper> findAllByPaperNameContainsOrderByPageViewDesc(Pageable pageable, String paperName);

}
