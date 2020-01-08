package com.baizhi.ly.service;

import com.baizhi.ly.entity.Guru;

import java.util.List;

public interface GuruService {
    //查询所有上师
    public abstract List<Guru> queryAll();
}
