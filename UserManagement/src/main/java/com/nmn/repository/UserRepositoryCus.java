package com.nmn.repository;

import com.nmn.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryCus {
    @Autowired
    EntityManager em;
    public List getListUser(Map<String, String> params){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> userRoot = q.from(User.class);
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String firstName = params.get("firstName");
            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(cb.like(userRoot.get("name"), String.format("%%%s%%", firstName)));
            }

            String lastName = params.get("lastName");
            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(cb.like(userRoot.get("lastName"), String.format("%%%s%%", lastName)));
            }

            String email = params.get("email");
            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(cb.like(userRoot.get("email"), String.format("%%%s%%", email)));
            }


            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = em.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int pageSize = Integer.parseInt("2");
                query.setFirstResult((Integer.parseInt(page) - 1) * pageSize);
                query.setMaxResults(pageSize);
            }
        }
        return query.getResultList();
    }

}
