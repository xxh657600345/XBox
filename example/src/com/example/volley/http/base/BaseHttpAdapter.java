package com.example.volley.http.base;

import com.android.volley.RequestQueue;

public class BaseHttpAdapter {
	protected RequestQueue requestQueue;

	public BaseHttpAdapter() {
		requestQueue = RequestManager.getRequestQueue();
	}
}
