package xyz.blushyes.util;

import cn.hutool.core.bean.BeanUtil;

/**
 * 转换工具类
 */
public class TransferUtil {
    public static <E, T> T copyTransfer(E from, T to) {
        BeanUtil.copyProperties(from, to);
        return to;
    }

    public static <E, T> T copyTransfer(E from, T to, String... ignoreList) {
        BeanUtil.copyProperties(from, to, ignoreList);
        return to;
    }
}
