package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.InvoiceDetail;
import com.app.repository.InvoiceDetailRepository;
import com.app.service.InvoiceDetailService;
import com.app.utils.Constant;

@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService{

	private InvoiceDetailRepository invoiceDetailRepository;
	
	@Autowired
	public InvoiceDetailServiceImpl(InvoiceDetailRepository invoiceDetailRepository) {
		this.invoiceDetailRepository = invoiceDetailRepository;
	}

	@Override
	public List<InvoiceDetail> findAll() {
		// TODO Auto-generated method stub
		return invoiceDetailRepository.findAll();
	}

	@Override
	public InvoiceDetail update(InvoiceDetail instance) {
		// TODO Auto-generated method stub
		return invoiceDetailRepository.save(instance);
	}

	@Override
	public void delete(InvoiceDetail instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.IN_ACTIVE.getValue());
		invoiceDetailRepository.save(instance);
	}

	@Override
	public InvoiceDetail findById(long id) {
		// TODO Auto-generated method stub
		return invoiceDetailRepository.findById(id).orElse(null);
	}

	@Override
	public InvoiceDetail insert(InvoiceDetail instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.ACTIVE.getValue());
		return invoiceDetailRepository.save(instance);
	}

	@Override
	public InvoiceDetail findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
 
 
}
