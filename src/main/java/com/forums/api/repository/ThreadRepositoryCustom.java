package com.forums.api.repository;

import com.forums.api.dto.request.thread.ThreadGetRequest;
import com.forums.api.entity.Thread;

import java.util.List;

public interface ThreadRepositoryCustom {
    public List<Thread> findThreadsByCriteria(ThreadGetRequest request, List<Long> categoryIds) ;
}
