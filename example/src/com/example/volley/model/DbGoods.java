package com.example.volley.model;


public class DbGoods {

	/**
	 * 主键
	 */
	private long id;
	/**
	 * 商品编号
	 */
	private int goods_id;
	/**
	 * 商品所属商品分类id
	 */
	private int cat_id;
	/**
	 * 商品的唯一货号
	 */
	private String goods_sn;
	/**
	 * 商品的名称
	 */
	private String goods_name;
	/**
	 * 商品名称显示的样式；包括颜色和字体样式；格式如#ff00ff+strong
	 */
	private String goods_name_style;
	/**
	 * 商品点击数
	 */
	private int click_count;
	/**
	 * 购买数
	 */
	private int sales_count;
	/**
	 * 品牌id
	 */
	private int brand_id;
	/**
	 * 供货人的名称
	 */
	private String provider_name;
	/**
	 * 商品库存数量
	 */
	private int goods_number;
	/**
	 * 商品的重量，以千克为单位
	 */
	private float goods_weight;
	/**
	 * 市场售价
	 */
	private float market_price;
	/**
	 * 本店售价
	 */
	private float shop_price;
	/**
	 * 促销价格
	 */
	private float promote_price;
	/**
	 * 促销价格开始日期
	 */
	private long promote_start_date;
	/**
	 * 促销价格结束日期
	 */
	private long promote_end_date;
	/**
	 * 商品报警数量
	 */
	private int warn_number;
	/**
	 * 商品关键字，放在商品页的关键字中，为搜索引擎收录用
	 */
	private String keywords;
	/**
	 * 商品的简短描述
	 */
	private String goods_brief;
	/**
	 * 商品的详细描述
	 */
	private String goods_desc;
	/**
	 * 商品在前台显示的微缩图片
	 */
	private String goods_thumb;
	/**
	 * 商品的实际大小图片
	 */
	private String goods_img;
	/**
	 * 上传的商品的原始图片
	 */
	private String original_img;
	/**
	 * 是否是实物，1，是；0，否；
	 */
	private int is_real;
	/**
	 * 商品的扩展属性
	 */
	private String extension_code;
	/**
	 * 该商品是否开放销售，1，是；0，否
	 */
	private int is_on_sale;
	/**
	 * 是否能单独销售，1，是；0，否；
	 */
	private int is_alone_sale;
	/**
	 * 是否配送
	 */
	private int is_shipping;
	/**
	 * 购买该商品可以使用的积分数量
	 */
	private int integral;
	/**
	 * 商品的添加时间
	 */
	private long add_time;
	/**
	 * 商品的显示顺序
	 */
	private int sort_order;
	/**
	 * 商品是否已经删除，0，否；1，已删除
	 */
	private int is_delete;
	/**
	 * 是否是精品；0，否；1，是
	 */
	private int is_best;
	/**
	 * 是否是新品
	 */
	private int is_new;
	/**
	 * 是否热销，0，否；1，是
	 */
	private int is_hot;
	/**
	 * 是否特价促销；0，否；1，是
	 */
	private int is_promote;
	/**
	 * 购买该商品所能领到的红包类型
	 */
	private int bonus_type_id;
	/**
	 * 最近一次更新商品配置的时间
	 */
	private long last_update;
	/**
	 * 商品所属类型id
	 */
	private int goods_type;
	/**
	 * 商品的商家备注，仅商家可见
	 */
	private String seller_note;
	/**
	 * 赠送消费积分数
	 */
	private int give_integral;
	/**
	 * 赠送等级积分数
	 */
	private int rank_integral;
	/**
	 * 供应商编号
	 */
	private int suppliers_id;
	/**
	 * 是否检查
	 */
	private int is_check;
	/**
	 * 是否收藏
	 */
	private int is_collect;
	/**
	 * 购物车id
	 */
	private int cart_rec_id;
	/**
	 * 购物车数目
	 */
	private int cart_goods_number;
	/**
	 * 分享链接
	 */
	private String share_url;
	/**
	 * 是否可以评论
	 */
	private int is_comment;

	public DbGoods() {
	}

	public DbGoods(GoodsVo vo) {
		this.goods_id = vo.getGoods_id();
		this.cat_id = vo.getCat_id();
		this.goods_sn = vo.getGoods_sn();
		this.goods_name = vo.getGoods_name();
		this.goods_name_style = vo.getGoods_name_style();
		this.click_count = vo.getClick_count();
		this.sales_count = vo.getSales_count();
		this.brand_id = vo.getBrand_id();
		this.provider_name = vo.getProvider_name();
		this.goods_number = vo.getGoods_number();
		this.goods_weight = vo.getGoods_weight();
		this.market_price = vo.getMarket_price();
		this.shop_price = vo.getShop_price();
		this.promote_price = vo.getPromote_price();
		this.promote_start_date = vo.getPromote_start_date();
		this.promote_end_date = vo.getPromote_end_date();
		this.warn_number = vo.getWarn_number();
		this.keywords = vo.getKeywords();
		this.goods_brief = vo.getGoods_brief();
		this.goods_desc = vo.getGoods_desc();
		this.goods_thumb = vo.getGoods_thumb();
		this.goods_img = vo.getGoods_img();
		this.original_img = vo.getOriginal_img();
		this.is_real = vo.getIs_real();
		this.extension_code = vo.getExtension_code();
		this.is_on_sale = vo.getIs_on_sale();
		this.is_alone_sale = vo.getIs_alone_sale();
		this.is_shipping = vo.getIs_shipping();
		this.integral = vo.getIntegral();
		this.add_time = vo.getAdd_time();
		this.sort_order = vo.getSort_order();
		this.is_delete = vo.getIs_delete();
		this.is_best = vo.getIs_best();
		this.is_new = vo.getIs_new();
		this.is_hot = vo.getIs_hot();
		this.is_promote = vo.getIs_promote();
		this.bonus_type_id = vo.getBonus_type_id();
		this.last_update = vo.getLast_update();
		this.goods_type = vo.getGoods_type();
		this.seller_note = vo.getSeller_note();
		this.give_integral = vo.getGive_integral();
		this.rank_integral = vo.getRank_integral();
		this.suppliers_id = vo.getSuppliers_id();
		this.is_check = vo.getIs_check();
		this.is_collect = vo.getIs_collect();
		this.cart_rec_id = vo.getCart_rec_id();
		this.cart_goods_number = vo.getCart_goods_number();
		this.share_url = vo.getShare_url();
		this.is_comment = vo.getIs_comment();
	}

	public DbGoods(ResponseGoods response) {
		this.goods_id = response.getGoods_id();
		this.cat_id = response.getCat_id();
		this.goods_sn = response.getGoods_sn();
		this.goods_name = response.getGoods_name();
		this.goods_name_style = response.getGoods_name_style();
		this.click_count = response.getClick_count();
		this.sales_count = response.getSales_count();
		this.brand_id = response.getBrand_id();
		this.provider_name = response.getProvider_name();
		this.goods_number = response.getGoods_number();
		this.goods_weight = response.getGoods_weight();
		this.market_price = response.getMarket_price();
		this.shop_price = response.getShop_price();
		this.promote_price = response.getPromote_price();
		this.promote_start_date = response.getPromote_start_date();
		this.promote_end_date = response.getPromote_end_date();
		this.warn_number = response.getWarn_number();
		this.keywords = response.getKeywords();
		this.goods_brief = response.getGoods_brief();
		this.goods_desc = response.getGoods_desc();
		this.goods_thumb = response.getGoods_thumb();
		this.goods_img = response.getGoods_img();
		this.original_img = response.getOriginal_img();
		this.is_real = response.getIs_real();
		this.extension_code = response.getExtension_code();
		this.is_on_sale = response.getIs_on_sale();
		this.is_alone_sale = response.getIs_alone_sale();
		this.is_shipping = response.getIs_shipping();
		this.integral = response.getIntegral();
		this.add_time = response.getAdd_time();
		this.sort_order = response.getSort_order();
		this.is_delete = response.getIs_delete();
		this.is_best = response.getIs_best();
		this.is_new = response.getIs_new();
		this.is_hot = response.getIs_hot();
		this.is_promote = response.getIs_promote();
		this.bonus_type_id = response.getBonus_type_id();
		this.last_update = response.getLast_update();
		this.goods_type = response.getGoods_type();
		this.seller_note = response.getSeller_note();
		this.give_integral = response.getGive_integral();
		this.rank_integral = response.getRank_integral();
		this.suppliers_id = response.getSuppliers_id();
		this.is_check = response.getIs_check();
		this.is_collect = response.getIs_collect();
		this.cart_rec_id = response.getCart_rec_id();
		this.cart_goods_number = response.getCart_goods_number();
		this.share_url = response.getShare_url();
		this.is_comment = response.getIs_comment();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public String getGoods_sn() {
		return goods_sn;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_name_style() {
		return goods_name_style;
	}

	public void setGoods_name_style(String goods_name_style) {
		this.goods_name_style = goods_name_style;
	}

	public int getClick_count() {
		return click_count;
	}

	public void setClick_count(int click_count) {
		this.click_count = click_count;
	}

	public int getSales_count() {
		return sales_count;
	}

	public void setSales_count(int sales_count) {
		this.sales_count = sales_count;
	}

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public String getProvider_name() {
		return provider_name;
	}

	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
	}

	public int getGoods_number() {
		return goods_number;
	}

	public void setGoods_number(int goods_number) {
		this.goods_number = goods_number;
	}

	public float getGoods_weight() {
		return goods_weight;
	}

	public void setGoods_weight(float goods_weight) {
		this.goods_weight = goods_weight;
	}

	public float getMarket_price() {
		return market_price;
	}

	public void setMarket_price(float market_price) {
		this.market_price = market_price;
	}

	public float getShop_price() {
		return shop_price;
	}

	public void setShop_price(float shop_price) {
		this.shop_price = shop_price;
	}

	public float getPromote_price() {
		return promote_price;
	}

	public void setPromote_price(float promote_price) {
		this.promote_price = promote_price;
	}

	public long getPromote_start_date() {
		return promote_start_date;
	}

	public void setPromote_start_date(long promote_start_date) {
		this.promote_start_date = promote_start_date;
	}

	public long getPromote_end_date() {
		return promote_end_date;
	}

	public void setPromote_end_date(long promote_end_date) {
		this.promote_end_date = promote_end_date;
	}

	public int getWarn_number() {
		return warn_number;
	}

	public void setWarn_number(int warn_number) {
		this.warn_number = warn_number;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getGoods_brief() {
		return goods_brief;
	}

	public void setGoods_brief(String goods_brief) {
		this.goods_brief = goods_brief;
	}

	public String getGoods_desc() {
		return goods_desc;
	}

	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public String getGoods_thumb() {
		return goods_thumb;
	}

	public void setGoods_thumb(String goods_thumb) {
		this.goods_thumb = goods_thumb;
	}

	public String getGoods_img() {
		return goods_img;
	}

	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}

	public String getOriginal_img() {
		return original_img;
	}

	public void setOriginal_img(String original_img) {
		this.original_img = original_img;
	}

	public int getIs_real() {
		return is_real;
	}

	public void setIs_real(int is_real) {
		this.is_real = is_real;
	}

	public String getExtension_code() {
		return extension_code;
	}

	public void setExtension_code(String extension_code) {
		this.extension_code = extension_code;
	}

	public int getIs_on_sale() {
		return is_on_sale;
	}

	public void setIs_on_sale(int is_on_sale) {
		this.is_on_sale = is_on_sale;
	}

	public int getIs_alone_sale() {
		return is_alone_sale;
	}

	public void setIs_alone_sale(int is_alone_sale) {
		this.is_alone_sale = is_alone_sale;
	}

	public int getIs_shipping() {
		return is_shipping;
	}

	public void setIs_shipping(int is_shipping) {
		this.is_shipping = is_shipping;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public long getAdd_time() {
		return add_time;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	public int getSort_order() {
		return sort_order;
	}

	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public int getIs_best() {
		return is_best;
	}

	public void setIs_best(int is_best) {
		this.is_best = is_best;
	}

	public int getIs_new() {
		return is_new;
	}

	public void setIs_new(int is_new) {
		this.is_new = is_new;
	}

	public int getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}

	public int getIs_promote() {
		return is_promote;
	}

	public void setIs_promote(int is_promote) {
		this.is_promote = is_promote;
	}

	public int getBonus_type_id() {
		return bonus_type_id;
	}

	public void setBonus_type_id(int bonus_type_id) {
		this.bonus_type_id = bonus_type_id;
	}

	public long getLast_update() {
		return last_update;
	}

	public void setLast_update(long last_update) {
		this.last_update = last_update;
	}

	public int getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(int goods_type) {
		this.goods_type = goods_type;
	}

	public String getSeller_note() {
		return seller_note;
	}

	public void setSeller_note(String seller_note) {
		this.seller_note = seller_note;
	}

	public int getGive_integral() {
		return give_integral;
	}

	public void setGive_integral(int give_integral) {
		this.give_integral = give_integral;
	}

	public int getRank_integral() {
		return rank_integral;
	}

	public void setRank_integral(int rank_integral) {
		this.rank_integral = rank_integral;
	}

	public int getSuppliers_id() {
		return suppliers_id;
	}

	public void setSuppliers_id(int suppliers_id) {
		this.suppliers_id = suppliers_id;
	}

	public int getIs_check() {
		return is_check;
	}

	public void setIs_check(int is_check) {
		this.is_check = is_check;
	}

	public int getIs_collect() {
		return is_collect;
	}

	public void setIs_collect(int is_collect) {
		this.is_collect = is_collect;
	}

	public int getCart_rec_id() {
		return cart_rec_id;
	}

	public void setCart_rec_id(int cart_rec_id) {
		this.cart_rec_id = cart_rec_id;
	}

	public int getCart_goods_number() {
		return cart_goods_number;
	}

	public void setCart_goods_number(int cart_goods_number) {
		this.cart_goods_number = cart_goods_number;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public int getIs_comment() {
		return is_comment;
	}

	public void setIs_comment(int is_comment) {
		this.is_comment = is_comment;
	}
}
