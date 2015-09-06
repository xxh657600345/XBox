package com.example.volley.http.base;

import android.content.Context;

public final class ResultCode {
	public final static int REQUEST_SUCCESS = 200;

	public final static int REQUEST_UNKNOW_ERROR = 100000000;

	// Client error.
	private final static int REQUEST_CLIENT_BASE_ERROR = 10000000;
	public final static int REQUEST_CLIENT_ERROR = REQUEST_CLIENT_BASE_ERROR + 1;
	public final static int REQUEST_UPLOAD_FAILED = REQUEST_CLIENT_BASE_ERROR + 2;

	// Network error.
	private final static int REQUEST_NETWORK_BASE_ERROR = 20000000;
	public final static int REQUEST_NETWORK_ERROR = REQUEST_NETWORK_BASE_ERROR + 1;

	// Server error.
	private final static int REQUEST_SERVER_BASE_ERROR = 1000000;
	// Common part start from 1000000.
	public final static int COMMON_BASE_ERROR = REQUEST_SERVER_BASE_ERROR;
	// Missing parameters.
	public final static int PARAM_MISSING = COMMON_BASE_ERROR + 1;
	// Invalid parameters.
	public final static int PARAM_INVALID = COMMON_BASE_ERROR + 2;

	// Account part start from 2000000.
	public final static int ACCOUNT_BASE_ERROR = REQUEST_SERVER_BASE_ERROR * 2;
	// Account already exist.
	public final static int ACCOUNT_ALREADY_EXIST = ACCOUNT_BASE_ERROR + 1;
	// Account type not support.
	public final static int ACCOUNT_NOT_SUPPORT = ACCOUNT_BASE_ERROR + 2;
	// Account not exist.
	public final static int ACCOUNT_NOT_EXIST = ACCOUNT_BASE_ERROR + 3;
	// Password wrong.
	public final static int PASSWORD_WRONG = ACCOUNT_BASE_ERROR + 4;

	// Default network error.
	public final static int NOT_FOUND = 404;

	// Return error message.
	public static String getErrorMsg(Context context, long errorCode) {
		if (null == context) {
			return "";
		}

		return "";
	}

}
