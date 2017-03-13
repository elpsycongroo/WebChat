package com.amayadream.webchat.controller;

import com.amayadream.webchat.pojo.User;
import com.amayadream.webchat.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/13.
 */

@Controller
public class SettingController {

    @Resource
    private User user;
    @Resource private IUserService userService;

    @RequestMapping(value = "{userid}/system-setting")
    public ModelAndView systemSetting(@PathVariable("userid") String userid){
        ModelAndView view = new ModelAndView("system-setting");
        user = userService.selectUserByUserid(userid);
        view.addObject("user",user);
        return view;
    }

}
