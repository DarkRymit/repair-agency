package com.epam.finalproject.repository.specification;

import com.epam.finalproject.entity.User;
import com.epam.finalproject.search.UserSearch;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    private UserSpecifications() {
    }

    public static Specification<User> hasUsernameLike(String name) {
        return (root, query, cb) -> cb.like(root.get("username"), "%" + name + "%");
    }

    public static Specification<User> matchSearch(UserSearch userSearch) {
        return (root, query, cb) -> {
            Specification<User> result = (root1, query1, criteriaBuilder) -> criteriaBuilder.and();

            if (userSearch.getUsername() != null && !userSearch.getUsername().isBlank()){
                result = result.and(hasUsernameLike(userSearch.getUsername()));
            }

            return result.toPredicate(root,query,cb);
        };
    }

}