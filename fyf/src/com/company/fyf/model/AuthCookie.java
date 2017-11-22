package com.company.fyf.model;

/**
 * Created by liyaxing on 2017/8/19.
 */
public class AuthCookie {

    private String auth ;
    private String userid ;
    private String username ;
    private String groupid ;
    private String nickname ;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "AuthCookie{" +
                "auth='" + auth + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", groupid='" + groupid + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
