package com.amayadream.webchat.service;

import com.amayadream.webchat.pojo.SystemInfo;

/**
 * Created by Administrator on 2017/3/15.
 */


public interface ISystemInfoService {
    int updateSystemSettings(SystemInfo systemInfo);
    SystemInfo findSystemSettingsById(String userid);
}
