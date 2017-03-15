package com.amayadream.webchat.dao;

import com.amayadream.webchat.pojo.SystemInfo;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/15.
 */

@Service(value="systemInfoDao")
public interface ISystemInfoDao {

    int insertOrUpdate(SystemInfo systemInfo);

    SystemInfo selectSystemInfoByUserId(String userid);

}
