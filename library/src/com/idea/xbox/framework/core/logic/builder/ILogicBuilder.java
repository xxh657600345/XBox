package com.idea.xbox.framework.core.logic.builder;

import java.util.List;

import android.content.Context;

import com.idea.xbox.framework.core.logic.ILogic;

public interface ILogicBuilder {

    void init(Context context);

    List<ILogic> addPropertiesToActivity(Object context) throws Exception;
}
