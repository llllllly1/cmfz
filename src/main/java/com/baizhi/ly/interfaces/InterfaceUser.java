package com.baizhi.ly.interfaces;

import com.baizhi.ly.entity.User;
import com.baizhi.ly.service.UserService;
import com.baizhi.ly.util.HttpUtil;
import com.baizhi.ly.util.SendCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("interfaceUser")
public class InterfaceUser {
    @Autowired
    UserService userService;
    //----------------------------------------------用户登陆接口----------------------------------------------------------------
    @RequestMapping("login")
    public Map loginUser(String phone,String password){
        User user1 = new User();
        user1.setPhone(phone);
        User user = userService.queryOne(user1);
        HashMap map = new HashMap();
        if (user==null){
            map.put("status","-200");
            map.put("message","用户不存在");
        }else if (!password.equals(user.getPassword())){
            map.put("status","-200");
            map.put("message","密码错误");
        }else if (user.getStatus()=="0"){
            map.put("status","-200");
            map.put("message","用户已冻结");
        }else {
            map.put("status","200");
            map.put("message","登陆成功");
            map.put("user",user);
        }
        return map;
    }
//-----------------------------------------------------发送验证码--------------------------------------------------------------------
    @RequestMapping("sendCode")
    public Map sendCode(String phone){
        HashMap map = new HashMap();
        try {
            String s = UUID.randomUUID().toString();
            String code = s.substring(0, 4);
            SendCodeUtil.send(phone,code);
            Jedis jedis = new Jedis("192.168.190.15",7000);
            jedis.set(phone,code);
            map.put("status","200");
            map.put("message","短信发送成功");
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","短信发送失败");
        }
        return map;
    }

    //-------------------------------------------------验证验证码-----------------------------------------------------------------------
    @RequestMapping("selectCode")
    public Map selectCode(String code,String phone){
        HashMap map = new HashMap();
        try {
            Jedis jedis = new Jedis("192.168.190.15",7000);
            String jecode = jedis.get(phone);
            if (code.equals(jecode)){
                map.put("status",200);
                map.put("message","验证成功");
            }else {
                map.put("status",-200);
                map.put("message","验证失败");
            }
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","验证失败");
        }
        return map;
    }
//----------------------------------------------------用户注册接口--------------------------------------------------------------------
    @RequestMapping("insert")
    public Map insertUser(MultipartFile multipartFile, User user, HttpServletRequest request){
        HashMap map = new HashMap();
        user.setStatus("1");
        map.put("status","-200");
        map.put("message","注册失败");
        if (multipartFile==null){
            userService.insert(user);
            map.put("status","200");
            map.put("message","注册成功");
            map.put("user",user);
        }else {
            String photo = HttpUtil.getHttp(multipartFile, request, "/upload/userImg");
            user.setPhoto(photo);
            map.put("status","200");
            map.put("message","注册成功");
            map.put("user",user);
        }
        return map;
    }

    //----------------------------------------------------修改用户信息------------------------------------------------------------------
    @RequestMapping("updateUser")
    public Map updateUser(User user,String uid){
        user.setId(uid);
        HashMap map = new HashMap();
        try{
            User user1 = userService.update(user);
            map.put("status",200);
            map.put("user",user1);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","修改失败");
        }
        return map;
    }

    //--------------------------------------------------金刚道友-----------------------------------------------------------------------
    @RequestMapping("queryAll")
    public Map queryAll(String uid){
        HashMap map = new HashMap();
        try{
            Set<User> users = userService.queryAll(uid);
            map.put("status",200);
            map.put("user",users);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","查询失败");
        }
        return map;
    }
}
