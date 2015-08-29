package com.idea.xbox.framework.beans;

import java.util.List;

public interface IBeanBuilder {
    Object getBean(String id) throws Exception;

    List<Object> addPropertiesToActivity(Object context) throws Exception;

    List<Object> getSingletonBeans();
}
