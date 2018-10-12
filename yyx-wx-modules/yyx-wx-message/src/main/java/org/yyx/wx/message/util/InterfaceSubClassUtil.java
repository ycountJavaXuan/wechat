package org.yyx.wx.message.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.wx.commons.exception.config.ConfigException;
import org.yyx.wx.commons.util.InterfaceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.yyx.wx.commons.bussinessenum.ResponseCodeFromWx.error_load_config;

/**
 * 接口实现类工具
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/10/12-10:16
 */
public class InterfaceSubClassUtil {

    private static final List<Class<?>> INTERFACE_SUB_CLASS = new ArrayList<>();
    /**
     * InterfaceSubClassUtil 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceSubClassUtil.class);

    private InterfaceSubClassUtil() {
    }

    /**
     * 从包路径中获取实现类
     *
     * @param servicePackageName 包名
     * @return 实现类集合
     */
    public static List<Class<?>> getInterfaceSubClass(String servicePackageName) {
        // 判断实现类数组是否为空
        boolean empty = ArrayUtil.isEmpty(INTERFACE_SUB_CLASS);
        if (!empty) {
            return INTERFACE_SUB_CLASS;
        }
        // 为空，从配置文件中获取具体业务实现类所在的包名
        if (StrUtil.isEmpty(servicePackageName)) {
            // 配置加载出错
            LOGGER.error("[配置加载出错] {}", servicePackageName);
            throw new ConfigException(error_load_config);
        }
        try {
            // 获取包下的所有类文件
            List<Class<?>> interfaceSubClass = InterfaceUtil.getInterfaceSubClass(servicePackageName);
            // 添加到实现类数组中
            INTERFACE_SUB_CLASS.addAll(interfaceSubClass);
        } catch (IOException e) {
            LOGGER.error("[IO异常] {}", e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error("[类没有找到异常] {}", e.getMessage());
        }
        return INTERFACE_SUB_CLASS;
    }
}