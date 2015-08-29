package com.idea.xbox.common.util;

public class UrlUtil {
    /**
     * 
     * 去掉url中多余的斜杠
     * 
     * @param url 字符�?
     * @return 去掉多余斜杠的字符串
     */
    public static String fixUrl(String url) {
        if (null == url) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        for (int i = stringBuffer.indexOf("//", stringBuffer.indexOf("//") + 2); i != -1; i =
                stringBuffer.indexOf("//", i + 1)) {
            stringBuffer.deleteCharAt(i);
        }
        return stringBuffer.toString();
    }
}
