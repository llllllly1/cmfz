package com.baizhi.ly.service.impl;

import com.baizhi.ly.dao.LogDao;
import com.baizhi.ly.entity.Log;
import com.baizhi.ly.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    //添加日志
    public void insertLog(Log log) {
        logDao.insert(log);
    }
}
