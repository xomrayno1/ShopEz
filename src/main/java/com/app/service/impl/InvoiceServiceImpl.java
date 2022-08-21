package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.model.Invoice;
import com.app.repository.InvoiceRepository;
import com.app.response.specification.InvoiceSpecification;
import com.app.service.InvoiceService;
import com.app.utils.Constant;

@Service
public class InvoiceServiceImpl implements InvoiceService{

	private InvoiceRepository invoiceRepository;
	
	@Autowired
	public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
		this.invoiceRepository = invoiceRepository;
	}

	@Override
	public List<Invoice> findAll() {
		// TODO Auto-generated method stub
		return invoiceRepository.findAll();
	}

	@Override
	public Invoice update(Invoice instance) {
		// TODO Auto-generated method stub
		return invoiceRepository.save(instance);
	}

	@Override
	public void delete(Invoice instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.IN_ACTIVE.getValue());
		invoiceRepository.save(instance);
	}

	@Override
	public Invoice findById(long id) {
		// TODO Auto-generated method stub
		return invoiceRepository.findById(id).orElse(null);
	}

	@Override
	public Invoice insert(Invoice instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.ACTIVE.getValue());
		return invoiceRepository.save(instance);
	}

	@Override
	public Invoice findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Invoice> doFilterSearchPagingInvoice(int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		return invoiceRepository.findAll(new InvoiceSpecification(), PageRequest.of(pageNumber - 1, pageSize));
	}
 
 
}
