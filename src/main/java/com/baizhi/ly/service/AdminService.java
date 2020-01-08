package com.baizhi.ly.service;

import com.baizhi.ly.entity.Admin;

public interface AdminService {
    //登陆查一个
    abstract Admin selectOne(String name);
}
