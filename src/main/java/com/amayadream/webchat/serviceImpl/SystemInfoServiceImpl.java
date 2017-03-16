package com.amayadream.webchat.serviceImpl;

import com.amayadream.webchat.dao.ISystemInfoDao;
import com.amayadream.webchat.pojo.SystemInfo;
import com.amayadream.webchat.service.ISystemInfoService;
import com.amayadream.webchat.utils.WordDefined;
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

    WordDefined wordDefined = new WordDefined();

    @Override
    public int saveOrUpdateSystemSettings(SystemInfo systemInfo) {
        String userid = systemInfo.getUserid();
        info = systemInfoDao.selectSystemInfoByUserId(userid);
        return systemInfoDao.updateSettings(systemInfo);
    }

    @Override
    public SystemInfo findSystemSettingsById(String userid) {
        info = systemInfoDao.selectSystemInfoByUserId(userid);
        if(info == null){
            info = new SystemInfo(wordDefined.SYSTEMINFO_PAGESIZE_FIVE,wordDefined.SYSTEMINFO_FRIENDSTIP_YES,wordDefined.SYSTEMINFO_SHOWONLINE_YES,userid,wordDefined.SYSTEMINFO_MYINFO_YES);
            systemInfoDao.insertSettings(info);
        }
        return info;
    }
}
