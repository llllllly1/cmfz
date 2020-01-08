package com.baizhi.ly.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.ly.entity.Banner;
import com.baizhi.ly.service.BannerService;

import java.util.ArrayList;
import java.util.List;

/*
    1. 创建一个Listener类 继承 AnalysisEventListener<实体类>
    2. 重写方法
    invoke : 读取每行数据后会执行的方法
    doAfterAllAnalysed: 所有数据读取完毕后执行的方法
 */

public class BannerListener extends AnalysisEventListener<Banner> {
    private BannerService bannerService;

    public BannerListener (BannerService bannerService) {
       this.bannerService = bannerService;
    }


    List<Banner> list = new ArrayList<Banner>();

    // Banner 针对每行数据 进行的实体类封装
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        list.add(banner);
        bannerService.insertBanner(banner);
        System.out.println(banner);
    }


    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(list);

    }
}
