package com.base.test.project.easychat.webScoket;

import com.alibaba.fastjson.JSONObject;
import com.base.test.common.constant.Constants;
import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.core.redis.RedisCache;
import com.base.test.project.easychat.entity.SocketEntity;
import com.base.test.project.system.service.impl.SysUserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;



/**
 * 虽然@component默认是单例模式的，但在spring boot 中还是会为每一个webscoket连接初始化一个bean，所以这里使用一个静态的set保存spring boot创建的bean--MyWebscoket
 */
@Slf4j

@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class MyWebScoket {
    //用户的Service层 数据库操作



    @Autowired
    private SysUserServiceImpl userService;

    @Autowired
    private RedisCache redisCache;

    //在静态方法中通过 ApplicationContext 来获取 SysUserServiceImpl 的实例。首先需要在 MyWebScoket 类中注入 ApplicationContext。
    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        MyWebScoket.applicationContext = applicationContext;
    }



    public static MyWebScoket myWebScoket;

    @PostConstruct
    public void init() {
        myWebScoket = this;


    }

    //用来存储每个客户端对应的MyWebScoket对象
    private static CopyOnWriteArraySet<MyWebScoket> webScoketset = new CopyOnWriteArraySet<MyWebScoket>();
    //用来记录sessionid和session之间的绑定关系。

    private static Map<String, Session> map = new HashMap<String, Session>();
    private Session session;//当前会话的session
    private String name;
    private String userId;
    SysUser user = null;//用户实体类

    /**
     * 成功建立连接调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws JsonProcessingException {
        //查询 发送人用户信息


        //user=userService.selectUserByUserId(Long.valueOf(userId));
        user = applicationContext.getBean(SysUserServiceImpl.class).selectUserByUserId(Long.valueOf(userId));
        //存储信息
        this.session = session;
        //取用户的唯一uid 强转下类型
        this.userId = String.valueOf(user.getUserId());
        //取用户的名字
        this.name = user.getUserName();
        //用户的唯一uid 和
        map.put(String.valueOf(user.getUserId()), session);
        webScoketset.add(this);//加入set中
     //   this.session.getAsyncRemote().sendText(user.getUserName() + "上线了" + "频道号是" + user.getUserId() +",当前在线人数为：" +applicationContext.getBean(RedisCache.class).keys(Constants.LOGIN_TOKEN_KEY + "*").size() +",当前群聊人数为：" + webScoketset.size());
        String notice=user.getUserName() + "上线了" + "频道号是" + user.getUserId() +",当前在线人数为：" +applicationContext.getBean(RedisCache.class).keys(Constants.LOGIN_TOKEN_KEY + "*").size() +",当前群聊人数为：" + webScoketset.size();

        notice(notice);
    }


    /**
     * 连接关闭调用的方法
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {

        webScoketset.remove(this);//加入set中
        Boolean aa = map.remove(String.valueOf(user.getUserId()), session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 收到客户消息后调用的方法
     *
     * @param session
     */
    @OnMessage
    public void OnMessage(String message, Session session) throws IOException {
        //message不是普通的string,而是我们定义的SocketEntity,json字符串
        SocketEntity socketEntity = new ObjectMapper().readValue(message, SocketEntity.class);
        //解析json
        JSONObject jsonObject = JSONObject.parseObject(message);

        //单聊
        if (socketEntity.getType() == 1) {
            //单聊：需要找到发送者和接收者即可
            socketEntity.setFromUser(String.valueOf(user.getUserId()));//发送者
            //确定发送人
            Session fromsession = map.get(socketEntity.getFromUser());
            //确定受信人
            Session tosession = map.get(socketEntity.getToUser());
            //通道保持畅通
            if (tosession != null) {
                // 存储聊天记录  已读  存进聊天记录表
               // UserService.instUser(user.getUid(), Integer.parseInt(socketEntity.getToUser()),jsonObject.get("message").toString(),jsonObject.get("type").toString(),1);

                fromsession.getAsyncRemote().sendText(name + ":" + socketEntity.getMessage());//发送消息
                tosession.getAsyncRemote().sendText(name + ":" + socketEntity.getMessage());//发送消息
            } else {
                // 存储聊天记录  未读  存进聊天记录表
                //UserService.instUser(user.getUid(), Integer.parseInt(socketEntity.getToUser()),jsonObject.get("message").toString(),jsonObject.get("type").toString(),0);

                fromsession.getAsyncRemote().sendText("系统消息:对方不在线或者您输入的频道号有误");//发送消息
            }
        } else {
            //   广播群发给每一个客户端
            broadcast(socketEntity, name);
        }

    }


    /**
     * 群发消息
     *
     * @param socketEntity
     */
    private void broadcast(SocketEntity socketEntity, String name) {
        for (MyWebScoket myWebScoket : webScoketset) {
            //发送消息
            myWebScoket.session.getAsyncRemote().sendText(name + ":" + socketEntity.getMessage());
        }
    }
    /**
     * 上线通知
     *
     * @param notice
     */
    private void notice(String notice) {
        for (MyWebScoket myWebScoket : webScoketset) {
            //发送消息
            myWebScoket.session.getAsyncRemote().sendText(notice);
        }
    }

}

