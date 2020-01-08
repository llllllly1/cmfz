package com.baizhi.ly.dao;

import com.baizhi.ly.DTO.UserDTO;
import com.baizhi.ly.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UsersDao extends Mapper<User>, InsertListMapper<User>, DeleteByIdListMapper<User,String> {

    //用户活跃度查询
    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);

    //用户所在地查询
    List<UserDTO> queryUserBySexLocation(String sex);
}
