package com.example.volley.http.impl;

import org.json.JSONObject;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.volley.config.XHttpConfig;
import com.example.volley.http.IGoodsHttpAdapter;
import com.example.volley.http.base.BaseHttpAdapter;
import com.example.volley.http.base.GsonRequest;
import com.example.volley.model.GoodsListResponse;
import com.idea.xbox.component.logger.Logger;

public class GoodsHttpAdapterImpl extends BaseHttpAdapter implements
		IGoodsHttpAdapter {
	private static final String TAG = "GsonRequest";

	@Override
	public void getGoodsByCategory(int cat_id, int page, String orderbykey,
			String orderbyvalue, Listener<GoodsListResponse> listener,
			ErrorListener errorListener) throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("cat_id", cat_id);
		jo.put("page", page);
		if (!TextUtils.isEmpty(orderbykey)) {
			jo.put("orderbykey", orderbykey);
			jo.put("orderbyvalue", orderbyvalue);
		}
		String url = XHttpConfig.HOME_URL + XHttpConfig.GOODS
				+ XHttpConfig.GOODS_BY_CATEGORY + XHttpConfig.REQ_DATA_PARAM
				+ jo.toString();
		Logger.d(TAG, url);
		GsonRequest<GoodsListResponse> gsonRequest = new GsonRequest<GoodsListResponse>(
				Request.Method.GET, url, GoodsListResponse.class, listener,
				errorListener) {
		};
		requestQueue.add(gsonRequest);
	}
}
