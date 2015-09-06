package com.example.volley.logic.impl;

import java.util.ArrayList;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.volley.R;
import com.example.volley.config.XMessageType;
import com.example.volley.db.IGoodsDbAdapter;
import com.example.volley.http.IGoodsHttpAdapter;
import com.example.volley.logic.IGoodsLogic;
import com.example.volley.model.GoodsListResponse;
import com.example.volley.model.GoodsVo;
import com.example.volley.model.ResponseGoods;
import com.idea.xbox.framework.core.logic.BaseLogic;

public class GoodsLogicImpl extends BaseLogic implements IGoodsLogic {

	private IGoodsHttpAdapter goodsHttpAdapter;
	private IGoodsDbAdapter goodsDbAdapter;

	private Context mContext;

	@Override
	public void onCreated(Context context) {
		mContext = context;
	}

	private void getGoodsListSuccess(int cat_id, int type,
			GoodsListResponse response) {
		ArrayList<ResponseGoods> items = response.getRespbody();
		ArrayList<GoodsVo> voItems = new ArrayList<GoodsVo>();
		for (ResponseGoods item : items) {
			GoodsVo voItem = new GoodsVo(item);
			voItems.add(voItem);
		}
		if (cat_id == 5) {
			sendMessage(XMessageType.GoodsMessage.GET_VVIP_SUCCESS, voItems);
		} else if (cat_id == 6) {
			sendMessage(XMessageType.GoodsMessage.GET_NIGHT_8_SUCCESS, voItems);
		} else {
			sendMessage(XMessageType.GoodsMessage.GET_GOODS_LIST_SUCCESS,
					voItems);
		}
	}

	private void getGoodsListFailed(String msg) {
		sendMessage(XMessageType.GoodsMessage.GET_GOODS_LIST_FAIL, msg);
	}

	@Override
	public void getGoodsByCategory(final int cat_id, int page,
			String orderbykey, String orderbyvalue) {
		try {
			goodsHttpAdapter.getGoodsByCategory(cat_id, page, orderbykey,
					orderbyvalue, new Listener<GoodsListResponse>() {

						@Override
						public void onResponse(GoodsListResponse response) {
							if (!response.isSuccessed()) {
								getGoodsListFailed(response.getRespmessage());
								return;
							}
							getGoodsListSuccess(cat_id, -1, response);
						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							if (null != error.networkResponse) {
								getGoodsListFailed(mContext
										.getString(R.string.toast_get_goods_list_fail));
							} else {
								getGoodsListFailed(mContext
										.getString(R.string.toast_network_error));
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			getGoodsListFailed(mContext.getString(R.string.toast_network_error));
		}
	}

	public IGoodsHttpAdapter getGoodsHttpAdapter() {
		return goodsHttpAdapter;
	}

	public void setGoodsHttpAdapter(IGoodsHttpAdapter goodsHttpAdapter) {
		this.goodsHttpAdapter = goodsHttpAdapter;
	}

	public IGoodsDbAdapter getGoodsDbAdapter() {
		return goodsDbAdapter;
	}

	public void setGoodsDbAdapter(IGoodsDbAdapter goodsDbAdapter) {
		this.goodsDbAdapter = goodsDbAdapter;
	}

}
