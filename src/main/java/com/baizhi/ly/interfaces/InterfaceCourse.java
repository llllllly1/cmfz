package com.baizhi.ly.interfaces;

import com.baizhi.ly.entity.Course;
import com.baizhi.ly.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("interfaceCourse")
public class InterfaceCourse {
    @Autowired
    CourseService courseService;

//    -------------------------------------------------展示功课---------------------------------------------------------------------
    @RequestMapping("queryCourse")
    public Map queryCourse(String uid){
        HashMap map = new HashMap();
        try{
            List<Course> courses = courseService.queryByUser(uid);
            map.put("status",200);
            map.put("option",courses);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","查询失败");
        }
        return map;
    }

//    ------------------------------------------------添加功课----------------------------------------------------------------------
    @RequestMapping("insertCourse")
    public Map insertCourse(String uid,Course course) {
        HashMap map = new HashMap();
        try{
            Course course1 = courseService.insert(course, uid);
            map.put("status",200);
            map.put("option",course1);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","添加失败");
        }
        return map;
    }

//    ------------------------------------------------删除功课---------------------------------------------------------------
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String id) {
        HashMap map = new HashMap();
        try{
            Course course = courseService.delete(id);
            map.put("status",200);
            map.put("option",course);
        }catch (Exception e){
            map.put("status",-200);
            map.put("message","删除失败");
        }
        return map;
    }
}
