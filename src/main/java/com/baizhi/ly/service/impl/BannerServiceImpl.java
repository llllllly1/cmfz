package com.baizhi.ly.service.impl;

import com.baizhi.ly.ascept.Logs;
import com.baizhi.ly.dao.BannerDao;
import com.baizhi.ly.entity.Banner;
import com.baizhi.ly.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    //添加轮播图
    public String insertBanner(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreateDate(new Date());
        bannerDao.insert(banner);
        return banner.getId();
    }

    //修改轮播图
    public String updateBanner(Banner banner) {
        banner.setUrl(null);
        bannerDao.updateByPrimaryKeySelective(banner);
        return banner.getId();
    }

    //删除轮播图
    public void deleteBanner(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    //批量删除轮播图
    public void deleteList(List list) {
        bannerDao.deleteByIdList(list);
    }

    //分页查询轮播图
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Logs(name = "分页查询轮播图")
    public Map queryAllFenYe(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds((page-1)*rows,rows));
        //查询总条数
        int records = bannerDao.selectCount(new Banner());
        //设置总页数
        int total = (records+(rows-1))/rows;
        hashMap.put("rows",banners);
        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("page",page);

        //rows 查询数据   page 当前页   records 总条数   total 总页数
        return hashMap;
    }

    //查询所有轮播图
    public List<Banner> queryAll() {
        List<Banner> banners = bannerDao.selectAll();
        return banners;
    }

    //根据id查一个
    public Banner queryOne(String id) {
        Banner banner = new Banner();
        banner.setId(id);
        Banner banner1 = bannerDao.selectOne(banner);
        return banner1;
    }

}
