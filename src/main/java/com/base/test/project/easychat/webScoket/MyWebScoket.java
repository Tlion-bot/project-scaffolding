package com.base.test.project.easychat.webScoket;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.base.test.common.constant.Constants;
import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.core.redis.RedisCache;
import com.base.test.common.exception.CustomException;
import com.base.test.project.business.domain.Chatting;
import com.base.test.project.business.service.ChattingService;
import com.base.test.project.easychat.entity.SocketEntity;
import com.base.test.project.system.service.impl.SysUserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;


/**
 * 虽然@component默认是单例模式的，但在spring boot 中还是会为每一个webscoket连接初始化一个bean，所以这里使用一个静态的set保存spring boot创建的bean--MyWebscoket
 */
@Slf4j

@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class MyWebScoket {
    //用户的Service层 数据库操作


    private static SysUserServiceImpl userService;

    @Autowired
    public void setSysUserServiceImpl(SysUserServiceImpl userService) {
        MyWebScoket.userService = userService;
    }


    private static RedisCache redisCache;

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        MyWebScoket.redisCache = redisCache;
    }


    private static ChattingService chattingService;

    @Autowired
    public void setChattingService(ChattingService chattingService) {
        MyWebScoket.chattingService = chattingService;
    }

    //在静态方法中通过 ApplicationContext 来获取 SysUserServiceImpl 的实例。首先需要在 MyWebScoket 类中注入 ApplicationContext。
    // private static ApplicationContext applicationContext;

/*    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        MyWebScoket.applicationContext = applicationContext;
    }*/


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


        user = userService.selectUserByUserId(Long.valueOf(userId));
        //user = applicationContext.getBean(SysUserServiceImpl.class).selectUserByUserId(Long.valueOf(userId));
        //存储信息
        if (ObjectUtil.isEmpty(user)) {
            new CustomException("未找到用户");
            System.out.println("未找到用户");
        }
        this.session = session;
        //取用户的唯一uid 强转下类型
        this.userId = String.valueOf(user.getUserId());
        //取用户的名字
        this.name = user.getUserName();
        //用户的唯一uid 和
        map.put(String.valueOf(user.getUserId()), session);
        webScoketset.add(this);//加入set中
        //   this.session.getAsyncRemote().sendText(user.getUserName() + "上线了" + "频道号是" + user.getUserId() +",当前在线人数为：" +applicationContext.getBean(RedisCache.class).keys(Constants.LOGIN_TOKEN_KEY + "*").size() +",当前群聊人数为：" + webScoketset.size());
        String notice = user.getUserName() + "上线了" + "频道号是" + user.getUserId() + ",当前在线人数为：" + redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*").size() + ",当前群聊人数为：" + webScoketset.size();

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
                Chatting chatting = new Chatting();
                chatting
                        .setFid(user.getUserId())
                        .setTid(Long.valueOf(socketEntity.getToUser()))
                        .setMessage(jsonObject.get("message").toString())
                        .setType(Integer.valueOf(jsonObject.get("type").toString()))
                        .setStime(DateUtil.date())
                        .setStatus(1)
                ;
                // UserService.instUser(user.getUid(), Integer.parseInt(socketEntity.getToUser()),jsonObject.get("message").toString(),jsonObject.get("type").toString(),1);

                fromsession.getAsyncRemote().sendText(name + "(本人)" + ":" + socketEntity.getMessage());//发送消息
                tosession.getAsyncRemote().sendText(name + ":" + socketEntity.getMessage());//发送消息
                chattingService.save(chatting);
            } else {


                // 存储聊天记录  未读  存进聊天记录表
                //UserService.instUser(user.getUid(), Integer.parseInt(socketEntity.getToUser()),jsonObject.get("message").toString(),jsonObject.get("type").toString(),0);

                List<Long> sysUsersIds = userService.list(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getDelFlag, 0)).stream().map(SysUser::getUserId).collect(Collectors.toList());
                if (!sysUsersIds.contains(Long.valueOf(socketEntity.getToUser()))) {
                    fromsession.getAsyncRemote().sendText("系统消息:您输入的频道号有误,未找到用户");//发送消息

                } else {
                    for (Long id : sysUsersIds) {
                        if (Long.valueOf(socketEntity.getToUser()).equals(id)) {
                            Chatting chatting = new Chatting();
                            chatting
                                    .setFid(user.getUserId())
                                    .setTid(Long.valueOf(socketEntity.getToUser()))
                                    .setMessage(jsonObject.get("message").toString())
                                    .setType(Integer.valueOf(jsonObject.get("type").toString()))
                                    .setStime(DateUtil.date())
                                    .setStatus(0)
                            ;

                            fromsession.getAsyncRemote().sendText(name + "(本人)" + ":" + socketEntity.getMessage());//发送消息
                            fromsession.getAsyncRemote().sendText("系统消息:对方不在线");//发送消息
                            chattingService.save(chatting);
                        }
                    }

                }
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
        CopyOnWriteArraySet<MyWebScoket> newSet = webScoketset.stream()
                .filter(ws -> !ws.equals(this))
                .collect(Collectors.toCollection(CopyOnWriteArraySet::new));
        for (MyWebScoket myWebScoket : newSet) {
            //发送消息
            myWebScoket.session.getAsyncRemote().sendText(name + ":" + socketEntity.getMessage());
        }
        this.session.getAsyncRemote().sendText(name + "(本人)" + ":" + socketEntity.getMessage());
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

