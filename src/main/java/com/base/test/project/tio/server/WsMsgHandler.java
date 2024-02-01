package com.base.test.project.tio.server;


import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;

/**
 * @author hwf
 * @date 2023/5/26
 */
@Slf4j
public class WsMsgHandler implements IWsMsgHandler {
    public static final WsMsgHandler me = new WsMsgHandler();
    private WsMsgHandler() {
    }

    /**
     *  握手时走这个方法，业务可以在这里获取cookie，request参数等
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientip = httpRequest.getClientIp();
        String sendUserId = httpRequest.getParam("userId");

        /*ChannelContext channel = Tio.getByBsId(channelContext.tioConfig, sendUserId);
        if (channel != null) {
            httpResponse.setStatus(HttpResponseStatus.C201);
            return httpResponse;
        }*/
        Tio.bindUser(channelContext,sendUserId);
        log.info("收到来自{}的ws握手包\r\n{}", clientip, httpRequest.toString());
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        int count = Tio.getAllChannelContexts(channelContext.tioConfig).getObj().size();
        log.info("握手后总在线人数{}", count);
        Tio.bindBsId(channelContext, channelContext.userid);
        /*String bsId = channelContext.getBsId();
        //获取未读消息
        int msgCount = imMsgService.getNotReadMsg(bsId);
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText("notRead:"+msgCount, DxWsServerConfig.CHARSET);
        Tio.sendToBsId(channelContext.tioConfig, bsId, wsResponse);*/
    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        log.info("*************onClose:{}", channelContext.getBsId());
        Tio.remove(channelContext, "receive close flag");
        //Tio.unbindBsId();
        return null;
    }

    /*
     * 字符消息（binaryType = blob）过来后会走这个方法
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.get();
        HttpRequest httpRequest = wsSessionContext.getHandshakeRequest();//获取websocket握手包
        if (log.isDebugEnabled()) {
            log.debug("握手包:{}", httpRequest);
        }

//		log.info("收到ws消息:{}", text);

        if (Objects.equals("心跳内容", text)) {
            return null;
        }
        //channelContext.getToken()
        //String msg = channelContext.getClientNode().toString() + " 说：" + text;
        String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, WsServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.tioConfig, Const.GROUP_ID_PREFIX, wsResponse);

        //返回值是要发送给客户端的内容，一般都是返回null
        return null;
    }
}
