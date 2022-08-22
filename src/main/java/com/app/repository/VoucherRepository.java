/**
 * 
 */
package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.app.model.Product;
import com.app.model.Voucher;

public interface VoucherRepository extends CrudRepository<Voucher, Long>, JpaSpecificationExecutor<Voucher>{

	@Override
	List<Voucher> findAll();
	
	Optional<Voucher> findByName(String name);
	
	Optional<Voucher> findByCode(String code);
}
