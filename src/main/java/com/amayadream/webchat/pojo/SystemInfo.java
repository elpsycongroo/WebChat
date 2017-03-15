package com.amayadream.webchat.pojo;

import com.amayadream.webchat.utils.StringUtil;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/3/15.
 */

@Repository(value="systemInfo")
public class SystemInfo {
    private  String sysinfoid = StringUtil.getGuid();
    private  Integer pagesize;
    private  Integer onlineshow;
    private  Integer myinfo;
    private  String userid;
    private  Integer friendstip;

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getOnlineshow() {
        return onlineshow;
    }

    public void setOnlineshow(Integer onlineshow) {
        this.onlineshow = onlineshow;
    }

    public Integer getMyinfo() {
        return myinfo;
    }

    public void setMyinfo(Integer myinfo) {
        this.myinfo = myinfo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSysinfoid() {
        return sysinfoid;
    }

    public void setSysinfoid(String sysinfoid) {
        this.sysinfoid = sysinfoid;
    }

    public Integer getFriendstip() {
        return friendstip;
    }

    public void setFriendstip(Integer friendstip) {
        this.friendstip = friendstip;
    }
}
