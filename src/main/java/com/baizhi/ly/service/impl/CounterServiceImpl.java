package com.baizhi.ly.service.impl;

import com.baizhi.ly.dao.CounterDao;
import com.baizhi.ly.entity.Counter;
import com.baizhi.ly.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    CounterDao counterDao;


//    ------------------------------------------------根据用户id查询计数器----------------------------------------------------------------------
    public List<Counter> queryOne(String uid) {
        Counter counter1 = new Counter();
        counter1.setUserId(uid);
        List<Counter> counters = counterDao.select(counter1);
        return counters;
    }
    //    ------------------------------------------------根据用户id添加计数器-----------------------------------------------------
    public Counter insert(Counter counter,String uid,String courseID) {
        counter.setId(UUID.randomUUID().toString());
        counter.setUserId(uid);
        counter.setCourseId(courseID);
        counter.setCreateDate(new Date());
        counterDao.insert(counter);
        return counter;
    }
    //    ------------------------------------------------根据用户id修改计数器-----------------------------------------------------
    public Counter update(Counter counter) {
        counterDao.updateByPrimaryKeySelective(counter);
        return counter;
    }
    //    ------------------------------------------------根据用户id删除计数器-----------------------------------------------------
    public Counter delete(String id) {
        Counter counter = new Counter();
        counter.setId(id);
        Counter counter1 = counterDao.selectOne(counter);
        counterDao.deleteByPrimaryKey(id);
        return counter1;
    }
}
