package com.idea.xbox.framework.core.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Message;

import com.idea.xbox.component.logger.Logger;
import com.idea.xbox.framework.core.logic.ILogic;
import com.idea.xbox.framework.core.logic.builder.ILogicBuilder;

final public class ContextManagement {
    private static final String TAG = "ContextManagement";

    private static Map<String, ContextProxy> mContextObjectMaps =
            new HashMap<String, ContextProxy>();

    /**
     * 系统的所有logic的缓存创建管理类
     */
    private static ILogicBuilder mLogicBuilder = null;

    private ContextManagement() {}

    public static synchronized void init(ILogicBuilder logicBuilder) {
        Logger.i(TAG, "ContextManagement.init");
        ContextManagement.mLogicBuilder = logicBuilder;
    }

    public static boolean isInit() {
        return ContextManagement.mLogicBuilder != null;
    }

    public static synchronized void registerContext(Object context) throws Exception {
        String key = getKey(context);
        if (!mContextObjectMaps.containsKey(key)) {
            if (context instanceof IHandleMessageObject) {
                ContextProxy contextProxy = new ContextProxy((IHandleMessageObject) context);
                mContextObjectMaps.put(key, contextProxy);
                List<ILogic> propertyObjects =
                        ContextManagement.mLogicBuilder.addPropertiesToActivity(context);
                for (Object obj : propertyObjects) {
                    if (obj != null && obj instanceof ILogic) {
                        ILogic logic = (ILogic) obj;
                        contextProxy.addLogic(logic);
                    }
                }
            }
        }
    }

    public static synchronized void unregisterContext(IHandleMessageObject contextObject) {
        String key = getKey(contextObject);
        if (mContextObjectMaps.containsKey(key)) {
            removeHandler(contextObject);
            mContextObjectMaps.remove(key);
        }
    }

    private static void removeHandler(IHandleMessageObject contextObject) {
        String key = getKey(contextObject);
        ContextProxy contextProxy = mContextObjectMaps.get(key);
        if (contextProxy.getHandler() != null) {
            if (contextProxy.getLogics().size() > 0) {
                for (ILogic logic : contextProxy.getLogics()) {
                    logic.removeHandler(contextProxy.getHandler());
                }
            }
        }
    }

    private static String getKey(Object object) {
        return Integer.toHexString(object.hashCode());
    }

    /**
     * 发�?消息
     * 
     * @param what 消息标识
     */
    protected static void sendEmptyMessage(int what, IHandleMessageObject contextObject) {
        Object key = getKey(contextObject);
        ContextProxy contextProxy = mContextObjectMaps.get(key);
        if (contextProxy != null && contextProxy.getHandler() != null) {
            contextProxy.getHandler().sendEmptyMessage(what);
        }
    }

    /**
     * 延迟发�?空消�?
     * 
     * @param what 消息标识
     * @param delayMillis 延迟时间
     */
    protected static void sendEmptyMessageDelayed(int what, long delayMillis,
            IHandleMessageObject contextObject) {
        String key = getKey(contextObject);
        ContextProxy contextProxy = mContextObjectMaps.get(key);
        if (contextProxy != null && contextProxy.getHandler() != null) {
            contextProxy.getHandler().sendEmptyMessageDelayed(what, delayMillis);
        }
    }

    /**
     * 发�?消息
     * 
     * @param msg 消息对象
     */
    protected static void sendMessage(Message msg, IHandleMessageObject contextObject) {
        Object key = getKey(contextObject);
        ContextProxy contextProxy = mContextObjectMaps.get(key);
        if (contextProxy != null && contextProxy.getHandler() != null) {
            contextProxy.getHandler().sendMessage(msg);
        }
    }

    /**
     * 延迟发�?消息
     * 
     * @param msg 消息对象
     * @param delayMillis 延迟时间
     */
    protected static void sendMessageDelayed(Message msg, long delayMillis,
            IHandleMessageObject contextObject) {
        Object key = getKey(contextObject);
        ContextProxy contextProxy = mContextObjectMaps.get(key);
        if (contextProxy != null && contextProxy.getHandler() != null) {
            contextProxy.getHandler().sendMessageDelayed(msg, delayMillis);
        }
    }

    /**
     * post�?��操作到UI线程
     * 
     * @param runnable Runnable
     */
    protected static void postRunnable(Runnable runnable, IHandleMessageObject contextObject) {
        Object key = getKey(contextObject);
        ContextProxy contextProxy = mContextObjectMaps.get(key);
        if (contextProxy != null && contextProxy.getHandler() != null) {
            contextProxy.getHandler().post(runnable);
        }
    }
}
