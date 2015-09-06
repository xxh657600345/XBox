package com.example.volley.config;

public interface XMessageType {
	String BUNDLE_DATA_KET_ERROR_CODE = "error_code";

	public interface AccountMessage {
		int BASE = 10000000;

		int REGISTER_SUCCESS = BASE + 1;
		int REGISTER_FAIL = BASE + 2;

		int LOGIN_SUCCESS = BASE + 3;
		int LOGIN_FAIL = BASE + 4;

		int GET_CODE_SUCCESS = BASE + 5;
		int GET_CODE_FAIL = BASE + 6;

		int LOGOUT_SUCCESS = BASE + 7;
		int LOGOUT_FAIL = BASE + 8;

		int CHANGE_PASSWORD_SUCCESS = BASE + 9;
		int CHANGE_PASSWORD_FAIL = BASE + 10;

		int FIND_PASSWORD_SUCCESS = BASE + 11;
		int FIND_PASSWORD_FAIL = BASE + 12;

		int GET_USER_INFO_SUCCESS = BASE + 13;
		int GET_USER_INFO_FAIL = BASE + 14;

		int UPDATE_USER_INFO_SUCCESS = BASE + 15;
		int UPDATE_USER_INFO_FAIL = BASE + 16;

		int SIGN_IN_SUCCESS = BASE + 17;
		int SIGN_IN_FAIL = BASE + 18;

		int SHAKE_SUCCESS = BASE + 19;
		int SHAKE_FAIL = BASE + 20;

		int UPLOAD_HEADER_SUCCESS = BASE + 21;
		int UPLOAD_HEADER_FAIL = BASE + 22;
	}

	public interface MarketMessage {
		int BASE = 20000000;

		int GET_ITEM_LIST_SUCCESS = BASE + 1;
		int GET_ITEM_LIST_FAIL = BASE + 2;

		int GET_NEW_LIST_SUCCESS = BASE + 3;
		int GET_NEW_LIST_FAIL = BASE + 4;

		int GET_HOT_LIST_SUCCESS = BASE + 5;
		int GET_HOT_LIST_FAIL = BASE + 6;

		int GET_BEST_LIST_SUCCESS = BASE + 7;
		int GET_BEST_LIST_FAIL = BASE + 8;

		int GET_ADS_SUCCESS = BASE + 9;
		int GET_ADS_FAIL = BASE + 10;
	}

	public interface SearchMessage {
		int BASE = 30000000;

		int SEARCH_GOODS_SUCCESS = BASE + 1;
		int SEARCH_GOODS_FAIL = BASE + 2;
	}

	public interface GoodsMessage {
		int BASE = 40000000;

		int GET_GOODS_LIST_SUCCESS = BASE + 1;
		int GET_GOODS_LIST_FAIL = BASE + 2;

		int GET_GOODS_DETAIL_SUCCESS = BASE + 3;
		int GET_GOODS_DETAIL_FAIL = BASE + 4;

		int GET_NEW_SUCCESS = BASE + 5;
		int GET_NEW_LIST_FAIL = BASE + 6;

		int GET_HOT_SUCCESS = BASE + 7;
		int GET_HOT_FAIL = BASE + 8;

		int GET_BEST_SUCCESS = BASE + 9;
		int GET_BEST_FAIL = BASE + 10;

		int GET_GIFT_SUCCESS = BASE + 11;
		int GET_GIFT_FAIL = BASE + 12;

		int GET_LIKE_SUCCESS = BASE + 13;
		int GET_LIKE_FAIL = BASE + 14;

		int LIKE_SUCCESS = BASE + 15;
		int LIKE_FAIL = BASE + 16;

		int UNLIKE_SUCCESS = BASE + 17;
		int UNLIKE_FAIL = BASE + 18;

		int GET_FOUR_GOODS_SUCCESS = BASE + 19;
		int GET_FOUR_GOODS_FAIL = BASE + 20;

		int GET_VVIP_SUCCESS = BASE + 21;
		int GET_VVIP_FAIL = BASE + 22;

		int GET_NIGHT_8_SUCCESS = BASE + 23;
		int GET_NIGHT_8_FAIL = BASE + 24;
	}

	public interface CategoryMessage {
		int BASE = 50000000;

		int GET_SUCCESS = BASE + 1;
		int GET_FAIL = BASE + 2;
	}

	public interface ShoppingCartMessage {
		int BASE = 60000000;

		int ADD_SUCCESS = BASE + 1;
		int ADD_FAIL = BASE + 2;

		int GET_SUCCESS = BASE + 3;
		int GET_FAIL = BASE + 4;

		int UPDATE_SUCCESS = BASE + 5;
		int UPDATE_FAIL = BASE + 6;

		int ADD_ONCE_SUCCESS = BASE + 7;
		int ADD_ONCE_FAIL = BASE + 8;

		int DELETE_SUCCESS = BASE + 9;
		int DELETE_FAIL = BASE + 10;
	}

	public interface AddressMessage {
		int BASE = 70000000;

		int ADD_SUCCESS = BASE + 1;
		int ADD_FAIL = BASE + 2;

		int GET_LIST_SUCCESS = BASE + 3;
		int GET_LIST_FAIL = BASE + 4;

		int EDIT_SUCCESS = BASE + 5;
		int EDIT_FAIL = BASE + 6;

		int DELETE_SUCCESS = BASE + 7;
		int DELETE_FAIL = BASE + 8;

		int GET_DETAIL_SUCCESS = BASE + 9;
		int GET_DETAIL_FAIL = BASE + 10;

		int SET_DETAULT_SUCCESS = BASE + 11;
		int SET_DETAULT_FAIL = BASE + 12;

		int GET_REGION_LIST_SUCCESS = BASE + 13;
		int GET_REGION_LIST_FAIL = BASE + 14;

		int GET_CITY_SUCCESS = BASE + 15;
		int GET_CITY_FAIL = BASE + 16;
	}

	public interface OrderMessage {
		int BASE = 80000000;

		int ADD_SUCCESS = BASE + 1;
		int ADD_FAIL = BASE + 2;

		int GET_LIST_SUCCESS = BASE + 3;
		int GET_LIST_FAIL = BASE + 4;

		int SUBMIT_SUCCESS = BASE + 5;
		int SUBMIT_FAIL = BASE + 6;

		int CANCEL_SUCCESS = BASE + 7;
		int CANCEL_FAIL = BASE + 8;

		int PAY_SUCCESS = BASE + 9;
		int PAY_FAIL = BASE + 10;

		int CHANGE_ADDRESS_SUCCESS = BASE + 11;
		int CHANGE_ADDRESS_FAIL = BASE + 12;

		int GET_COMPLETED_SUCCESS = BASE + 13;
		int GET_COMPLETED_FAIL = BASE + 14;

		int GET_UNCOMPLETED_SUCCESS = BASE + 15;
		int GET_UNCOMPLETED_FAIL = BASE + 16;

		int GET_CANCEL_SUCCESS = BASE + 17;
		int GET_CANCEL_FAIL = BASE + 18;

		int GET_RECHANGE_ITEMS_SUCCESS = BASE + 19;
		int GET_RECHANGE_ITEMS_FAIL = BASE + 20;

		int GET_BONUS_SUCCESS = BASE + 21;
		int GET_BONUS_FAIL = BASE + 22;

		int ALIPAY_ORDER_SUCCESS = BASE + 23;
		int ALIPAY_ORDER_FAIL = BASE + 24;

		int ALIPAY_RECHARGE_SUCCESS = BASE + 25;
		int ALIPAY_RECHARGE_FAIL = BASE + 26;

		int PAY_BALANCE_SUCCESS = BASE + 27;
		int PAY_BALANCE_FAIL = BASE + 28;

		int WXPAY_ORDER_SUCCESS = BASE + 29;
		int WXPAY_ORDER_FAIL = BASE + 30;

		int WXPAY_RECHARGE_SUCCESS = BASE + 31;
		int WXPAY_RECHARGE_FAIL = BASE + 32;
	}

	public interface CommentMessage {
		int BASE = 90000000;

		int GET_GOODS_COMMENTS_SUCCESS = BASE + 1;
		int GET_GOODS_COMMENTS_FAIL = BASE + 2;

		int ADD_GOODS_COMMENTS_SUCCESS = BASE + 3;
		int ADD_GOODS_COMMENTS_FAIL = BASE + 4;
	}

	public interface SettingsMessage {
		int BASE = 100000000;

		int GET_SHIPPING_SUCCESS = BASE + 1;
		int GET_SHIPPING_FAIL = BASE + 2;

		int GET_ABOUT_SUCCESS = BASE + 3;
		int GET_ABOUT_FAIL = BASE + 4;
	}

	public interface ActivityMessage {
		int BASE = 110000000;

		int GET_ACTIVITY_SUCCESS = BASE + 1;
		int GET_ACTIVITY_FAIL = BASE + 2;
	}
}
