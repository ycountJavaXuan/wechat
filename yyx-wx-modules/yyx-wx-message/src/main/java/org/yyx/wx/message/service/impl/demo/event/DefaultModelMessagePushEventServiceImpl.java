package org.yyx.wx.message.service.impl.demo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yyx.wx.commons.bussinessenum.MessageTypeEnum;
import org.yyx.wx.commons.vo.pubnum.BaseMessageAndEventRequestAndResponse;
import org.yyx.wx.commons.vo.pubnum.request.event.ModelMessagePushEventRequest;
import org.yyx.wx.commons.vo.pubnum.response.message.BaseMessageResponse;
import org.yyx.wx.commons.vo.pubnum.response.message.TextMessageResponse;
import org.yyx.wx.message.proxy.event.ModelMessagePushEventHandlerProxy;

import static org.yyx.wx.commons.constant.EventConstant.MODEL_MESSAGE_PUSH_FAILED_BY_OTHER;
import static org.yyx.wx.commons.constant.EventConstant.MODEL_MESSAGE_PUSH_FAILED_BY_USER;
import static org.yyx.wx.commons.constant.EventConstant.MODEL_MESSAGE_PUSH_SUCCESS;

/**
 * 默认的模板消息事件推送业务实现
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/9/14-14:06
 */
@Service
public class DefaultModelMessagePushEventServiceImpl implements ModelMessagePushEventHandlerProxy {
    /**
     * DefaultModelMessagePushEventServiceImpl日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultModelMessagePushEventServiceImpl.class);


    /**
     * 模板消息业务处理
     *
     * @param baseMessageAndEventRequest 微信推送过来的事件实体
     * @return 给公众号推送的消息
     */
    @Override
    public final BaseMessageResponse dealMessage(BaseMessageAndEventRequestAndResponse baseMessageAndEventRequest) {
        LOGGER.info("[DEMO] 自定义订阅[关注]业务实现类");
        ModelMessagePushEventRequest messageAndEventRequest = (ModelMessagePushEventRequest) baseMessageAndEventRequest;
        String status = messageAndEventRequest.getStatus();
        String message = "[DEMO] 叶云轩自定义模板消息事件回复。此次推送结果:";
        switch (status) {
            case MODEL_MESSAGE_PUSH_SUCCESS:
                LOGGER.info("[推送成功] {}", messageAndEventRequest.getMsgID());
                message += "成功";
                break;
            case MODEL_MESSAGE_PUSH_FAILED_BY_OTHER:
                LOGGER.info("[推送失败,未知原因] {}", messageAndEventRequest.getMsgID());
                message += "推送失败,未知原因";
                break;
            case MODEL_MESSAGE_PUSH_FAILED_BY_USER:
                LOGGER.info("[推送失败,用户拒绝] {}", messageAndEventRequest.getMsgID());
                message += "推送失败,用户拒绝";
                break;
            default:
                break;
        }
        TextMessageResponse textMessageResponse = new TextMessageResponse();
        textMessageResponse.setCreateTime(System.currentTimeMillis());
        textMessageResponse.setMsgId(1L);
        textMessageResponse.setToUserName(baseMessageAndEventRequest.getFromUserName());
        textMessageResponse.setFromUserName(baseMessageAndEventRequest.getToUserName());
        textMessageResponse.setMsgType(MessageTypeEnum.text.toString());
        textMessageResponse.setContent(message);
        return textMessageResponse;
    }
}
