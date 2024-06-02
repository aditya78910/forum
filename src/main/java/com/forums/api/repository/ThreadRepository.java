package com.forums.api.repository;

import com.forums.api.entity.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadRepository extends JpaRepository<Thread, Long>, ThreadRepositoryCustom {

    @Override
    Page<Thread> findAll(Pageable pageable);

    Page<Thread> findAllByCategoryIdIn(Pageable pageable, List<Long> ids);


    List<Thread> findAllByCategoryIdIn(List<Long> ids, Sort sortOrder);
}
