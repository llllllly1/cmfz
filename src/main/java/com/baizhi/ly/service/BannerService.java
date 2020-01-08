package com.baizhi.ly.service;

import com.baizhi.ly.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    //添加轮播图
    public abstract String insertBanner(Banner banner);
    //修改轮播图
    public abstract String updateBanner(Banner banner);
    //删除轮播图
    public abstract  void deleteBanner(String id);
    //批量删除轮播图
    public abstract void deleteList(List list);
    //分页查询轮播图
    public abstract Map queryAllFenYe(Integer page, Integer rows);
    //查询所有轮播图
    public abstract List<Banner> queryAll();
    //根据id查一个
    public abstract Banner queryOne(String id);
}
