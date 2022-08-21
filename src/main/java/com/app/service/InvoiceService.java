package com.app.service;

import org.springframework.data.domain.Page;

import com.app.model.Invoice;

public interface InvoiceService extends BaseService<Invoice>{
	Page<Invoice> doFilterSearchPagingInvoice(int pageSize, int pageNumber);
}
