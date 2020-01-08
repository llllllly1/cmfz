package com.baizhi.ly.service.impl;

import com.baizhi.ly.ascept.Logs;
import com.baizhi.ly.dao.AlbumDao;
import com.baizhi.ly.entity.Album;
import com.baizhi.ly.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumDao albumDao;

    //分页查询专辑
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Album> albums = albumDao.selectByRowBounds(new Album(), new RowBounds((page-1)*rows,rows));
        //查询总条数
        int records = albumDao.selectCount(new Album());
        //设置总页数
        int total = (records+(rows-1))/rows;
        hashMap.put("rows",albums);
        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("page",page);

        //rows 查询数据   page 当前页   records 总条数   total 总页数
        return hashMap;
    }

    //添加专辑
    @Logs(name = "添加专辑")
    public String insertAlbum(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCreateDate(new Date());
        albumDao.insert(album);
        return album.getId();
    }

    //修改专辑
    @Logs(name = "修改专辑")
    public String updateAlbum(Album album) {
        album.setCover(null);
        albumDao.updateByPrimaryKeySelective(album);
        return album.getId();
    }

    //批量删除专辑
    @Logs(name = "批量删除专辑")
    public void deleteList(List list) {
        albumDao.deleteByIdList(list);
    }

    //查询所有专辑
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Album> queryAll() {
        List<Album> albums = albumDao.selectAll();
        return albums;
    }

 //--------------------------------------------------根据id查询专辑-------------------------------------------------------------------
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Album queryOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }


}
