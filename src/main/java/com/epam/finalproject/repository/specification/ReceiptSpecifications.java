package com.epam.finalproject.repository.specification;

import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.ReceiptStatus;
import com.epam.finalproject.model.entity.enums.ReceiptStatusEnum;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.search.ReceiptSearch;
import com.epam.finalproject.model.search.ReceiptWithCustomerSearch;
import com.epam.finalproject.model.search.ReceiptWithMasterSearch;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.Objects;
import java.util.Set;

public class ReceiptSpecifications {

    private ReceiptSpecifications() {
    }

    public static Specification<Receipt> hasStatusWithName(Set<ReceiptStatusEnum> status) {
        if (status.isEmpty()) throw new IllegalArgumentException();
        return (root, query, cb) -> {
            Join<ReceiptStatus, Receipt> receiptStatusReceiptJoin = root.join("status");
            return receiptStatusReceiptJoin.get("name").in(status);
        };
    }

    public static Specification<Receipt> hasMasterWithNameLike(String name) {
        if (name.isBlank()) throw new IllegalArgumentException();
        return (root, query, cb) -> {
            Join<User, Receipt> masterReceiptJoin = root.join("master");
            return cb.like(masterReceiptJoin.get("username"), "%" + name + "%");
        };
    }
    public static Specification<Receipt> hasUserWithNameLike(String name) {
        if (name.isBlank()) throw new IllegalArgumentException();
        return (root, query, cb) -> {
            Join<User, Receipt> userReceiptJoin = root.join("user");
            return cb.like(userReceiptJoin.get("username"), "%" + name + "%");
        };
    }
    public static Specification<Receipt> hasCustomer(User customer) {
        Objects.requireNonNull(customer);
        return (root, query, cb) -> cb.equal(root.get("user"), customer.getId());
    }
    public static Specification<Receipt> hasMaster(User master) {
        Objects.requireNonNull(master);
        return (root, query, cb) -> cb.equal(root.get("master"), master.getId());
    }

    public static Specification<Receipt> matchSearch(ReceiptSearch receiptSearch) {
        return (root, query, cb) -> {
            Specification<Receipt> result = (root1, query1, criteriaBuilder) -> criteriaBuilder.and();

            if (receiptSearch.getReceiptStatuses() != null && !receiptSearch.getReceiptStatuses().isEmpty()){
                result = result.and(hasStatusWithName(receiptSearch.getReceiptStatuses()));
            }
            if(receiptSearch.getMasterUsername() != null &&!receiptSearch.getMasterUsername().isBlank()){
                result = result.and(hasMasterWithNameLike(receiptSearch.getMasterUsername()));
            }
            if(receiptSearch.getCustomerUsername() != null &&!receiptSearch.getCustomerUsername().isBlank()){
                result = result.and(hasUserWithNameLike(receiptSearch.getCustomerUsername()));
            }
            return result.toPredicate(root,query,cb);
        };
    }

    public static Specification<Receipt> matchSearch(ReceiptWithCustomerSearch receiptSearch) {
        return (root, query, cb) -> {
            Specification<Receipt> result = hasCustomer(receiptSearch.getCustomer());

            if (receiptSearch.getReceiptStatuses() != null && !receiptSearch.getReceiptStatuses().isEmpty()){
                result = result.and(hasStatusWithName(receiptSearch.getReceiptStatuses()));
            }
            if(receiptSearch.getMasterUsername() != null &&!receiptSearch.getMasterUsername().isBlank()){
                result = result.and(hasMasterWithNameLike(receiptSearch.getMasterUsername()));
            }
            return result.toPredicate(root,query,cb);
        };
    }

    public static Specification<Receipt> matchSearch(ReceiptWithMasterSearch receiptSearch) {
        return (root, query, cb) -> {
            Specification<Receipt> result = hasMaster(receiptSearch.getMaster());

            if (receiptSearch.getReceiptStatuses() != null && !receiptSearch.getReceiptStatuses().isEmpty()){
                result = result.and(hasStatusWithName(receiptSearch.getReceiptStatuses()));
            }
            if(receiptSearch.getCustomerUsername() != null &&!receiptSearch.getCustomerUsername().isBlank()){
                result = result.and(hasUserWithNameLike(receiptSearch.getCustomerUsername()));
            }
            return result.toPredicate(root,query,cb);
        };
    }
}