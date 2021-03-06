package com.amayadream.webchat.controller;

import com.amayadream.webchat.pojo.Log;
import com.amayadream.webchat.pojo.SystemInfo;
import com.amayadream.webchat.service.ILogService;
import com.amayadream.webchat.service.ISystemInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Author :  Amayadream
 * Date   :  2016.01.10 00:23
 * TODO   :
 */
@Controller
public class LogController {
    @Resource
    private Log log;
    @Resource
    private ILogService logService;
    @Resource
    private SystemInfo systemInfo;
    @Resource
    private ISystemInfoService systemInfoService;

    @RequestMapping(value = "{userid}/log")
    public ModelAndView selectAll(@PathVariable("userid") String userid, @RequestParam(defaultValue = "1") int page, HttpSession session) {
        systemInfo = (SystemInfo) session.getAttribute("systemInfo");
        int pageSize = systemInfo.getPagesize();
        ModelAndView view = new ModelAndView("log");
        //此处原分页逻辑存在问题.详见service
        List<Log> list = logService.selectLogByUserid(userid, page, pageSize);
        int count = logService.selectCountByUserid(userid, pageSize);
        view.addObject("list", list);
        view.addObject("count", count);
        view.addObject("page",page);
        view.addObject("pageSize",pageSize);
        return view;
    }

}
