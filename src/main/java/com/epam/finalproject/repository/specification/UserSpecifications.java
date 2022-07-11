package com.epam.finalproject.repository.specification;

import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.model.search.MasterSearch;
import com.epam.finalproject.model.search.UserSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.Objects;
import java.util.Set;

public class UserSpecifications {

    private UserSpecifications() {
    }

    public static Specification<User> hasUsernameLike(String name) {
        Objects.requireNonNull(name);
        if (name.isBlank()) throw new IllegalArgumentException();
        return (root, query, cb) -> cb.like(root.get("username"), "%" + name + "%");
    }
    public static Specification<User> hasRoleWithName(Set<RoleEnum> names) {
        if (names.isEmpty()) throw new IllegalArgumentException();
        return (root, query, cb) -> {
            Join<Role, User> roleReceiptJoin = root.join("roles");
            return roleReceiptJoin.get("name").in(names);
        };
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

    public static Specification<User> matchSearch(MasterSearch masterSearch) {
        return (root, query, cb) -> {
            Specification<User> result = hasRoleWithName(Set.of(RoleEnum.MASTER));

            if (masterSearch.getUsername() != null && !masterSearch.getUsername().isBlank()){
                result = result.and(hasUsernameLike(masterSearch.getUsername()));
            }

            return result.toPredicate(root,query,cb);
        };
    }
}