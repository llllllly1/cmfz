package com.baizhi.ly;


import com.baizhi.ly.dao.BannerDao;
import com.baizhi.ly.dao.UsersDao;
import com.baizhi.ly.service.AdminService;
import com.baizhi.ly.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(value = SpringRunner.class)
public class test2 {
    @Autowired
    AdminService adminService;
    @Autowired
    BannerDao bannerDao;
    @Autowired
    UsersDao userDao;
    @Autowired
    ArticleService articleService;
    @Test
    public void test1(){
        Map map = articleService.queryByPage(1, 2);
        for (Object o : map.keySet()) {
            System.out.println(map.get(o));
        }
    }

}
