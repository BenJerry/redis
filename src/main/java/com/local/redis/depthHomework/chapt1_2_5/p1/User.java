package com.local.redis.depthHomework.chapt1_2_5.p1;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -90000078L;

    private String username;
    private String password;

    private String nick;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
