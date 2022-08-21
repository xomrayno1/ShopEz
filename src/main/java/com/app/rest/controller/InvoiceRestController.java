package com.app.rest.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.ApplicationException;
import com.app.model.Invoice;
import com.app.model.InvoiceDetail;
import com.app.model.Product;
import com.app.model.Users;
import com.app.model.request.CreateInvoiceDetailRequest;
import com.app.model.request.CreateInvoiceRequest;
import com.app.model.request.DeleteRequest;
import com.app.model.request.InvoicePagingSearchSortModel;
import com.app.repository.InvoiceDetailRepository;
import com.app.response.APIResponse;
import com.app.response.APIStatus;
import com.app.service.InvoiceService;
import com.app.service.ProductService;
import com.app.service.UserService;
import com.app.utils.Constant;
import com.app.utils.ResponseUtil;


@RestController
@RequestMapping(value = Constant.INVOICE_API)
@CrossOrigin(origins = {Constant.CROSS_ORIGIN_LOCAL_8000, Constant.CROSS_ORIGIN_LOCAL_8001, Constant.CROSS_ORIGIN_LOCAL_8080 })
public class InvoiceRestController {

	private InvoiceService invoiceService;
	
	private UserService userService;
 
	
	private ProductService productService;
	 
	private ModelMapper mapper = new ModelMapper();
	
	private InvoiceDetailRepository invoiceDetailRepo;
	
	private static final Logger log = LoggerFactory.getLogger(InvoiceRestController.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat(Constant.PATTERN_DATE_DDMMYYYY);

	@Autowired
	public InvoiceRestController(InvoiceService invoiceService, UserService userService, ProductService productService,
			InvoiceDetailRepository invoiceDetailRepo) {
		this.invoiceService = invoiceService;
		this.userService = userService;
		this.productService = productService;
		this.invoiceDetailRepo = invoiceDetailRepo;
	}
 
	
	@PostMapping(value = Constant.INVOICE_GET_LIST_PAGING_SORT_SEARCH_FILTER)
	public ResponseEntity<APIResponse> getListPagingSortSearchFilter(@RequestBody InvoicePagingSearchSortModel cpssm){
		Page<Invoice> invoices =  invoiceService.doFilterSearchPagingInvoice(cpssm.getPageSize(), cpssm.getPageNumber());
		try {
			if(invoices == null) {
				throw new ApplicationException(APIStatus.ERR_INVOICE_LIST_IS_EMPTY);
			}
			log.info("get list filter successfully");
			return ResponseUtil.responseSuccess(invoices);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error invoice list is empty");
			throw new ApplicationException(APIStatus.ERR_INVOICE_LIST_IS_EMPTY);
		}
	}
	
	@GetMapping(value = Constant.INVOICE_GET_DETAIL)
	public ResponseEntity<APIResponse> getInvoiceDetail(@PathVariable("invoiceId") long invoiceId){
		try {
			Invoice invoice = invoiceService.findById(invoiceId);
			if(invoice == null) {
				throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
			}
			log.info("get invoice detail successfully");
			return ResponseUtil.responseSuccess(invoice);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error invoice id not exists");
			throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
		}
	}
	
	@GetMapping(value = Constant.INVOICE_GET_LIST_DETAIL)
	public ResponseEntity<APIResponse> getInvoiceListDetail(@PathVariable("invoiceId") long invoiceId){
		try {
			Invoice invoice = invoiceService.findById(invoiceId);
			if(invoice == null) {
				throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
			}
			List<InvoiceDetail> list = invoiceDetailRepo.findByInvoice(invoice);
			
			log.info("get invoice detail successfully");
			return ResponseUtil.responseSuccess(list);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error invoice id not exists");
			throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
		}
	}
	
	@PostMapping(value = Constant.INVOICE_CREATE)
	public ResponseEntity<APIResponse> createInvoice(@Validated @RequestBody CreateInvoiceRequest invoiceRequest){
		try {
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			Invoice invoice = mapper.map(invoiceRequest, Invoice.class);
			 
			invoice.setType(Constant.INVOICE_TYPE.IN_PROGRESS.getValue());
			Users users = userService.findById(invoiceRequest.getUserId());
			invoice.setShipPrice(BigDecimal.ZERO);
			invoice.setUsers(users); 
			invoice.setDate(new Date());
			Invoice newInvoice = invoiceService.insert(invoice);
			BigDecimal totalAmount = BigDecimal.ZERO;
			for(CreateInvoiceDetailRequest detail: invoiceRequest.getDetails()) {
				InvoiceDetail invoiceDetail = new InvoiceDetail();
				Product product = productService.findById(detail.getProductId());
				invoiceDetail.setProductt(product);
				invoiceDetail.setPrice(detail.getPrice());
				invoiceDetail.setQuantity(detail.getQuantity());
				invoiceDetail.setAmout(detail.getAmout());
				invoiceDetail.setInvoice(newInvoice);
				totalAmount = totalAmount.add(detail.getAmout() != null ? detail.getAmout() : BigDecimal.ZERO);
				invoiceDetailRepo.save(invoiceDetail);
			}
			//totalAmount = totalAmount.add(BigDecimal.ZERO);
			newInvoice.setTotalAmount(totalAmount);
			invoiceService.update(newInvoice);
 
			log.info("create invoice successfully");
			return ResponseUtil.responseSuccess("Create invoice successfully");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error create invoice");
			e.printStackTrace();
			throw new ApplicationException(APIStatus.ERR_CREATE_INVOICE);
		}
	}
	
	@PostMapping(value = Constant.INVOICE_DELETE)
	public ResponseEntity<APIResponse> deleteInvoice(@RequestBody DeleteRequest deleteRequest){
		try {
			if(deleteRequest  != null && deleteRequest.getIds() != null) {
				for(Long id : deleteRequest.getIds()) {
					Invoice invoice = invoiceService.findById(id);
					if(invoice == null) {
						throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
					}
					invoiceService.delete(invoice);
				}
			}
 
			log.info("delete invoice successfully");
			return ResponseUtil.responseSuccess("Delete invoice successfully");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error delete invoice id not exist");
			throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
		}
	}
	
	@PostMapping(value = Constant.INVOICE_CANCEL)
	public ResponseEntity<APIResponse> cancelInvoice(@RequestBody DeleteRequest deleteRequest){
		try {
			if(deleteRequest  != null && deleteRequest.getIds() != null) {
				for(Long id : deleteRequest.getIds()) {
					Invoice invoice = invoiceService.findById(id);
					if(invoice == null) {
						throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
					}
					invoice.setType(Constant.INVOICE_TYPE.CANCEL.getValue());
					invoiceService.update(invoice);
				}
			}
 
			log.info("delete invoice successfully");
			return ResponseUtil.responseSuccess("Delete invoice successfully");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error delete invoice id not exist");
			throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
		}
	}
	
	@PostMapping(value = Constant.INVOICE_COMPLETE)
	public ResponseEntity<APIResponse> cancelComplete(@RequestBody DeleteRequest deleteRequest){
		try {
			if(deleteRequest  != null && deleteRequest.getIds() != null) {
				for(Long id : deleteRequest.getIds()) {
					Invoice invoice = invoiceService.findById(id);
					if(invoice == null) {
						throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
					}
					invoice.setType(Constant.INVOICE_TYPE.COMPLETE.getValue());
					invoiceService.update(invoice);
				}
			}
 
			log.info("delete invoice successfully");
			return ResponseUtil.responseSuccess("Delete invoice successfully");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error delete invoice id not exist");
			throw new ApplicationException(APIStatus.ERR_INVOICE_ID_NOT_EXIST);
		}
	}
 
}
