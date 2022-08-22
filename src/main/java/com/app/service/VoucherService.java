/**
 * 
 */
package com.app.service;

import org.springframework.data.domain.Page;

import com.app.model.Voucher;

public interface VoucherService extends BaseService<Voucher> {
	Page<Voucher> doFilterSearchPagingVoucher(String searchKey, int pageSize, int pageNumber);
	 
	Voucher findByCode(String code);
	
}
