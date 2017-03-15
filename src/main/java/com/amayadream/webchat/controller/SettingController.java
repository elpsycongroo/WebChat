package com.amayadream.webchat.controller;

import com.amayadream.webchat.pojo.SystemInfo;
import com.amayadream.webchat.pojo.User;
import com.amayadream.webchat.service.ILogService;
import com.amayadream.webchat.service.ISystemInfoService;
import com.amayadream.webchat.service.IUserService;
import com.amayadream.webchat.utils.CommonDate;
import com.amayadream.webchat.utils.LogUtil;
import com.amayadream.webchat.utils.NetUtil;
import com.amayadream.webchat.utils.WordDefined;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/13.
 */

@Controller
public class SettingController {

    @Resource
    private User user;
    @Resource
    private SystemInfo systemInfo;
    @Resource
    private IUserService userService;
    @Resource
    private ISystemInfoService systemInfoService;
    @Resource
    private ILogService logService;

    @RequestMapping(value = "{userid}/system-setting")
    public ModelAndView systemSetting(@PathVariable("userid") String userid) {
        ModelAndView view = new ModelAndView("system-setting");
        user = userService.selectUserByUserid(userid);
        systemInfo = systemInfoService.findSystemSettingsById(userid);
        view.addObject("user", user);
        view.addObject("systemInfo",systemInfo);
        return view;
    }

    @RequestMapping(value = "{userid}/updateSystem-setting", method = RequestMethod.POST)
    public String updateSystemSetting(@PathVariable("userid") String userid, SystemInfo systemInfo, RedirectAttributes attributes, NetUtil netUtil, LogUtil logUtil, CommonDate date, WordDefined defined, HttpServletRequest request) {
//      ModelAndView view = new ModelAndView("system-setting");
        try {
            systemInfoService.saveOrUpdateSystemSettings(systemInfo);
            logService.insert(logUtil.setLog(userid,date.getTime24(),defined.LOG_TYPE_UPDATE,defined.LOG_DETAIL_UPDATE_SYSINFO,netUtil.getIpAddress(request)));
            attributes.addFlashAttribute("message", "["+userid+"]系统设置更新成功!");
        }catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "[" + userid + "]系统设置更新失败!");
        }
        return "redirect:/{userid}/system-setting";
    }


}
