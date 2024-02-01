package com.base.test.project.tio.server;

import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.WsServerAioListener;

/**
 * @author hwf
 * @date 2023/5/26
 */
@Slf4j
public class ServerAioListener extends WsServerAioListener {
    public static final ServerAioListener me = new ServerAioListener();
    private ServerAioListener() {
    }
    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        super.onAfterConnected(channelContext, isConnected, isReconnect);
        if (log.isInfoEnabled()) {
            log.info("onAfterConnected\r\n{}", channelContext);
        }
    }
    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {
        WsPacket wsPacket = (WsPacket) packet;
//        Tio.send(channelContext,wsPacket);
//        super.onAfterSent(channelContext, packet, isSentSuccess);
        if (log.isInfoEnabled()) {
            log.info("onAfterSent\r\n{}\r\n{}\r\n{}\r\n{}",wsPacket.getWsBodyText(),wsPacket.getBody(), packet.logstr(), channelContext);
        }
    }
    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        super.onBeforeClose(channelContext, throwable, remark, isRemove);
        if (log.isInfoEnabled()) {
            log.info("onBeforeClose\r\n{}", channelContext);
        }
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
        if (wsSessionContext != null && wsSessionContext.isHandshaked()) {
            int count = Tio.getAllChannelContexts(channelContext.tioConfig).getObj().size();
            String msg = channelContext.getClientNode().toString() + " 离开了，现在共有【" + count + "】人在线";
            log.info(msg);
        }
    }
    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
        WsPacket wsPacket = (WsPacket) packet;
        super.onAfterDecoded(channelContext, packet, packetSize);
        if (log.isInfoEnabled()) {
            log.info("onAfterDecoded\r\n{}\r\n{}\r\n{}\r\n{}",wsPacket.getWsBodyText(),wsPacket.getBody(), packet.logstr(), channelContext);
        }
    }
    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {
        super.onAfterReceivedBytes(channelContext, receivedBytes);
        if (log.isInfoEnabled()) {
            log.info("onAfterReceivedBytes\r\n{}", channelContext);
        }
    }
    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {
        super.onAfterHandled(channelContext, packet, cost);
        if (log.isInfoEnabled()) {
            log.info("onAfterHandled\r\n{}\r\n{}", packet.logstr(), channelContext);
        }
    }
}
