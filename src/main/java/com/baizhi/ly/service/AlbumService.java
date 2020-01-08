package com.baizhi.ly.service;

import com.baizhi.ly.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //分页查所有专辑
    public abstract Map queryByPage(Integer page, Integer rows);
    //添加专辑
    public abstract String insertAlbum(Album album);
    //修改专辑
    public abstract String updateAlbum(Album album);
    //批量删除专辑
    public abstract void deleteList(List list);
    //查询所有专辑
    public abstract List<Album> queryAll();
    //根据id查询专辑
    public abstract Album queryOne(String id);
}
