package com.baizhi.ly.controller;

import com.baizhi.ly.DTO.UserDTO;
import com.baizhi.ly.entity.User;
import com.baizhi.ly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping("queryUserTime")
    //用户注册信息统计
    public Map queryUserTime(){
        Map map = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userService.queryUserByTime("男",1));
        manList.add(userService.queryUserByTime("男",7));
        manList.add(userService.queryUserByTime("男",30));
        manList.add(userService.queryUserByTime("男",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userService.queryUserByTime("女",1));
        womenList.add(userService.queryUserByTime("女",7));
        womenList.add(userService.queryUserByTime("女",30));
        womenList.add(userService.queryUserByTime("女",365));
        map.put("man",manList);
        map.put("women",womenList);
        return map;
    }

    //用户地区分布情况统计
    @RequestMapping("queryUserLocation")
    public Map showUserLocation(){
        List<UserDTO> mandtoList = userService.queryUserBySexLocation("男");
        List<UserDTO> womendtoList = userService.queryUserBySexLocation("女");
        Map map = new HashMap();
        map.put("man",mandtoList);
        map.put("women",womendtoList);
        return map;
    }

    //查询所有用户
    @RequestMapping("queryAllUser")
    public Map queryAllUser(Integer page,Integer rows){
        Map map = userService.queryAllUser(page, rows);
        return map;
    }

    //增删改用户
    @RequestMapping("save")
    public Map insertUser(User user,String oper,String [] id){
        HashMap map = new HashMap();
        if("add".equals(oper)){
            String userId = userService.insert(user);
            map.put("userId",userId);
        }else if ("edit".equals(oper)){
            if (user.getPhoto().equals("")){
                User user1 = userService.queryId(user.getId());
                user.setPhoto(user1.getPhoto());
            }
            User user1 = userService.update(user);
            map.put("userId",user1.getId());
        }else {
            userService.delete(Arrays.asList(id));
        }
        return map;
    }

    //上传用户头像
    @RequestMapping("uploadUser")
    public void uploadUser(MultipartFile photo, String userId, HttpSession session){
        String realPath = session.getServletContext().getRealPath("upload/img/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String filename = new Date().getTime()+"_"+photo.getOriginalFilename();
        //文件上传
        try {
            photo.transferTo(new File(realPath,filename));
            User user = new User();
            user.setId(userId);
            user.setPhoto("upload/img/"+filename);
            User user1 = userService.queryOne(user);
            if (photo.getOriginalFilename().equals(null)||photo.getOriginalFilename().equals("")||photo.getOriginalFilename().equals(' ')){
                System.out.println("没有文件"+user);
            }else {
                userService.update(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
