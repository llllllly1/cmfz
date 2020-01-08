package com.baizhi.ly.service;

import com.baizhi.ly.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    //分页查专辑下的所有章节
    public abstract Map queryByPage(Integer page, Integer rows,String albumId);
    //添加章节
    public abstract String insertChapter(Chapter chapter,String albumId);
    //修改章节
    public abstract String updateChapter(Chapter chapter);
    //批量删除章节
    public abstract void deleteList(List list);
}
