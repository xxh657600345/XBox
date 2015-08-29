package com.idea.xbox.framework.core;

import android.app.Application;
import android.content.Context;

import com.idea.xbox.component.logger.Logger;
import com.idea.xbox.framework.core.logic.builder.ILogicBuilder;
import com.idea.xbox.framework.core.ui.ContextManagement;

abstract public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i(TAG, "BaseApplication.onCreate");
        if (!ContextManagement.isInit()) {
            initSystem(getApplicationContext());
            ContextManagement.init(createLogicBuilder(this.getApplicationContext()));
        }
    }

    /**
     * 系统的初始化方法<BR>
     * 
     * @param context 系统的context对象
     */
    abstract protected void initSystem(Context context);

    /**
     * Logic建�?管理类需要创建的接口<BR>
     * �?��子类继承后，指定Logic建�?管理类具体实�?
     * 
     * @param context 系统的context对象
     * @return Logic建�?管理类具体实�?
     */
    abstract protected ILogicBuilder createLogicBuilder(Context context);

}
