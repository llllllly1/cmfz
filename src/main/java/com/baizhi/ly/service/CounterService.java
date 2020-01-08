package com.baizhi.ly.service;

import com.baizhi.ly.entity.Counter;

import java.util.List;

public interface CounterService {
    //根据用户id查询计数器
    public abstract List<Counter> queryOne(String uid);

    //根据用户id添加计数器
    public abstract Counter insert(Counter counter,String uid,String courseID);
    //根据用户id修改计数器
    public abstract Counter update(Counter counter);
    //根据用户id删除计数器
    public abstract Counter delete(String id);

}
