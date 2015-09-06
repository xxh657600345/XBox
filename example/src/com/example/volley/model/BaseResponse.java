package com.example.volley.model;

public class BaseResponse {
	private int respcode;
	private String respmessage;

	public boolean isSuccessed() {
		if (respcode == 200) {
			return true;
		}
		return false;
	}

	public int getRespcode() {
		return respcode;
	}

	public void setRespcode(int respcode) {
		this.respcode = respcode;
	}

	public String getRespmessage() {
		return respmessage;
	}

	public void setRespmessage(String respmessage) {
		this.respmessage = respmessage;
	}
}
