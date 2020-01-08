package com.baizhi.ly.controller;

import com.baizhi.ly.entity.Guru;
import com.baizhi.ly.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    //查询所有上师
    @RequestMapping("queryAllGuru")
    public List<Guru> queryAllGuru(){
        List<Guru> gurus = guruService.queryAll();
        System.out.println("进入到这里");
        return gurus;
    }
}
