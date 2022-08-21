package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Invoice;
import com.app.model.InvoiceDetail;
@Repository
public interface InvoiceDetailRepository  extends CrudRepository<InvoiceDetail, Long>, JpaSpecificationExecutor<InvoiceDetail>{

	@Override
	List<InvoiceDetail> findAll();
 
	List<InvoiceDetail> findByInvoice(Invoice invoice);
}
