package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.model.Voucher;
import com.app.repository.VoucherRepository;
import com.app.response.specification.VoucherSpecification;
import com.app.service.VoucherService;
import com.app.utils.Constant;

@Service
public class VoucherServiceImpl implements VoucherService{
	 
	private VoucherRepository vouRepo;
	
	@Autowired
	public VoucherServiceImpl(VoucherRepository vouRepo) {
		this.vouRepo = vouRepo;
	}

	@Override
	public List<Voucher> findAll() {
		// TODO Auto-generated method stub
		return vouRepo.findAll();
	}

	@Override
	public Voucher update(Voucher instance) {
		// TODO Auto-generated method stub
		return vouRepo.save(instance);
	}

	@Override
	public void delete(Voucher instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.IN_ACTIVE.getValue());
		vouRepo.save(instance);
	}

	@Override
	public Voucher findById(long id) {
		// TODO Auto-generated method stub
		return vouRepo.findById(id).orElse(null);
	}

	@Override
	public Voucher insert(Voucher instance) {
		// TODO Auto-generated method stub
		instance.setStatus(Constant.Status.ACTIVE.getValue());
		return vouRepo.save(instance);
	}

	@Override
	public Voucher findByName(String name) {
		// TODO Auto-generated method stub
		return vouRepo.findByName(name).orElse(null);
	}

	@Override
	public Page<Voucher> doFilterSearchPagingVoucher(String searchKey, int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		return vouRepo.findAll(new VoucherSpecification(searchKey),
				PageRequest.of(pageNumber - 1, pageSize));
	}

	@Override
	public Voucher findByCode(String code) {
		// TODO Auto-generated method stub
		return vouRepo.findByCode(code).orElse(null);
	}

}
