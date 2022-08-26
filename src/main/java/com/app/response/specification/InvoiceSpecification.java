package com.app.response.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.app.model.Invoice;
import com.app.model.Users;
import com.app.utils.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
@Getter
@Setter
public class InvoiceSpecification implements Specification<Invoice>{
	 
	private Long userId;
	private Integer type;

	public InvoiceSpecification(Long userId, Integer type  ) {
		this.userId = userId;
		this.type = type;
	}
		
	@Override
	public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		List<Predicate> predicates = new ArrayList<>();
		
		if(type != null) {
			Predicate proType = criteriaBuilder.equal(root.get("type"), type);
			predicates.add(proType);
		}
  
		if(userId != null) {
			Join<Users, Users> wareHouse  =  root.join("users", JoinType.INNER); //entity
			Predicate proWarehouseId = criteriaBuilder.equal(wareHouse.get("id"), userId);
			predicates.add(proWarehouseId);
		}
 
		return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
	}

	
	
}
