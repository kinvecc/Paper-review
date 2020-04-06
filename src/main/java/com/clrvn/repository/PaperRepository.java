package com.clrvn.repository;

import com.clrvn.entity.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Clrvn
 * @description
 * @className PaperRepository
 */
@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {

    Page<Paper> findByOrderByPageViewDesc(Pageable pageable);

    Page<Paper> findAllByIdIsInOrderByPageViewDesc(Pageable pageable, List<Integer> idsList);


}
