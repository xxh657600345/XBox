package com.example.volley.config;

public class XHttpConfig {
	/**
	 * Base Config
	 */
	public static final String CONTENT_TYPE_KEY = "Content-Type";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";

	public static final String HTTP_HEADER_AUTH = "Authorization";

	public static final String HTTP_HEADER_ACCEPT_LANGUAGE = "Accept-Language";

	public static final String DEFAULT_ENCODER = "UTF-8";
	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final String HOME_URL = "http://182.254.153.15:8080/miliapp/";
	public static final String REQ_DATA_PARAM = "?reqData=";
	public static final String PAGE_SIZE_PARAM = "page_size=";
	public static final String DEFAULT_PAGE_SIZE_PARAM = PAGE_SIZE_PARAM
			+ DEFAULT_PAGE_SIZE;

	/**
	 * Goods
	 */
	public static final String GOODS = "control/website/jindongGoodsController/";
	public static final String GOODS_DETAIL = "get_good_detail.do";
	public static final String GOODS_BY_CATEGORY = "get_goods_by_category.do";

}
