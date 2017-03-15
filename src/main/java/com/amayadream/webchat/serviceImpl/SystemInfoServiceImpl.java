package com.amayadream.webchat.serviceImpl;

import com.amayadream.webchat.dao.ISystemInfoDao;
import com.amayadream.webchat.pojo.SystemInfo;
import com.amayadream.webchat.service.ISystemInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/15.
 */

@Service(value="systemInfoService")
public class SystemInfoServiceImpl implements ISystemInfoService {

    @Resource
    private ISystemInfoDao systemInfoDao;
    @Resource
    private SystemInfo info;

    @Override
    public int saveOrUpdateSystemSettings(SystemInfo systemInfo) {
        String userid = systemInfo.getUserid();
        info = systemInfoDao.selectSystemInfoByUserId(userid);
        if(info == null){
            return systemInfoDao.insertSettings(systemInfo);
        }
        return systemInfoDao.updateSettings(systemInfo);
    }

    @Override
    public SystemInfo findSystemSettingsById(String userid) {
        return systemInfoDao.selectSystemInfoByUserId(userid);
    }
}
