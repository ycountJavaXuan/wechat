package org.yyx.wx.message.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;
import org.yyx.wx.message.proxy.message.ImageMessageHandlerProxy;

import java.util.List;

import static org.yyx.wx.commons.constant.ConfigConstant.PACKAGE_INTERFACE;
import static org.yyx.wx.message.util.InterfaceSubClassUtil.getInterfaceSubClass;

/**
 * 图片业务实现类Condition
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/9/10-13:39
 */
@Component
public class ImageMessageServiceCondition implements Condition {
    /**
     * ImageMessageServiceCondition 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMessageServiceCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String servicePackageName = context.getEnvironment().getProperty(PACKAGE_INTERFACE);
        List<Class<?>> interfaceSubClass = getInterfaceSubClass(servicePackageName);
        // 遍历实现类数组
        for (int i = 0; i < interfaceSubClass.size(); i++) {
            // 接口实例
            Class<?> interfaceInstance = interfaceSubClass.get(i);
            Object o = null;
            try {
                o = interfaceInstance.newInstance();
            } catch (InstantiationException e) {
                LOGGER.error("[实例化异常] {}", e.getMessage());
            } catch (IllegalAccessException e) {
                LOGGER.error("[权限异常] {}", e.getMessage());
            }
            // 如果已存在 ImageMessageHandlerProxy 接口的实现类，return false;
            if (o instanceof ImageMessageHandlerProxy) {
                return false;
            }
        }
        return true;
    }
}