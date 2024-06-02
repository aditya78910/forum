package com.forums.api.repository;


import com.forums.api.dto.request.thread.ThreadGetRequest;
import com.forums.api.entity.Thread;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ThreadRepositoryImpl implements ThreadRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Thread> findThreadsByCriteria(ThreadGetRequest request, List<Long> categoryIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Thread> cq = cb.createQuery(Thread.class);
        Root<Thread> root = cq.from(Thread.class);

        Predicate finalPredicate = root.get("category").get("id").in(categoryIds);

        if(!ObjectUtils.isEmpty(request.getAuthors())) {
            // Combine predicates using AND
             finalPredicate = cb.and(finalPredicate, root.get("username").in(request.getAuthors()));
        }

        // Apply sorting by created_at

        if(request.isNewest_first()) {
            cq.orderBy(cb.desc(root.get("createdAt")));
        }else{
            cq.orderBy(cb.asc(root.get("createdAt")));
        }

        cq.where(finalPredicate);

        if(ObjectUtils.isEmpty(request.getPage()) || ObjectUtils.isEmpty(request.getPage_size()))
            return entityManager.createQuery(cq).getResultList();
        else{
            int startIndex = request.getPage() * request.getPage_size();
            return entityManager.createQuery(cq)
                    .setFirstResult(startIndex)
                    .setMaxResults(request.getPage_size())
                    .getResultList();
        }
    }

}
