package com.idea.xbox.framework.core.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.idea.xbox.framework.core.logic.builder.ILogicBuilder;

abstract public class BaseFragment extends Fragment implements IHandleMessageObject {
    private static final String TAG = "BaseFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            ContextManagement.registerContext(this);
        } catch (Exception e) {
            Log.w(TAG, e.getMessage(), e);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void handleStateMessage(Message msg) {

    }

    final protected boolean isInit() {
        return ContextManagement.isInit();
    }

    final protected void initLogicBuilder(ILogicBuilder logicBuilder) {
        ContextManagement.init(logicBuilder);
    }

    /**
     * 发�?消息
     * 
     * @param what 消息标识
     */
    protected final void sendEmptyMessage(int what) {
        ContextManagement.sendEmptyMessage(what, this);
    }

    /**
     * 延迟发�?空消�?
     * 
     * @param what 消息标识
     * @param delayMillis 延迟时间
     */
    protected final void sendEmptyMessageDelayed(int what, long delayMillis) {
        ContextManagement.sendEmptyMessageDelayed(what, delayMillis, this);
    }

    /**
     * post�?��操作到UI线程
     * 
     * @param runnable Runnable
     */
    protected final void postRunnable(Runnable runnable) {
        ContextManagement.postRunnable(runnable, this);
    }

    /**
     * 发�?消息
     * 
     * @param msg 消息对象
     */
    protected final void sendMessage(Message msg) {
        ContextManagement.sendMessage(msg, this);
    }

    /**
     * 延迟发�?消息
     * 
     * @param msg 消息对象
     * @param delayMillis 延迟时间
     */
    protected final void sendMessageDelayed(Message msg, long delayMillis) {
        ContextManagement.sendMessageDelayed(msg, delayMillis, this);
    }

    /**
     * Fragment的释放的方法<BR>
     * 在这里对�?��加载到logic中的handler进行释放
     */
    public void onDestroy() {
        ContextManagement.unregisterContext(this);
        super.onDestroy();
    }

}
