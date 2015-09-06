package com.example.volley.model;

import java.util.ArrayList;

public class GoodsListResponse extends BaseResponse {
	private ArrayList<ResponseGoods> respbody;

	public ArrayList<ResponseGoods> getRespbody() {
		return respbody;
	}

	public void setRespbody(ArrayList<ResponseGoods> respbody) {
		this.respbody = respbody;
	}
}
