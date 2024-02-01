package com.base.test.project.tio.server;

import org.tio.server.ServerTioConfig;
import org.tio.utils.jfinal.P;

import java.io.IOException;

/**
 * @author hwf
 * @date 2023/5/26
 */
public class WsServerStarter {
    private org.tio.websocket.server.WsServerStarter wsServerStarter;
    private static ServerTioConfig serverTioConfig;
    /**
     *
     * @author tanyaowu
     */
    public WsServerStarter(int port, WsMsgHandler wsMsgHandler) throws Exception {
        wsServerStarter = new org.tio.websocket.server.WsServerStarter(port, wsMsgHandler);
        serverTioConfig = wsServerStarter.getServerTioConfig();
        serverTioConfig.setName(WsServerConfig.PROTOCOL_NAME);
        serverTioConfig.setServerAioListener(ServerAioListener.me);
        //设置ip监控
//        serverTioConfig.setIpStatListener(ShowcaseIpStatListener.me);
        //设置ip统计时间段
//        serverTioConfig.ipStats.addDurations(ShowcaseServerConfig.IpStatDuration.IPSTAT_DURATIONS);
        //设置心跳超时时间
        serverTioConfig.setHeartbeatTimeout(WsServerConfig.HEARTBEAT_TIMEOUT);
        if (P.getInt("ws.use.ssl", 1) == 1) {
            //如果你希望通过wss来访问，就加上下面的代码吧，不过首先你得有SSL证书（证书必须和域名相匹配，否则可能访问不了ssl）
//            String keyStoreFile = "classpath:config/ssl/keystore.jks";
//            String trustStoreFile = "classpath:config/ssl/keystore.jks";
//            String keyStorePwd = "214323428310224";
            String keyStoreFile = P.get("ssl.keystore", null);
            String trustStoreFile = P.get("ssl.truststore", null);
            String keyStorePwd = P.get("ssl.pwd", null);
            serverTioConfig.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
        }
    }
    /**
     * @author tanyaowu
     * @throws IOException
     */
    public static void start() throws Exception {
        WsServerStarter appStarter = new WsServerStarter(P.getInt("ws.server.port", 9327), WsMsgHandler.me);
        appStarter.wsServerStarter.start();
    }
    /**
     * @return the serverTioConfig
     */
    public static ServerTioConfig getServerTioConfig() {
        return serverTioConfig;
    }
    public org.tio.websocket.server.WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }
}
