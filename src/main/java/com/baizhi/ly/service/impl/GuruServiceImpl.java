package com.baizhi.ly.service.impl;

import com.baizhi.ly.dao.GuruDao;
import com.baizhi.ly.entity.Guru;
import com.baizhi.ly.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;

    //查询所有上师
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> queryAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }
}
