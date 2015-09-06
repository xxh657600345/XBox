package com.example.volley.logic;

public interface IGoodsLogic {

	void getGoodsByCategory(int cat_id, int page, String orderbykey,
			String orderbyvalue);

}
