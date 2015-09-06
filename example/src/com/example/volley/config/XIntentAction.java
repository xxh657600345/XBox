package com.example.volley.config;

public interface XIntentAction {
	public static final int ADDRESS_REQUEST_BASE = 1000;
	public static final int ADDRESS_RESULT_BASE = 1100;

	public static final int ORDER_PAYMENT_REQUEST_BASE = 2000;
	public static final int ORDER_PAYMENT_RESULT_BASE = 2100;

	public static final int ORDER_BILL_HEADER_REQUEST_BASE = 3000;
	public static final int ORDER_BILL_HEADER_RESULT_BASE = 3100;

	public static final int BONUS_REQUEST_BASE = 4000;
	public static final int BONUS_RESULT_BASE = 4100;

	public static final int CITY_REQUEST_BASE = 5000;
	public static final int CITY_RESULT_BASE = 5100;

	public static final String EXIT_APP_ACTION = "com.xxh.activity.EXIT_APP";

	public interface LoginActivityAction {
		String ACTION = "com.xxh.activity.LOGIN";
	}

	public interface RegisterActivityAction {
		String ACTION = "com.xxh.activity.REGISTER";
	}

	public interface FindPasswordActivityAction {
		String ACTION = "com.xxh.activity.FIND_PASSWORD";
	}

	public interface ChangePasswordActivityAction {
		String ACTION = "com.xxh.activity.CHANGE_PASSWORD";
	}

	public interface GuideActivityAction {
		String ACTION = "com.xxh.activity.GUIDE";
	}

	public interface AdsActivityAction {
		String ACTION = "com.xxh.activity.ADS";
		String KEY_ADS_IMG = "ads_img";
	}

	public interface HomeActivityAction {
		String ACTION = "com.xxh.activity.HOME";
	}

	public interface MarketActivityAction {
		String ACTION = "com.xxh.activity.MARKET";
	}

	public interface CategoryActivityAction {
		String ACTION = "com.xxh.activity.CATEGORY";
	}

	public interface ShoppingCartActivityAction {
		String ACTION = "com.xxh.activity.SHOPPINGCART";
	}

	public interface MySixActivityAction {
		String ACTION = "com.xxh.activity.MYSIX";
	}

	public interface SettingsActivityAction {
		String ACTION = "com.xxh.activity.SETTINGS";
	}

	public interface GoodsDetailActivityAction {
		String ACTION = "com.xxh.activity.GOODSDETAIL";
		String KEY_GOODS_VO = "goods_vo";
		String KEY_FROM_NOTIFICATION = "from_notification";
	}

	public interface GoodsListActivityAction {
		String ACTION = "com.xxh.activity.GOODSLIST";

		String KEY_MARKET_TYPE = "market_type";
		String KEY_CATEGORY_ID = "cat_id";
		String KEY_CATEGORY_NAME = "cat_name";
	}

	public interface CategoryGoodsListActivityAction {
		String ACTION = "com.xxh.activity.CATEGORYGOODSLIST";

		String KEY_CATEGORY_ID = "cat_id";
		String KEY_CATEGORY_NAME = "cat_name";
	}

	public interface CityActivityAction {
		String ACTION = "com.xxh.activity.CITY";

		String KEY_FROM_HOME = "from_home";
		int REQUEST_HOME_CITY = CITY_REQUEST_BASE + 1;
		int RESULT_HOME_CITY = CITY_RESULT_BASE + 1;
	}

	public interface MyAddressActivityAction {
		String ACTION = "com.xxh.activity.MY_ADDRESS";

		String KEY_FROM_ORDER = "from_order";
		int REQUEST_ORDER_EDIT = ADDRESS_REQUEST_BASE + 1;
		int RESULT_ORDER_EDIT = ADDRESS_RESULT_BASE + 1;
	}

	public interface AddressDetailActivityAction {
		String ACTION = "com.xxh.activity.ADDRESS_DETAIL";

		String KEY_ADDRESS_VO = "address_vo";
	}

	public interface AddressNewActivityAction {
		String ACTION = "com.xxh.activity.ADDRESS_NEW";
	}

	public interface MyLikeActivityAction {
		String ACTION = "com.xxh.activity.MY_LIKE";
	}

	public interface GoodsDescActivityAction {
		String ACTION = "com.xxh.activity.GOODS_DESC";

		String KEY_DESC = "goods_desc";
	}

	public interface GoodsSearchActivityAction {
		String ACTION = "com.xxh.activity.GOODS_SEARCH";

		String KEY_GOODS_NAME = "goods_name";
	}

	public interface OrderEditActivityAction {
		String ACTION = "com.xxh.activity.ORDER_EDIT";

		String KEY_SHOPPINGCART_LIST = "shoppingcart_list";
		String KEY_ADDRESS_VO = "address_vo";
		String KEY_BONUS_VO = "bonus_vo";
	}

	public interface OrderPayActivityAction {
		String ACTION = "com.xxh.activity.ORDER_PAY";

		String KEY_ORDER_VO = "order_vo";
	}

	public interface OrderDetailActivityAction {
		String ACTION = "com.xxh.activity.ORDER_DETAIL";

		String KEY_ORDER_VO = "order_vo";
		String KEY_ORDER_TYPE = "order_type";
	}

	public interface MyOrderActivityAction {
		String ACTION = "com.xxh.activity.MY_ORDER";
	}

	public interface OrderPaymentActivityAction {
		String ACTION = "com.xxh.activity.ORDER_PAYMENT";

		String KEY_REQUEST_ORDER_EDIT = "request_payment_list";
		String KEY_REQUEST_ORDER_EDIT_SUM = "request_payment_list_sum";
		String KEY_RESULT_ORDER_EDIT = "result_payment";
		int REQUEST_ORDER_EDIT = ORDER_PAYMENT_REQUEST_BASE + 1;
		int RESULT_ORDER_EDIT = ORDER_PAYMENT_RESULT_BASE + 1;
	}

	public interface OrderBillHeaderActivityAction {
		String ACTION = "com.xxh.activity.ORDER_BILL_HEADER";

		String KEY_REQUEST_ORDER_EDIT = "request_header_vo";
		String KEY_RESULT_ORDER_EDIT = "result_header_vo";
		int REQUEST_ORDER_EDIT = ORDER_BILL_HEADER_REQUEST_BASE + 1;
		int RESULT_ORDER_EDIT = ORDER_BILL_HEADER_RESULT_BASE + 1;
	}

	public interface GoodsCommentActivityAction {
		String ACTION = "com.xxh.activity.GOODS_COMMENT";

		String KEY_GOODS_VO = "goods_vo";
	}

	public interface RechargeBalanceActivityAction {
		String ACTION = "com.xxh.activity.RECHARGE_BALANCE";
	}

	public interface MyBonusActivityAction {
		String ACTION = "com.xxh.activity.MY_BONUS";

		String KEY_FROM_ORDER = "from_order";
		int REQUEST_ORDER_EDIT = BONUS_REQUEST_BASE + 1;
		int RESULT_ORDER_EDIT = BONUS_RESULT_BASE + 1;
	}

	public interface ShippingIntroActivityAction {
		String ACTION = "com.xxh.activity.SHIPPING_INTRO";
	}

	public interface AboutActivityAction {
		String ACTION = "com.xxh.activity.ABOUT";
	}

	public interface ShakeActivityAction {
		String ACTION = "com.xxh.activity.SHAKE";
	}

	public interface SignInActivityAction {
		String ACTION = "com.xxh.activity.SIGN_IN";
	}

	public interface UpdateHeaderActivityAction {
		String ACTION = "com.xxh.activity.UPDATE_HEADER";
	}

	public interface ActivityActivityAction {
		String ACTION = "com.xxh.activity.ACTIVITY";

		String KEY_FROM_MARKET = "from_market";
	}
}
