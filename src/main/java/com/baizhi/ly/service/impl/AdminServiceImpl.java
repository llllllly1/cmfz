package com.baizhi.ly.service.impl;

import com.baizhi.ly.dao.AdminDao;
import com.baizhi.ly.entity.Admin;
import com.baizhi.ly.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    //登陆查一个
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Admin selectOne(String name) {
        Admin admin = adminDao.selectOne(new Admin(null, name, null));
        return admin;
    }
}
