package com.app.response.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.app.model.Category;
import com.app.utils.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
@Getter
@Setter
public class CategorySpecification implements Specification<Category>{
	private final String searchKey;
 
 
	
	public CategorySpecification(String searchKey ) {
		this.searchKey = searchKey;
	}


	@Override
	public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		List<Predicate> predicates = new ArrayList<>();
		
		if(searchKey != null && !searchKey.trim().isEmpty()) {
			String wrapSearch = "%" + searchKey.trim() + "%";
			Predicate cateName = criteriaBuilder.like(root.get("name"), wrapSearch);
			predicates.add(cateName);
		}
		
		Predicate proStatus = criteriaBuilder.equal(root.get("status"), Constant.Status.ACTIVE);
		predicates.add(proStatus);
	 
		
		return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
	}

	
	
}
