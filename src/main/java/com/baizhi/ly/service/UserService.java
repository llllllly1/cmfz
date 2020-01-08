package com.baizhi.ly.service;

import com.baizhi.ly.DTO.UserDTO;
import com.baizhi.ly.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    //用户活跃度信息统计
    Integer queryUserByTime(String sex, Integer day);
    //用户分布地区统计
    List<UserDTO> queryUserBySexLocation(String sex);
    //添加用户
    String insert(User user);
    //修改用户
    User update(User user);
    //删除用户
    void delete(List id);
    //根据名字查一个
    User queryOne(User user);
    //根据id查一个
    User queryId(String id);
    //随机查询五个用户，除自己
    Set<User> queryAll(String id);
    //分页查询所有用户
    Map queryAllUser(Integer page,Integer rows);
}
