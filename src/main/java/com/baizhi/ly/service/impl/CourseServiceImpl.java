package com.baizhi.ly.service.impl;

import com.baizhi.ly.dao.CourseDao;
import com.baizhi.ly.entity.Course;
import com.baizhi.ly.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseDao courseDao;

//    ------------------------------------------------根据用户id查询功课---------------------------------------------------------------------
    public List<Course> queryByUser(String uid) {
        Course course = new Course();
        course.setUserId(uid);
        List<Course> courses = courseDao.select(course);
        return courses;
    }

//-----------------------------------------------------添加功课----------------------------------------------------------------------
    public Course insert(Course course,String uid) {
        course.setUserId(uid);
        course.setId(UUID.randomUUID().toString());
        course.setCreateDate(new Date());
        courseDao.insert(course);
        return course;
    }
//-----------------------------------------------------删除功课-------------------------------------------------------------
    public Course delete(String id) {
        Course course = new Course();
        course.setId(id);
        Course course1 = courseDao.selectOne(course);
        courseDao.deleteByPrimaryKey(id);
        return course1;
    }
}
