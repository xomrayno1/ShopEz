package com.app.rest.controller;

import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.ApplicationException;
import com.app.model.Voucher;
import com.app.model.request.CreateVoucherRequest;
import com.app.model.request.DeleteRequest;
import com.app.model.request.UpdateVoucherRequest;
import com.app.model.request.VoucherPagingSearchSortModel;
import com.app.model.response.ModelResponse;
import com.app.response.APIResponse;
import com.app.response.APIStatus;
import com.app.service.VoucherService;
import com.app.utils.Constant;
import com.app.utils.ResponseUtil;

@RestController
@RequestMapping(value = Constant.VOUCHER_API)
@CrossOrigin(origins = {Constant.CROSS_ORIGIN_LOCAL_8000, Constant.CROSS_ORIGIN_LOCAL_8001, Constant.CROSS_ORIGIN_LOCAL_8080, Constant.CROSS_ORIGIN_LOCAL_3000, Constant.CROSS_ORIGIN_LOCAL_3001 })
public class VoucherRestController {
	
	private VoucherService voucherService;
 
	
	@Autowired
	ServletContext context;
	
	@Autowired
	public VoucherRestController(VoucherService voucherService ) {
		this.voucherService = voucherService;
		 
	}

	private ModelMapper mapper = new ModelMapper();
	
	private static final Logger log = LoggerFactory.getLogger(VoucherRestController.class);

	private SimpleDateFormat sdf = new SimpleDateFormat(Constant.PATTERN_DATE_DDMMYYYY);
	
	@PostMapping(Constant.VOUCHER_GET_LIST_PAGING_SORT_SEARCH_FILTER)
	public ResponseEntity<APIResponse> getListPagingSortSearchFilter(@RequestBody VoucherPagingSearchSortModel ppssm) {
		try {
			Page<Voucher> page = voucherService.doFilterSearchPagingVoucher(ppssm.getSearchKey(),
					 ppssm.getPageSize(), ppssm.getPageNumber());
 
			ModelResponse modelResponse = new ModelResponse(page.getContent(), page.getTotalElements(), page.getPageable());
			
			return ResponseUtil.responseSuccess(modelResponse);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(APIStatus.ERR_VOUCHER_LIST_IS_EMPTY);
		}
	}
 
	@GetMapping(Constant.VOUCHER_GET_DETAIL)
	public ResponseEntity<APIResponse> getVoucherDetail(@PathVariable("voucherId") long proId) {
		Voucher voucher = voucherService.findById(proId);
		if(voucher != null) {
			return ResponseUtil.responseSuccess(voucher);
		}
		throw new ApplicationException(APIStatus.ERR_VOUCHER_ID_NOT_EXIST);
	}
	
	@PostMapping(Constant.VOUCHER_DELETE)
	public ResponseEntity<APIResponse> getVoucherDetail(@RequestBody DeleteRequest deleteRequest) {
		try {
			if(deleteRequest == null) {
				throw new ApplicationException(APIStatus.ERR_VOUCHER_ID_NOT_EXIST);
			}
			for (Long proId : deleteRequest.getIds()) {
				Voucher getVoucher = voucherService.findById(proId);
				if(getVoucher == null) {
					log.error("Error delete voucher id not exist {}", proId);
					throw new ApplicationException(APIStatus.ERR_VOUCHER_ID_NOT_EXIST); 
				} 
				voucherService.delete(getVoucher);
			}
			return ResponseUtil.responseSuccess("Delete voucher successfully");
		} catch (Exception e) {
			log.error("Error delete voucher ");
			e.printStackTrace();
			throw new ApplicationException(APIStatus.ERR_VOUCHER_ID_NOT_EXIST);
		}
	}
	
	@PostMapping(Constant.VOUCHER_CREATE)
	public ResponseEntity<APIResponse> createVoucher(@RequestBody CreateVoucherRequest voucherRequest) {
		try {
			String voucherCode = "";
			Voucher voucherByCode = null;
			do {
				voucherCode = Constant.POSTFIX_CODE_VOUCHER + getPrefixRandomNumberCodeVoucher();
				voucherByCode = voucherService.findByCode(voucherCode);
			} while (voucherByCode != null);
			
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			Voucher voucher = mapper.map(voucherRequest, Voucher.class);
			voucher.setCode(voucherCode);
			voucherService.insert(voucher);
		} catch (Exception e) {
			log.error("Error create voucher", e.getMessage());
			e.printStackTrace();
			throw new ApplicationException(APIStatus.ERR_CREATE_VOUCHER);
		}
		return ResponseUtil.responseSuccess("Create voucher successfully");
	}
 
	public int getPrefixRandomNumberCodeVoucher() {
		final int MAX = 9999;
		final int MIN = 1000;
		return (int)(Math.random() * ((MAX - MIN) + 1)) + MIN;
	}
  
	@PutMapping(Constant.VOUCHER_UPDATE)
	public ResponseEntity<APIResponse> updateVoucher(@RequestBody UpdateVoucherRequest voucherRequest) {
		Voucher voucherById = voucherService.findById(voucherRequest.getId());
		if(voucherById != null) {
			try {
				mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				Voucher voucher = mapper.map(voucherRequest, Voucher.class);
				voucher.setCreatedDate(voucherById.getCreatedDate());
				voucher.setUpdatedDate(voucherById.getUpdatedDate());
				voucher.setStatus(voucherById.getStatus());
				voucher.setCode(voucherById.getCode());
				voucherService.update(voucher);
				
				log.info("Update voucher successfully");
			} catch (Exception e) {
				log.error("Error update voucher");
				e.printStackTrace();
				throw new ApplicationException(APIStatus.ERR_UPDATE_VOUCHER);
			}
		}else {
			throw new ApplicationException(APIStatus.ERR_VOUCHER_ID_NOT_EXIST);
		}
		return ResponseUtil.responseSuccess("Update voucher successfully");
	}
	
	
 
}
