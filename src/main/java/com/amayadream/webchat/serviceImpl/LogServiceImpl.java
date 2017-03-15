package com.amayadream.webchat.serviceImpl;

import com.amayadream.webchat.dao.ILogDao;
import com.amayadream.webchat.pojo.Log;
import com.amayadream.webchat.service.ILogService;
import com.amayadream.webchat.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * NAME   :  WebChat/com.amayadream.webchat.serviceImpl
 * Author :  Amayadream
 * Date   :  2016.01.09 16:43
 * TODO   :
 */
@Service(value = "logService")
public class LogServiceImpl implements ILogService {

    @Resource private ILogDao logDao;
    @Resource private Log log;

    @Override
    public List<Log> selectAll(int page, int pageSize) {
        return logDao.selectAll(page, pageSize);
    }

    @Override
    public List<Log> selectLogByUserid(String userid, int page, int pageSize) {
        //修改整体逻辑到第一行开始
        int start = 0;
        int end = pageSize;
        if(page != 1) {
            start = pageSize * (page - 1) ;
            //此处逻辑有误 修改为pageSize
            end = pageSize ;
        }
        return logDao.selectLogByUserid(userid, start, end);
    }

    @Override
    public int selectCount(int pageSize) {
        int pageCount = Integer.parseInt(logDao.selectCount().getUserid());
        return pageCount % pageSize == 0 ? pageCount/pageSize : pageCount/pageSize + 1;
    }

    @Override
    public int selectCountByUserid(String userid, int pageSize) {
        int pageCount = Integer.parseInt(logDao.selectCountByUserid(userid).getUserid());
        return pageCount % pageSize == 0 ? pageCount/pageSize : pageCount/pageSize + 1;
    }


    @Override
    public boolean insert(Log log) {
        //生成UUID
        log.setId(StringUtil.getGuid());
        return logDao.insert(log);
    }

    @Override
    public boolean delete(String id) {
        return logDao.delete(id);
    }

    @Override
    public boolean deleteThisUser(String userid) {
        return logDao.deleteThisUser(userid);
    }

    @Override
    public boolean deleteAll() {
        return logDao.deleteAll();
    }
}
