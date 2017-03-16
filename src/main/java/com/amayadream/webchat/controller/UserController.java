package com.amayadream.webchat.controller;

import com.amayadream.webchat.pojo.SystemInfo;
import com.amayadream.webchat.pojo.User;
import com.amayadream.webchat.service.ILogService;
import com.amayadream.webchat.service.ISystemInfoService;
import com.amayadream.webchat.service.IUserService;
import com.amayadream.webchat.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * NAME   :  WebChat/com.amayadream.webchat.controller
 * Author :  Amayadream
 * Date   :  2016.01.09 17:56
 * TODO   :  用户控制器
 */
@Controller
@SessionAttributes("userid")//将ModelMap中的userid转存到session中以便跨request访问
public class UserController {
    @Resource private User user;
    @Resource private IUserService userService;
    @Resource private ILogService logService;
    @Resource private SystemInfo systemInfo;
    @Resource private ISystemInfoService systemInfoService;

    /**
     * 聊天主页
     */
    @RequestMapping(value = "chat")
    public ModelAndView getIndex(HttpSession session){
        ModelAndView view = new ModelAndView("index");
        if(session.getAttribute("userview") != null){
            session.removeAttribute("userview");
        }
        return view;
    }

    /**
     * 显示个人信息页面
     * 指定请求的类型为GET
     */
    @RequestMapping(value = "{userid}/info", method = RequestMethod.GET)//参数中使用@ModelAttribute注解将之前存储于session中的属性绑定到方法的入参
    public ModelAndView selectUserByUserid(@PathVariable("userid") String userid, @ModelAttribute("userid") String sessionid){
        ModelAndView view = new ModelAndView("information");
        user = userService.selectUserByUserid(userid);
        view.addObject("user", user);
        return view;
    }

    /**
     * 显示个人信息编辑页面
     * @param userid
     * @param sessionid
     * @return
     */
    @RequestMapping(value = "{userid}/config")//参数中使用@ModelAttribute注解将之前存储于session中的属性绑定到方法的入参
    public ModelAndView setting(@PathVariable("userid") String userid, @ModelAttribute("userid") String sessionid){
        ModelAndView view = new ModelAndView("info-setting");
        user = userService.selectUserByUserid(userid);
        //将user 装入模型中在jsp回显 装入session中亦可
        view.addObject("user", user);
        return view;
    }

    /**
     * 查看用户信息
     * @param userid
     * @return
     */
    @RequestMapping(value = "{userid}/link-to-user-home")
    public ModelAndView showUserInfo(@PathVariable("userid") String userid,HttpSession session,WordDefined defined,HttpServletRequest req){
        ModelAndView view = new ModelAndView("userinfo");
        systemInfo = systemInfoService.findSystemSettingsById(userid);
        String infoUserId = (String)session.getAttribute("userid");
        if(!infoUserId.equals(userid) && systemInfo.getMyinfo() == defined.SYSTEMINFO_MYINFO_NO){
            req.setAttribute("error","["+userid+"]设置了不允许查看资料！");
            return view;
        }
        user = userService.selectUserByUserid(userid);
        session.setAttribute("userview",user);
        if(infoUserId.equals(userid)) {
            req.setAttribute("message", "这是你自己");
        }
        return view;
    }

    /**
     * 更新用户信息
     * 限定使用POST
     * @param userid
     * @param sessionid
     * @param user
     * @return
     */
    @RequestMapping(value = "{userid}/update", method = RequestMethod.POST)
    public String  update(@PathVariable("userid") String userid, @ModelAttribute("userid") String sessionid, User user, RedirectAttributes attributes,
                          NetUtil netUtil, LogUtil logUtil, CommonDate date, WordDefined defined, HttpServletRequest request){
        boolean flag = userService.update(user);
        if(flag){
            logService.insert(logUtil.setLog(userid, date.getTime24(), defined.LOG_TYPE_UPDATE, defined.LOG_DETAIL_UPDATE_PROFILE, netUtil.getIpAddress(request)));
            //传递到jsp页面后从session中删除下列信息
            attributes.addFlashAttribute("message", "["+userid+"]资料更新成功!");
        }else{
            attributes.addFlashAttribute("error", "["+userid+"]资料更新失败!");
        }
        return "redirect:/{userid}/config";
    }

    /**
     * 修改密码
     * @param userid
     * @param oldpass
     * @param newpass
     * @return
     */
    @RequestMapping(value = "/{userid}/pass", method = RequestMethod.POST)
    public String changePassword(@PathVariable("userid") String userid, String oldpass, String newpass, RedirectAttributes attributes,
                                 NetUtil netUtil, LogUtil logUtil, CommonDate date, WordDefined defined, HttpServletRequest request){
        user = userService.selectUserByUserid(userid);
        if(oldpass.equals(user.getPassword())){
            user.setPassword(newpass);
            boolean flag = userService.update(user);
            if(flag){
                logService.insert(logUtil.setLog(userid, date.getTime24(), defined.LOG_TYPE_UPDATE, defined.LOG_DETAIL_UPDATE_PASSWORD, netUtil.getIpAddress(request)));
                attributes.addFlashAttribute("message", "["+userid+"]密码更新成功!");
            }else{
                attributes.addFlashAttribute("error", "["+userid+"]密码更新失败!");
            }
        }else{
            attributes.addFlashAttribute("error", "密码错误!");
        }
        return "redirect:/{userid}/config";
    }

    /**
     * 头像上传
     * @param userid
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "{userid}/upload")
    public String upload(@PathVariable("userid") String userid, MultipartFile file, HttpServletRequest request, UploadUtil uploadUtil,
                         RedirectAttributes attributes, NetUtil netUtil, LogUtil logUtil, CommonDate date, WordDefined defined){
        try{
            String fileurl = uploadUtil.upload(request, "upload", userid);
            user = userService.selectUserByUserid(userid);
            user.setProfilehead(fileurl);
            boolean flag = userService.update(user);
            if(flag){
                logService.insert(logUtil.setLog(userid, date.getTime24(), defined.LOG_TYPE_UPDATE, defined.LOG_DETAIL_UPDATE_PROFILEHEAD, netUtil.getIpAddress(request)));
                attributes.addFlashAttribute("message", "["+userid+"]头像更新成功!");
            }else{
                attributes.addFlashAttribute("error", "["+userid+"]头像更新失败!");
            }
        } catch (Exception e){
            attributes.addFlashAttribute("error", "["+userid+"]头像更新失败!");
        }
        return "redirect:/{userid}/config";
    }

    /**
     * 获取用户头像
     * @param userid
     */
    @RequestMapping(value = "{userid}/head")
    public void head(@PathVariable("userid") String userid, HttpServletRequest request, HttpServletResponse response){
        try {
            user = userService.selectUserByUserid(userid);
            String path = user.getProfilehead();
            String rootPath = request.getSession().getServletContext().getRealPath("/");
            String picturePath = rootPath + path;
            response.setContentType("image/jpeg; charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(picturePath);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputStream = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
