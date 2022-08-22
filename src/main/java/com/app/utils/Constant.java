package com.app.utils;

public interface Constant {
	
	public static final String CROSS_ORIGIN_LOCAL_8000 = "http://localhost:8000";
	public static final String CROSS_ORIGIN_LOCAL_8001 = "http://localhost:8001";
	public static final String CROSS_ORIGIN_LOCAL_8080 = "http://localhost:8080";
	public static final String CROSS_ORIGIN_LOCAL_3000 = "http://localhost:3000";
	public static final String CROSS_ORIGIN_LOCAL_3001 = "http://localhost:3001";
 
	
	public static final String API_PREFIX = "/api/v1";
	
	// product API
	public static final String PRODUCT_API = API_PREFIX + "/products";
	public static final String PRODUCT_GET_LIST_PAGING_SORT_SEARCH_FILTER = "/product_get_list_paging_sort_search_filter";
	public static final String PRODUCT_GET_DETAIL = "/product_get_detail/{proId}";
	public static final String PRODUCT_DELETE = "/product_delete";
	public static final String PRODUCT_UPDATE = "/product_update";
	public static final String PRODUCT_CREATE = "/product_create";
	public static final String PRODUCT_UPLOAD = "/product_upload";
	public static final String PRODUCT_GET_LIST_ACTIVE = "/product_get_list_active";
	public static final String PRODUCT_GET_BY_CATEGORY = "/product_get_by_category/{cateId}";
	
	// voucher API
	public static final String VOUCHER_API = API_PREFIX + "/vouchers";
	public static final String VOUCHER_GET_LIST_PAGING_SORT_SEARCH_FILTER = "/voucher_get_list_paging_sort_search_filter";
	public static final String VOUCHER_GET_DETAIL = "/voucher_get_detail/{voucherId}";
	public static final String VOUCHER_DELETE = "/voucher_delete";
	public static final String VOUCHER_UPDATE = "/voucher_update";
	public static final String VOUCHER_CREATE = "/voucher_create";
 
	//category API
	public static final String CATEGORY_API = API_PREFIX + "/categories";
	public static final String CATEGORY_GET_LIST_PAGING_SORT_SEARCH_FILTER = "/category_get_list_paging_sort_search_filter";
	public static final String CATEGORY_GET_DETAIL= "/category_get_detail/{cateId}";
	public static final String CATEGORY_DELETE = "/category_delete";
	public static final String CATEGORY_UPDATE = "/category_update";
	public static final String CATEGORY_CREATE = "/category_create";
	public static final String CATEGORY_GET_LIST_ACTIVE = "/category_get_list_active";
 
	//user api
	public static final String USER_API = API_PREFIX + "/user";
	public static final String USER_GET_LIST_PAGING_SORT_SEARCH_FILTER = "/user_get_list_paging_sort_search_filter";
	public static final String USER_GET_DETAIL= "/user_get_detail/{userId}";
	public static final String USER_DELETE = "/user_delete";
	public static final String USER_UPDATE = "/user_update";
	public static final String USER_CREATE = "/user_create";
	public static final String USER_GET_BY_LEVEL = "/user_get_by_level/{level}";
	public static final String USER_GET_BY_DEPARTMENT = "/user_get_by_department/{departmentId}";
	 
	public static final String INVOICE_API = API_PREFIX + "/invoices";
	public static final String INVOICE_GET_LIST_PAGING_SORT_SEARCH_FILTER = "/invoice_get_list_paging_sort_search_filter";
	public static final String INVOICE_GET_DETAIL= "/invoice_get_detail/{invoiceId}";
	public static final String INVOICE_DELETE = "/invoice_delete";
	public static final String INVOICE_UPDATE = "/invoice_update";
	public static final String INVOICE_CREATE = "/invoice_create";
	public static final String INVOICE_GET_LIST_ACTIVE = "/invoice_get_list_active";
	public static final String INVOICE_GET_LIST_DETAIL= "/invoice_get_list_detail/{invoiceId}";
	public static final String INVOICE_CANCEL = "/invoice_cancel";
	public static final String INVOICE_COMPLETE = "/invoice_complete";
	
	public static final String POSTFIX_CODE_VOUCHER = "VC";
 
	//auth
	
	public static final String AUTH_API = "/authenticate";
 
	
	public static final String PATH_RESOURCE = "src/main/resources";
	public static final String PATH_UPLOAD = "uploads";
 
	public enum Status {
		IN_ACTIVE(0),
		ACTIVE(1);
		
		private final int value;

		private Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}
	
	enum ShopType {
		PROVINCE(1),
		DISTRICT(2),
		WARD(3),
		VILLAGE(4)
		;
		
		private final int value;

		private ShopType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	enum WareHouseType {
		KHO_CHAN(1),
		KHO_LE(2),
		KHO_CO_SO(3),
		QUAY_THUOC(4)
		;
		
		private final int value;

		private WareHouseType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	enum ROLE {
		ADMIN(1),
		USER(2),
		;
		
		private final int value;

		private ROLE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
 
	}
	
	
	enum INVOICE_TYPE {
		IN_PROGRESS(0),
		CANCEL(1),
		COMPLETE(2),
		;
		
		private final int value;

		private INVOICE_TYPE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
 
	}
	
	public static final String PATTERN_DATE_DDMMYYYY = "dd/mm/yyyy";
	
	public static final String PATTERN_DATE_TIMESTAMP = "dd/mm/yyyy HH:mm:ss";
	
	public static final String PATTERN_DATE_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	
	
}
