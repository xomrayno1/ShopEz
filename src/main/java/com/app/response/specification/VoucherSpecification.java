package com.app.response.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.app.model.Voucher;
import com.app.utils.Constant;
 
public class VoucherSpecification implements Specification<Voucher>{

	private String searchKey;
 
	public VoucherSpecification(String searchKey ) {
		 
		this.searchKey = searchKey;
	 
	}

	@Override
	public Predicate toPredicate(Root<Voucher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		List<Predicate> predicates = new ArrayList<>();
		
		if(searchKey != null && !searchKey.trim().isEmpty()) {
			String wrapSearch = "%" + searchKey.trim() +"%";
			Predicate proName = criteriaBuilder.like(root.get("name"), wrapSearch);
			Predicate proCode = criteriaBuilder.like(root.get("code"), wrapSearch);
			Predicate searchPredicate = criteriaBuilder.or(proName, proCode);
			predicates.add(searchPredicate);
		}
 
		Predicate proStatus = criteriaBuilder.equal(root.get("status"), Constant.Status.ACTIVE.getValue());
		predicates.add(proStatus);
 
		return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
 
	 
	
}
