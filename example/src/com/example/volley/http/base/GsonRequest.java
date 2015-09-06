package com.example.volley.http.base;

import java.io.UnsupportedEncodingException;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

public class GsonRequest<T> extends Request<T> {

	private final Listener<T> mListener;

	private Gson mGson;

	private Class<T> mClass;

	/** The default socket timeout in milliseconds */
	public static final int DEFAULT_TIMEOUT_MS = 2500;

	/** The default number of retries */
	public static final int DEFAULT_MAX_RETRIES = 2;

	/** The default backoff multiplier */
	public static final float DEFAULT_BACKOFF_MULT = 1f;

	public GsonRequest(int method, String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mGson = new Gson();
		mClass = clazz;
		mListener = listener;
		setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
				DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));
	}

	public GsonRequest(String url, Class<T> clazz, Listener<T> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, clazz, listener, errorListener);
	}

	@Override
	protected void deliverResponse(T arg0) {
		mListener.onResponse(arg0);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, "UTF-8");
			return Response.success(mGson.fromJson(jsonString, mClass),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

}
