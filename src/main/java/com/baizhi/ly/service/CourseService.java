package com.baizhi.ly.service;

import com.baizhi.ly.entity.Course;

import java.util.List;

public interface CourseService {
    //根据用户id查询功课
    public abstract List<Course> queryByUser(String uid);
    //根据用户id添加功课
    public abstract Course insert(Course course,String uid);
    //删除功课
    public abstract Course delete(String id);
}
