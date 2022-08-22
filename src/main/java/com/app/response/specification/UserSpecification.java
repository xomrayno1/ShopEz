package com.app.response.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.app.model.Users;
import com.app.utils.Constant;

public class UserSpecification implements Specification<Users>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String searchKey;


	public UserSpecification(String searchKey) {
		this.searchKey = searchKey;
		
	}

	@Override
	public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		List<Predicate> predicates = new ArrayList<>();
		
		if(searchKey != null && !searchKey.trim().isEmpty()) {
			String wrapSearch = "%" + searchKey.trim() + "%";
			Predicate cateName = criteriaBuilder.like(root.get("name"), wrapSearch);
			Predicate cateCode = criteriaBuilder.like(root.get("code"), wrapSearch);
			Predicate searchPredicate = criteriaBuilder.or(cateName, cateCode);
			predicates.add(searchPredicate);
		}
		
		
		Predicate proStatus = criteriaBuilder.equal(root.get("status"), Constant.Status.ACTIVE.getValue());
		predicates.add(proStatus);

 
		return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
	}

	public String getSearchKey() {
		return searchKey;
	}

 
}
