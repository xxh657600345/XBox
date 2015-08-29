package com.idea.xbox.framework.core.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.idea.xbox.framework.core.logic.ILogic;

final public class ContextProxy {
    private IHandleMessageObject mHandleMessageObject;

    /**
     * 缓存持有的logic对象的集�?
     */
    private final Map<Class<?>, ILogic> mLogics = new HashMap<Class<?>, ILogic>();

    /**
     * 该activity持有的handler�?
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mHandleMessageObject != null) {
                mHandleMessageObject.handleStateMessage(msg);
            }
        }
    };

    protected ContextProxy(IHandleMessageObject activityObject) {
        mHandleMessageObject = activityObject;
    }

    /**
     * 通过接口类获取logic对象<BR>
     * 
     * @param interfaceClass 接口类型
     * @return logic对象
     */
    protected void addLogic(ILogic logic) {
        if (null != logic && !mLogics.keySet().contains(logic.getClass())) {
            logic.addHandler(mHandler);
            mLogics.put(logic.getClass(), logic);
        }
    }

    final public Handler getHandler() {
        return mHandler;
    }

    protected Collection<ILogic> getLogics() {
        return mLogics.values();
    }
}
