package com.app.service;

import org.springframework.data.domain.Page;

import com.app.model.Invoice;

public interface InvoiceService extends BaseService<Invoice>{
	Page<Invoice> doFilterSearchPagingInvoice(Long userId, Integer type, int pageSize, int pageNumber);
}
