package com.app.rest.controller;

import java.text.SimpleDateFormat;
import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.ApplicationException;
import com.app.model.Category;
import com.app.model.Product;
import com.app.model.request.CreateProductRequest;
import com.app.model.request.DeleteRequest;
import com.app.model.request.ProductPagingSearchSortModel;
import com.app.model.request.UpdateProductRequest;
import com.app.model.response.ModelResponse;
import com.app.response.APIResponse;
import com.app.response.APIStatus;
import com.app.service.CategoryService;
import com.app.service.ProductService;
import com.app.utils.AppUtils;
import com.app.utils.Constant;
import com.app.utils.ResponseUtil;

@RestController
@RequestMapping(value = Constant.PRODUCT_API)
@CrossOrigin(origins = {Constant.CROSS_ORIGIN_LOCAL_8000, Constant.CROSS_ORIGIN_LOCAL_8001, Constant.CROSS_ORIGIN_LOCAL_8080, Constant.CROSS_ORIGIN_LOCAL_3000, Constant.CROSS_ORIGIN_LOCAL_3001 })
public class ProductRestController {
	
	private ProductService productService;
	private CategoryService categoryService;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	public ProductRestController(ProductService productService, CategoryService categoryService ) {
		this.productService = productService;
		this.categoryService = categoryService;	 
	}

	private ModelMapper mapper = new ModelMapper();
	
	private static final Logger log = LoggerFactory.getLogger(ProductRestController.class);

	private SimpleDateFormat sdf = new SimpleDateFormat(Constant.PATTERN_DATE_DDMMYYYY);
	
	@PostMapping(Constant.PRODUCT_GET_LIST_PAGING_SORT_SEARCH_FILTER)
	public ResponseEntity<APIResponse> getListPagingSortSearchFilter(@RequestBody ProductPagingSearchSortModel ppssm) {
		try {
			Page<Product> page = productService.doFilterSearchPagingProduct(ppssm.getSearchKey(),
					 ppssm.getPageSize(), ppssm.getPageNumber());
 
			ModelResponse modelResponse = new ModelResponse(page.getContent(), page.getTotalElements(), page.getPageable());
			
			return ResponseUtil.responseSuccess(modelResponse);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(APIStatus.ERR_PRODUCT_LIST_IS_EMPTY);
		}
	}
 
	@GetMapping(Constant.PRODUCT_GET_DETAIL)
	public ResponseEntity<APIResponse> getProductDetail(@PathVariable("proId") long proId) {
		Product product = productService.findById(proId);
		if(product != null) {
			return ResponseUtil.responseSuccess(product);
		}
		throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST);
	}
	
	@PostMapping(Constant.PRODUCT_DELETE)
	public ResponseEntity<APIResponse> getProductDetail(@RequestBody DeleteRequest deleteRequest) {
		try {
			if(deleteRequest == null) {
				throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST);
			}
			for (Long proId : deleteRequest.getIds()) {
				Product getProduct = productService.findById(proId);
				if(getProduct == null) {
					log.error("Error delete product id not exist {}", proId);
					throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST); 
				} 
				getProduct.setStatus(Constant.Status.IN_ACTIVE.getValue());
				productService.delete(getProduct);
			}
			return ResponseUtil.responseSuccess("Delete product successfully");
		} catch (Exception e) {
			log.error("Error delete product ");
			e.printStackTrace();
			throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST);
		}
	}
	
	@PostMapping(Constant.PRODUCT_CREATE)
	public ResponseEntity<APIResponse> createProduct(@ModelAttribute CreateProductRequest productRequest) {

		Product productByCode = productService.findByCode(productRequest.getCode());
		if (productByCode == null) {
			try {
				mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
					
				Product product = mapper.map(productRequest, Product.class);
				Category category = categoryService.findById(productRequest.getCategoryId());
				if(category == null) {
					throw new ApplicationException(APIStatus.ERR_CATEGORY_ID_NOT_EXIST);
				}
				product.setCategory(category);
				if(productRequest.getImageFile() != null && !productRequest.getImageFile().isEmpty()) {
					String imgName = AppUtils.uploadFile(productRequest.getImageFile());
					product.setImageUrl("/upload/" +imgName);	 
				}
				
				productService.insert(product);
				log.info("Create product successfully");
			} catch (Exception e) {
				log.error("Error create product", e.getMessage());
				e.printStackTrace();
				throw new ApplicationException(APIStatus.ERR_CREATE_PRODUCT);
			}
		} else {
			log.error("Error create product code already exists {}", productByCode.getCode());
			throw new ApplicationException(APIStatus.ERR_PRODUCT_CODE_ALREADY_EXISTS);
		}
		return ResponseUtil.responseSuccess("Create product successfully");
	}
 
	
	@PutMapping(Constant.PRODUCT_UPDATE)
	public ResponseEntity<APIResponse> updateProduct(@ModelAttribute UpdateProductRequest productRequest) {
		
		Product productByCode = productService.findByName(productRequest.getCode());
		Product productById = productService.findById(productRequest.getId());
		if(productById != null) {
			if(productByCode != null) {
				if(!productByCode.getCode().equals(productRequest.getCode())) {
					log.error("Error update product code already exists {}", productRequest.getCode());
					throw new ApplicationException(APIStatus.ERR_PRODUCT_CODE_ALREADY_EXISTS);
				} 
			}
			try {
				mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				Product product = mapper.map(productRequest, Product.class);
				Category category = categoryService.findById(productRequest.getCategoryId());
				if(productRequest.getImageFile() != null && !productRequest.getImageFile().isEmpty()) {
					String imgName = AppUtils.uploadFile(productRequest.getImageFile());
					product.setImageUrl("/upload/" +imgName);
					
				}
				product.setImageUrl(productById.getImageUrl());
				product.setStatus(productById.getStatus());
				product.setCreatedDate(productById.getCreatedDate());
				product.setCategory(category);
				productService.update(product);
				
				log.info("Update product successfully");
			} catch (Exception e) {
				log.error("Error update product");
				e.printStackTrace();
				throw new ApplicationException(APIStatus.ERR_UPDATE_PRODUCT);
			}
		}else {
			throw new ApplicationException(APIStatus.ERR_PRODUCT_ID_NOT_EXIST);
		}
		return ResponseUtil.responseSuccess("Update product successfully");
	}
	
	@GetMapping(Constant.PRODUCT_GET_BY_CATEGORY)
	public ResponseEntity<APIResponse> getByCategory(@PathVariable("cateId") long cateId) {
		Category category = categoryService.findById(cateId);
		if(category != null) {
			List<Product> products = productService.findByCategory(category);
			return ResponseUtil.responseSuccess(products);
		}
		throw new ApplicationException(APIStatus.ERR_CATEGORY_ID_NOT_EXIST);
	}
}
