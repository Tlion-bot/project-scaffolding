package com.base.test.project.easychat.entity;

/**
 * zyh
 * 2020/07/29
 */
public class SocketEntity {
    private int type;//聊天类型，0群聊，1单聊；
    private String fromUser;//发送者
    private String toUser;//接收者；（可以是用户名等）session.getId（）
    private String message;//消息
    public int getType() {
        return type;
    }

    public void setType( int type ) {
        this.type=type;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser( String fromUser ) {
        this.fromUser=fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser( String toUser ) {
        this.toUser=toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message=message;
    }


}
