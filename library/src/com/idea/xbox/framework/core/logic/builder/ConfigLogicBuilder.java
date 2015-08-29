package com.idea.xbox.framework.core.logic.builder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.idea.xbox.framework.beans.IBeanBuilder;
import com.idea.xbox.framework.core.logic.ILogic;

abstract public class ConfigLogicBuilder extends BaseLogicBuilder implements ILogicBuilder {

    public IBeanBuilder mBeanBuilder;

    private Context mContext;

    public ConfigLogicBuilder(Context context) {
        this.mContext = context.getApplicationContext();
        this.mBeanBuilder = getBeanBuilder(mContext);
        init(mContext);
    }

    @Override
    public List<ILogic> addPropertiesToActivity(Object context) throws Exception {
        List<Object> propertyObjects = this.mBeanBuilder.addPropertiesToActivity(context);
        List<ILogic> logics = new ArrayList<ILogic>();
        for (Object obj : propertyObjects) {
            if (obj instanceof ILogic) {
                ILogic logic = (ILogic) obj;
                logic.addHandler(getHandler());
                logics.add(logic);
            }
        }
        return logics;
    }

    abstract protected IBeanBuilder getBeanBuilder(Context context);
}
