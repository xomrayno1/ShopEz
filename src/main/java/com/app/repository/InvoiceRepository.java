package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Invoice;
@Repository
public interface InvoiceRepository  extends CrudRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice>{

	@Override
	List<Invoice> findAll();
  
}
