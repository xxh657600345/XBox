package com.example.volley.http;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.volley.model.GoodsListResponse;

public interface IGoodsHttpAdapter {

	void getGoodsByCategory(int cat_id, int page, String orderbykey,
			String orderbyvalue, Listener<GoodsListResponse> listener,
			ErrorListener errorListener) throws Exception;

}
