package com.example.volley.config;

import android.content.Context;
import android.os.Message;

import com.example.volley.R;
import com.idea.xbox.component.logger.Logger;
import com.idea.xbox.component.xml.impl.XmlRawParser;
import com.idea.xbox.framework.beans.IBeanBuilder;
import com.idea.xbox.framework.beans.XmlBeanBuilder;
import com.idea.xbox.framework.beans.config.Beans;
import com.idea.xbox.framework.core.logic.builder.ConfigLogicBuilder;

public class XConfigLogicBuilder extends ConfigLogicBuilder {
	private static final String TAG = "XConfigLogicBuilder";

	private XmlBeanBuilder mBeanBuilder;

	public XConfigLogicBuilder(Context context) {
		super(context);
	}

	@Override
	public void init(Context context) {
	}

	protected IBeanBuilder getBeanBuilder(Context context) {
		try {
			if (mBeanBuilder == null) {
				int[] rawIds = { R.raw.beans_config_db, R.raw.beans_db,
						R.raw.beans_logic, R.raw.beans_http,
						R.raw.beans_config_logger, R.raw.beans_ui };
				XmlRawParser parser = new XmlRawParser(context, rawIds,
						Beans.class);
				mBeanBuilder = new XmlBeanBuilder(context, parser);
			}
			return mBeanBuilder;
		} catch (Exception e) {
			Logger.e(TAG, e.getMessage(), e);
			return null;
		}
	}

	protected void handleStateMessage(Message msg) {
		int what = msg.what;
		Logger.d(TAG, "ConfigLogicBuilder.handleStateMessage,what=" + what);
	}

}
